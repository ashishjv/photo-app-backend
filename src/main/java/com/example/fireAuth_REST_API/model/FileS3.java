package com.example.fireAuth_REST_API.model;

public class FileS3 {
    private String id      = ""; //"60312bb6a7836128846ffd4d";
    private String fileId  = ""; //"f83954fb-60bb-4700-8afa-cff5cda27c3e";
    private String name    = ""; //"d99a70c6-27c2-4e87-9bf0-0a49f93956f8.jpg";
    private String path    = ""; //"album/d99a70c6-27c2-4e87-9bf0-0a49f93956f8.jpg";
    private String owner   = ""; //null;

    public FileS3(){}

    public FileS3(String id, // S3 ETag
                  String fileId, // MongoDB file ID
                  String name, // MongoDB Original file Name
                  String path,
                  String owner){
        this.id = id;
        this.fileId = fileId;
        this.name = name;
        this.path = path;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

