package com.example.stratifytask.services;

import com.example.stratifytask.models.FileInfo;
import com.example.stratifytask.repositories.FileInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@Service
public class FileInfoService {

    private FileInfoRepository fileInfoRepository;

    @Autowired
    public FileInfoService(FileInfoRepository fileInfoRepository){
        this.fileInfoRepository=fileInfoRepository;
    }

    public void saveFileInfo(MultipartFile csvFile){
        final Path root = Paths.get("uploads");
        //create a root directory
        prepareRootDirectory(root);
        //copy the file to the root directory
        copyUploadedFile(csvFile,root);
        //retrieving file basic attributes
        BasicFileAttributes attributes=getFileAttributes(root,csvFile);

        LocalDate fileCreationDate=attributes.creationTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        //creating a new fileInfo instance
        FileInfo fileInfo=new FileInfo(csvFile.getOriginalFilename().toLowerCase().replace(" ",""),
                fileCreationDate,
                csvFile.getSize(),
                LocalDate.now()
                );
        fileInfoRepository.save(fileInfo);

        /*removing the created directory after retrieving necessary info,
         in a real case we will probably be saving the files to a cloud provider
         **/
        cleanDirectory(root);
    }
    private static void prepareRootDirectory(Path directory){
        try {
            Files.createDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    private static void copyUploadedFile(MultipartFile csvFile,Path root){
        try {
            Files.copy(csvFile.getInputStream(), root.resolve(csvFile.getOriginalFilename().toLowerCase().replace(" ","")));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
    private static BasicFileAttributes getFileAttributes(Path root,MultipartFile csvFile){
        File file= new File(root+"/"+csvFile.getOriginalFilename().replace(" ",""));
        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(file.toPath(),
                    BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    return attr;}

    private static void cleanDirectory(Path root){
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}
