package com.ohgiraffers.comprehensive.common.util;

import com.ohgiraffers.comprehensive.common.exception.ServerInternalException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.FAIL_TO_DELETE_FILE;
import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.FAIL_TO_UPLOAD_FILE;

public class FileUploadUtils {
                                                        //String uploadDir, String fileName을 기반으로 multipartFile 저장
    public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) {

        try(InputStream inputStream = multipartFile.getInputStream()) { //multipartFile을 기반으로 inputStream

            //해당 path가 존재하는지 uploadPath가 존재하지 않으면 파일 먼저 생성
            Path uploadPath = Paths.get(uploadDir);
            /* 업로드 경로가 존재하지 않을 시 경로 먼저 생성 */
            if (!Files.exists(uploadPath))
                Files.createDirectories(uploadPath);

            /* 파일명 생성 */                         //FilenameUtils.getExtension : OriginalFilename을 이용해서 뒤의 확장자를 뗀다.
            String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

            /* 파일 저장 */  //uploadPath와 저장할 파일명 합쳐서 Path 객체 생성
            Path filePath = uploadPath.resolve(replaceFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); //StandardCopyOption.REPLACE_EXISTING 존재하면 교체한다.

            return replaceFileName;

        } catch (IOException e) {
            throw new ServerInternalException(FAIL_TO_UPLOAD_FILE);
        }

    }

    public static void deleteFile(String uploadDir, String fileName) {

        try {
            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(fileName);
            Files.delete(filePath);
        } catch (IOException e) {
            throw new ServerInternalException(FAIL_TO_DELETE_FILE);
        }

    }

}
