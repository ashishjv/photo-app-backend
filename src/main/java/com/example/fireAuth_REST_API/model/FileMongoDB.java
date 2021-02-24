package com.example.fireAuth_REST_API.model;

public class FileMongoDB {
    private String id;
    private String owner;
    private String fileName;

    public FileMongoDB(String fileName, String owner){
        this.fileName = fileName;
        this.owner = owner;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
