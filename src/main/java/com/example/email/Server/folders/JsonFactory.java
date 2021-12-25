package com.example.email.Server.folders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonFactory {

    public void createJsonFile(File resultFile, Object value) throws IOException {
        resultFile.createNewFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resultFile, value);
    }

}
