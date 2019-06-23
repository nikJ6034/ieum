package com.eum.util.file.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eum.attachFile.entity.AttachFile;
import com.eum.util.file.FileUtil;

/**
 * UploadResourceImageFileUtil
 */
@Component
 public class UploadResourceImageFileUtil{

    @Value("${uploads.resouceImgPath}")
    private String resourceImagePath;

    public AttachFile imageUpload(String originalName, byte[] fileData) throws Exception {
        String formatName = originalName.substring(originalName.lastIndexOf(".")+1);

        if(ImageUtil.getMediaType(formatName) != null) {
        	AttachFile uploadFile = FileUtil.uploadFile(resourceImagePath, originalName,fileData);
        	uploadFile.setResourcePath("/uploads/resImg"+uploadFile.getSavedPath());
            return uploadFile;
        }else{
            throw new Exception("이미지가 아닙니다.");
        }
       
    }
    
    public AttachFile imageUpload(String originalName, byte[] fileData, int height) throws Exception {
    	AttachFile imageUpload = imageUpload(originalName,fileData);
    	File file = new File(imageUpload.getFullPath()+File.separator+imageUpload.getVirtualName());
    	BufferedImage sourceImg = ImageIO.read(file);
		BufferedImage destImg = Scalr.resize(sourceImg,Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_HEIGHT, height);
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		ImageIO.write(destImg, formatName.toUpperCase(), file);
		return imageUpload;
    }

    public AttachFile imageUploadWithThumbnail(String originalName, byte[] fileData) throws Exception {
        AttachFile imageUpload = imageUpload(originalName, fileData);
        makeThumbnail(imageUpload.getFullPath(),imageUpload.getVirtualName());
        imageUpload.setThumbnailYN("Y");
       return imageUpload;
    }
    
    public void imageDelete(String path, String fileName) {
    	File file = null;
    	File thumbnailFile = null;
    	if(path.lastIndexOf("/") == 0 || path.lastIndexOf("\\") == 0) {
        	file = new File(path+fileName);
        	thumbnailFile = new File(path+"s_"+fileName);
    	}else {
    		file = new File(path+File.separator+fileName);
    		thumbnailFile = new File(path+File.separator+"s_"+fileName);
    	}
    	
    	if(file.exists()) {
    		file.delete();
    	}
    	if(thumbnailFile.exists()) {
    		thumbnailFile.delete();
    	}
    }


    //썸네일 이미지 생성
	private static String makeThumbnail(String uploadPath, String fileName) throws Exception {
		
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath , fileName));
		
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 300);
		
		String thumbnailName = uploadPath + File.separator + "s_" + fileName;
		
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);

		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
    
}