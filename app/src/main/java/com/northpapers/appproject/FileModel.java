package com.northpapers.appproject;

/**
 * Created by Rahul Baboria on 4/23/2017.
 */

public class FileModel {
    public  String heading;
    public  String description;
    public  String newImageReference;
    public  String fileReference;


    public FileModel(String heading, String description, String newImageReference, String fileReference) {
        this.heading = heading;
        this.description = description;
        this.newImageReference = newImageReference;
        this.fileReference = fileReference;
    }




     public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getNewImageReference() {
        return newImageReference;
    }

    public String getFileReference() {
        return fileReference;
    }
}
