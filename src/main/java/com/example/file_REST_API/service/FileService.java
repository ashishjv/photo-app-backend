package com.example.file_REST_API.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Value("${aws.access_key_id}")
    private String keyID;

    @Value("${aws.secret_access_key}")
    private String keySecret;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public boolean upload(MultipartFile file) {

        BasicAWSCredentials credentials = new BasicAWSCredentials(this.keyID, this.keySecret);

        final AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3client.putObject(this.bucketName, file.getOriginalFilename(), file.getInputStream(),metadata);
            return true;
        } catch (AmazonServiceException | IOException e) {
            return false;
        }
    }

    public S3Object getFile(String key){

        BasicAWSCredentials credentials = new BasicAWSCredentials(this.keyID, this.keySecret);

        final AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        return s3client.getObject(this.bucketName,key);
    }

    public void deleteFile(String key){

        BasicAWSCredentials credentials = new BasicAWSCredentials(this.keyID, this.keySecret);

        final AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        s3client.deleteObject(this.bucketName,key);
    }


}
