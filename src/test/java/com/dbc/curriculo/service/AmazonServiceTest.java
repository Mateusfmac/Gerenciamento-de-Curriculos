package com.dbc.curriculo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.dbc.curriculo.exceptions.S3Exception;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AmazonServiceTest {

    @InjectMocks
    private AmazonS3Service amazonS3Service;

    @Mock
    private AmazonS3 amazonS3;

    private MockMultipartFile documento;

    private URL url;

    @Before
    public void init() throws MalformedURLException {

        ReflectionTestUtils.setField(amazonS3Service, "bucketName", "teste");

        documento = new MockMultipartFile(
                "arquivo.pdf",
                "arquivo",
                "application/pdf",
                "{key1: value1}".getBytes());

        url = new URL(
                "https",
                "stackoverflow.com",
                80, "pages/page1.html");


    }

    @Test
    public void deveTestarUploadFile() throws S3Exception, IOException {

        doReturn(null).when(amazonS3).putObject(any(), any(), any(), any());
        when(amazonS3.getUrl(anyString(), anyString())).thenReturn(url);
        URI uri = amazonS3Service.uploadFile(documento);
        assertNotNull(uri);
    }

    @Test(expected = S3Exception.class)
    public void deveTestarUploadFileException() throws S3Exception, IOException {

        doReturn(null).when(amazonS3).putObject(any(), any(), any(), any());
        when(amazonS3.getUrl(anyString(), anyString())).thenReturn(new URL(
                "https",
                "stackoverflow .com",
                80, "pages/page1.html"));

        amazonS3Service.uploadFile(documento);

    }

}
