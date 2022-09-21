package com.example.email.Server.DataBaseServices;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadFile {

    public static void uploadFile(byte[] data,String userID ,String fileName) throws IOException {
        MongoDatabase database = DataBase.connectToDB(userID);
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        GridFSUploadOptions options = new GridFSUploadOptions();
        try (GridFSUploadStream uploadStream = gridFSBucket.openUploadStream(fileName, options)) {
            uploadStream.write(data);
            uploadStream.flush();
            System.out.println("The file id of the uploaded file is: " + uploadStream.getObjectId().toHexString());
        } catch (Exception e) {
            System.err.println("The file upload failed: " + e);
        }
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\chess.png");
        String name = "logo.png";
        String originalFileName = "logo.png";
        String contentType = "image/png";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        uploadFile( result.getBytes(), "UploadedFiles","chess.png");

    }
}
