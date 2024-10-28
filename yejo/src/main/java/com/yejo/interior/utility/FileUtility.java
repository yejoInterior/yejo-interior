package com.yejo.interior.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class FileUtility {
	
	@Value("${cdn.server}")
    private String server;

    @Value("${cdn.user}")
    private String username;

    @Value("${cdn.password}")
    private String password;
    
    @Value("${cdn.path}")
    private String cdnPath;

    public Map<String,Object> uploadFile(MultipartFile file, String directory) {
    	Map<String, Object> response = new HashMap<>();
        if (file.isEmpty()) {
            log.warn("No file uploaded.");
            response.put("success", false);
            return response;
        }

        FTPClient ftpClient = new FTPClient();
        
        try {
            // 서버에 연결
            ftpClient.connect(server);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // InputStream으로 파일 열기
            try (InputStream inputStream = file.getInputStream()) {
            	String uuidFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                String remoteFilePath = "/www/images/" + directory + "/" + uuidFileName;
                String cdnFilePath = cdnPath + directory + "/" + uuidFileName;
  
                // 파일 업로드
                boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
                if (done) {
                    log.info("File uploaded successfully: " + file.getOriginalFilename());
                    response.put("success", true);
                    response.put("filePath", cdnFilePath);
                    return response;
                } else {
                    log.warn("Failed to upload the file: " + file.getOriginalFilename());
                    response.put("success", false);
                    return response;
                }
            }

        } catch (IOException ex) {
            log.error("Error occurred during file upload: " + ex.getMessage(), ex);
            response.put("success", false);
            return response;
        } finally {
            try {
                // FTP 연결 종료
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                log.error("Error while disconnecting FTP client: " + ex.getMessage(), ex);
            }
        }
    }
 // CDN에서 파일 삭제 메서드
    public boolean deleteFileFromCDN(String filePath) {
        FTPClient ftpClient = new FTPClient();
        boolean success = false;

        try {
            // 서버에 연결
            ftpClient.connect(server);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 파일 삭제
            String remoteFilePath = filePath.replace(cdnPath, "/www/images/");
            boolean deleted = ftpClient.deleteFile(remoteFilePath);

            if (deleted) {
                log.info("File deleted successfully from CDN: " + remoteFilePath);
                success = true;
            } else {
                log.warn("Failed to delete file from CDN: " + remoteFilePath);
            }

        } catch (IOException ex) {
            log.error("Error occurred during file deletion: " + ex.getMessage(), ex);
        } finally {
            try {
                // FTP 연결 종료
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                log.error("Error while disconnecting FTP client: " + ex.getMessage(), ex);
            }
        }

        return success;
    }
    
    
}
