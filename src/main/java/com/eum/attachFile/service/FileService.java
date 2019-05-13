package com.eum.attachFile.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eum.attachFile.entity.AttachFile;
import com.eum.attachFile.repository.AttachFileRepository;

/**
 * FileService
 */
@Service
public class FileService {
    
    @Autowired
    AttachFileRepository attachFileRepository;
    
    public Optional<AttachFile> one(long id){
        return attachFileRepository.findById(id);
    }
    
}