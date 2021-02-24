package com.example.fireAuth_REST_API.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.example.fireAuth_REST_API.model.FileMongoDB;
import com.example.fireAuth_REST_API.model.FileS3;
import com.example.fireAuth_REST_API.repository.FileMongoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private FileMongoDBRepository fileMongoDBRepository;

    @Value("${aws.access_key_id}")
    private String keyID;

    @Value("${aws.secret_access_key}")
    private String keySecret;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public FileMongoDB saveFileMongoDB(FileMongoDB fileMongoDB) {
        return fileMongoDBRepository.save(fileMongoDB);
    }

    public FileS3 upload(MultipartFile file) {

        System.out.println("Inside PostMapping FileService 1");

        FileMongoDB fileMongoDB = new FileMongoDB(file.getOriginalFilename(), "");
        fileMongoDB = saveFileMongoDB(fileMongoDB);

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

            PutObjectResult putObjectResult = s3client.putObject(this.bucketName, fileMongoDB.getId(), file.getInputStream(),metadata);

//            System.out.println("\n *** id     = ETag                       = "+putObjectResult.getETag());
//            System.out.println("\n *** fileID = MongoDB unique file ID     = "+fileMongoDB.getId());
//            System.out.println("\n *** name   = MongoDB original file name = "+fileMongoDB.getFileName());
//            System.out.println("\n *** path                                = "+"album/"+fileMongoDB.getId());
//            System.out.println("\n *** owner  = MongoDB Owner              = "+fileMongoDB.getOwner());

            FileS3 fileS3 = new FileS3();
            fileS3.setId(putObjectResult.getETag());
            fileS3.setFileId(fileMongoDB.getId());
            fileS3.setName(fileMongoDB.getFileName());
            fileS3.setPath("album/"+fileMongoDB.getId());
            fileS3.setOwner(fileMongoDB.getOwner());

            //return "*** Success putting in S3";
            return fileS3;

        } catch (AmazonServiceException | IOException e) {
            //return "*** Failed putting in S3";
            return null;
        }
    }

    public S3Object getFile(String key){ // key is the file name

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

