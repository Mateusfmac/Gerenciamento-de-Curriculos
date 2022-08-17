package com.dbc.curriculo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dbc.curriculo.exceptions.S3Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {

    @Value("${s3.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public URI uploadFile(MultipartFile multipartFile) throws S3Exception {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            String fileName = Calendar.getInstance().toString() + multipartFile.getOriginalFilename();
            fileName = Base64.getEncoder().encodeToString(
                            fileName.getBytes());
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentType);
        } catch (IOException e) {
            throw new S3Exception("Erro ao salvar arquivo.");
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) throws S3Exception {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            amazonS3.putObject(bucketName, fileName, inputStream, objectMetadata);
            return amazonS3.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new S3Exception("Erro ao salvar arquivo");
        }
    }
}
