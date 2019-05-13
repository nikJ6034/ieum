package com.eum.util.file;

import com.eum.attachFile.entity.AttachFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * UploadFile
 */
@Component
public class UploadFileUtil {

    @Value("${uploads.filePath}")
    private String uploadFilePath;

    public AttachFile fileUpload(String originalName, byte[] fileData) throws Exception{

        return FileUtil.uploadFile(uploadFilePath, originalName,fileData);

    }
    
}