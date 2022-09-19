package com.example.email.Server.DataBaseServices;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSDownloadOptions;
import org.bson.types.ObjectId;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DownloadFiles {

    public static void downloadFile(String userID, String emailID, String fileName){
        MongoDatabase database = DataBase.connectToDB("UploadedFiles");
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        ObjectId fileId = new ObjectId("63288afa1767d920497e914b");
        try (GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(fileId)) {
            int fileLength = (int) downloadStream.getGridFSFile().getLength();
            byte[] bytesToWriteTo = new byte[fileLength];
            downloadStream.read(bytesToWriteTo);
            System.out.println(new String(bytesToWriteTo, StandardCharsets.UTF_8));
        }


    }

    public static void downloadFile2(String userID, String emailID, String fileName){
        MongoDatabase database = DataBase.connectToDB("UploadedFiles");
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        GridFSDownloadOptions downloadOptions = new GridFSDownloadOptions().revision(0);
        try (FileOutputStream streamToDownloadTo = new FileOutputStream(
                "D:\\Bank System\\bankingSystemBackend\\" +
                "myProject.zip")) {
            gridFSBucket.downloadToStream("logo.zip", streamToDownloadTo, downloadOptions);
            streamToDownloadTo.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        downloadFile2("1", "1", "1");
    }
}
