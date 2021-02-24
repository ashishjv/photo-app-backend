package com.example.fireAuth_REST_API.resource;

import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.S3Object;
import com.example.fireAuth_REST_API.model.FileS3;
import com.example.fireAuth_REST_API.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileResource {

    @Autowired
    private FileService fileService;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(name = "fileId") String fileId)
            throws IOException {

        S3Object object = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(object.getObjectMetadata().getContentType()))
                .header(Headers.CONTENT_DISPOSITION, "attachment; filename=\""+fileId+"\"")
                .body(new ByteArrayResource(object.getObjectContent().readAllBytes()));
    }


    @GetMapping("/show/{fileId}")
    public void showFile(@PathVariable(name = "fileId") String fileId,
                            HttpServletResponse response) throws IOException {
        S3Object object = fileService.getFile(fileId);
        response.setContentType(object.getObjectMetadata().getContentType());
        response.getOutputStream().write(object.getObjectContent().readAllBytes());
    }

    @PostMapping("/upload")
    public FileS3 uploadFile(@RequestPart("file0") MultipartFile file){
        return fileService.upload(file);
    }

    @DeleteMapping
    public void delete(@RequestParam(name="fileId") String fileId){
        fileService.deleteFile(fileId);
    }
}
