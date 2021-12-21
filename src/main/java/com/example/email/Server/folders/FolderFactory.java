package com.example.email.Server.folders;
import java.io.File;

public class FolderFactory {
    private boolean fileCreated;

    public FolderFactory(){
        fileCreated = false;
    }

    public boolean getFileCreated(){return fileCreated;}

    public void createFolder(String path){
        File f = new File(path);
        fileCreated = f.mkdirs();
    }
}
