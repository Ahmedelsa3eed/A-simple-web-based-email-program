package com.example.email.Server.folders;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
@Configuration
@EnableScheduling
public class FileListFilter {

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void recursiveDelete(File file) {
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File fyle : files) {
                    if (fyle.isDirectory()) {
                        recursiveDelete(fyle);
                    }
                    else {
                        if (fyle.lastModified() > (double)30 * 24 * 60 * 60 * 1000) {
                            fyle.delete();
                        }
                    }
                }
            }
        }
    }
}
