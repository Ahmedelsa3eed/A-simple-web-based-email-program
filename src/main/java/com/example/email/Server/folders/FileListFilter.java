package com.example.email.Server.folders;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/*rt java.io.File;
@Configuration
@EnableScheduling
public class FileListFilter {

    //@Scheduled(fixedDelay = 3 *  1000)
    public void recursiveDelete(File file) {
        System.out.println("a");
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File fyle : files) {
                    if (fyle.isDirectory()) {
                        recursiveDelete(fyle);
                    }
                    else {
                        System.out.println(fyle.lastModified());
                        if (fyle.lastModified() > (double) 3* 1000) {
                            fyle.delete();
                        }
                    }
                }
            }
        }
    }
}
*/