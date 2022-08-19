package com.dbc.curriculo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.dbc.curriculo.exceptions.S3Exception;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AmazonServiceTest {

    @InjectMocks
    private AmazonS3Service amazonS3Service;

    @Mock
    private AmazonS3 amazonS3;

    private MockMultipartFile documento;

    private final String bucketName = "";

    @Before
    public void init() {

        ReflectionTestUtils.setField(amazonS3Service, "bucketName", "teste");

        documento = new MockMultipartFile(
                "arquivo.pdf",
                "arquivo",
                "application/pdf",
                "{key1: value1}".getBytes());
    }

    @Test
    public void deveTestaruploadFile() throws S3Exception, MalformedURLException {

        URL url = new URL(
                "https",
                "stackoverflow.com",
                80, "pages/page1.html");

        doReturn(null).when(amazonS3).putObject(any(), any(), any(), any());
        when(amazonS3.getUrl(anyString(), anyString())).thenReturn(url);
        URI uri = amazonS3Service.uploadFile(documento);
        assertNotNull(uri);
    }

    @Test(expected = IOException.class)
    public void deveTestarExececaoIoException() throws S3Exception, FileNotFoundException {


    }

//    @Test()
//    public void deveTestarExcecaoURIError() throws S3Exception, URISyntaxException, MalformedURLException {
//
//
//        doReturn(null).when(amazonS3).putObject(any(), any(), any(), any());
//        when(amazonS3.getUrl(anyString(), anyString())).thenReturn(any(URL.class));
//        //when(url.toURI()).thenReturn(new URI("http://www. javacodegeeks.com/"));
//
//    }




}
