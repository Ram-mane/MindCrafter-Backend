package com.user.bloggs.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.user.bloggs.services.FileServices;
@Service
public class FileServicesImpl implements FileServices {

	@Override
	public String uploadeImage(String path, MultipartFile file) throws IOException {
		  // Ensure path ends with a separator
//        if (!path.endsWith(File.separator)) {
//            path += File.separator;
//        }

        // File name
        String name = file.getOriginalFilename();

        //renaming the file
        String randomID = UUID.randomUUID().toString();
        
        String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
        
        // File path
        String filePath = path + fileName;

        // Create folder if not exist
        File directory = new File(path);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + path);
            } else {
                throw new IOException("Failed to create directory: " + path);
            }
        }

        // Copy image to path
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		String fullPath = path+File.separator+fileName;
		
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
