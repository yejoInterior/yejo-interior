package com.yejo.interior.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpToHttpsRedirectConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return factory -> factory.addAdditionalTomcatConnectors(createHttpConnector());
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(80); // 기존 HTTP 포트
        connector.setSecure(false);
        connector.setRedirectPort(44333); // HTTPS 포트로 리다이렉트
        return connector;
    }
}
