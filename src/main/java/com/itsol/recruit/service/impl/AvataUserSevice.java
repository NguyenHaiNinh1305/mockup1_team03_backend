package com.itsol.recruit.service.impl;

import com.itsol.recruit.service.IStorageSevice;
import io.jsonwebtoken.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class AvataUserSevice implements IStorageSevice {

    private final Path strorageFolder = Paths.get("uploads");

    public AvataUserSevice(){
        try{
            Files.createDirectories(strorageFolder);
        } catch (Exception e) {
            throw new RuntimeException("cannot initialize storage");
        }
    }

    private boolean isImageFile(MultipartFile file){
        // kiem tra duoi file xem co dung file anh hay khong
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png","jpg","jpeg","bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }


    @Override
    // nhan vao mot file
    public String storaFile(MultipartFile file) {
        try {

    System.out.println("vao methot storafile");
    if (file.isEmpty()) {
        throw new RuntimeException("failed to store empty file.");
    }
    if (!isImageFile(file)) {
        throw new RuntimeException("You can only upload image(.png,.jpg,.jpeg,.bmp)");
    }
    // file chi co the nho hon 5mb
    float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
    if (fileSizeInMegabytes > 5.0f) {
        throw new RuntimeException("file must be <= 5mb");
    }

    //doi ten file
    String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
    String generaredFileName = UUID.randomUUID().toString().replace("-", "");
    generaredFileName = generaredFileName + "." + fileExtension;
    Path destinationFilePath = this.strorageFolder.resolve(
                    Paths.get(generaredFileName))
            .normalize().toAbsolutePath();
    if (!destinationFilePath.getParent().equals(this.strorageFolder.toAbsolutePath())) {
        throw new RuntimeException(
                "canot store file outside current directory");
    }
        try(InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
            return generaredFileName;

        }catch (IOException e){
            throw new RuntimeException(
                    "Failed to store file.", e);
        }

        }


    @Override
    public byte[] readFileContent(String fileName) {
        try{
            Path file = strorageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }else {
                throw new RuntimeException(
                        "Could not read file:" +fileName
                );
            }

        } catch (java.io.IOException e) {
            throw new RuntimeException(
                    "Could not read file:" +fileName
            );
        }
    }

}
