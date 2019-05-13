package com.eum.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import com.eum.attachFile.entity.AttachFile;

import org.springframework.util.FileCopyUtils;

/**
 * UploadFileUtil
 */
public class FileUtil {

    public static AttachFile uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
        AttachFile attachFile = new AttachFile();
        UUID uid = UUID.randomUUID();

        String savedName = uid.toString() + "_" + originalName;

        // 파일을 저장할 폴더 생성(년 월 일 기준)
        String savedPath = calcPath(uploadPath);

        // 저장할 파일준비
        File target = new File(uploadPath + savedPath, savedName);

        // 파일을 저장
        FileCopyUtils.copy(fileData, target);

        attachFile.setVirtualName(savedName);
        attachFile.setRealName(originalName);
        attachFile.setSavedPath(savedPath);
        attachFile.setFullPath(uploadPath + savedPath);
        return attachFile;
    }

    // 폴더 생성 함수
    private static String calcPath(String uploadPath) throws IOException {

        Calendar cal = Calendar.getInstance();

        String yearPath = "/" + cal.get(Calendar.YEAR);

        String monthPath = yearPath + "/" + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

        String datePath = monthPath + "/" + new DecimalFormat("00").format(cal.get(Calendar.DATE));

        makeDir(uploadPath, yearPath, monthPath, datePath);

        return datePath;
    }// calcPath

    // 폴더 생성 함수
    private static void makeDir(String uploadPath, String... paths) throws IOException {
		
		if(new File(uploadPath + paths[paths.length -1]).exists()) {
			return;
        }//if
        File imgfileRoot = new File(uploadPath);
        if(!imgfileRoot.exists()){
        	Path path = imgfileRoot.toPath();
        	Files.createDirectories(path);
        }
		
		for(String path : paths) {
			
			File dirPath = new File(uploadPath + path);
			
			if(!dirPath.exists()) {
				Files.createDirectory(dirPath.toPath());
			}//if
			
		}//for
		
	}//makeDir
	
}