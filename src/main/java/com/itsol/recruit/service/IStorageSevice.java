package com.itsol.recruit.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageSevice {
    String storaFile(MultipartFile file);

    byte[] readFileContent(String fileName);




}
