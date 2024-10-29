package com.yejo.interior.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.repository.CompanyLocationRepository;

class CompanyLocationServiceTest {

    @InjectMocks
    private CompanyLocationService locationService;

    @Mock
    private CompanyLocationRepository locationRepository;

    private CompanyLocationEntity location;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        location = new CompanyLocationEntity();
        location.setId(1L);
        location.setAddress("123 Main St");
        location.setTel("010-1234-5678");
        location.setSafeTel("0507-1234-5678");
        location.setEmail("test@example.com");
        location.setNaverMapUrl("https://naver.com");
        location.setKakaoMapUrl("https://kakao.com");
        location.setLatitude(37.5665);
        location.setLongitude(126.9780);
    }

    @Test
    void testSaveLocation() {
        when(locationRepository.save(any(CompanyLocationEntity.class))).thenReturn(location);

        CompanyLocationEntity savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        assertEquals(location.getAddress(), savedLocation.getAddress());
        verify(locationRepository).save(location);
    }

    @Test
    void testGetLocation() {
        when(locationRepository.findById(1L)).thenReturn(java.util.Optional.of(location));

        CompanyLocationEntity foundLocation = locationService.getLocation();

        assertNotNull(foundLocation);
        assertEquals(location.getAddress(), foundLocation.getAddress());
        verify(locationRepository).findById(1L);
    }

    @Test
    void testUpdateLocation() {
        when(locationRepository.findById(location.getId())).thenReturn(java.util.Optional.of(location));
        when(locationRepository.save(any(CompanyLocationEntity.class))).thenReturn(location);

        CompanyLocationEntity updatedLocation = new CompanyLocationEntity();
        updatedLocation.setId(1L);
        updatedLocation.setAddress("456 New St");
        updatedLocation.setTel("010-9876-5432");
        updatedLocation.setSafeTel("0507-9876-5432");
        updatedLocation.setEmail("updated@example.com");
        updatedLocation.setNaverMapUrl("https://new.naver.com");
        updatedLocation.setKakaoMapUrl("https://new.kakao.com");
        updatedLocation.setLatitude(37.1234);
        updatedLocation.setLongitude(127.5678);

        CompanyLocationEntity result = locationService.updateLocation(updatedLocation);

        assertNotNull(result);
        assertEquals("456 New St", result.getAddress());
        verify(locationRepository).save(location);
    }

    @Test
    void testUpdateLocationNotFound() {
        when(locationRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        CompanyLocationEntity updatedLocation = new CompanyLocationEntity();
        updatedLocation.setId(2L);

        assertThrows(RuntimeException.class, () -> locationService.updateLocation(updatedLocation));
    }
}
