package com.eum.program.service;

import com.eum.attachFile.entity.AttachFile;
import com.eum.attachFile.repository.AttachFileRepository;
import com.eum.auth.dto.AuthDTO;
import com.eum.config.oAuth2.AuthService;
import com.eum.program.dto.ProgramDTO;
import com.eum.program.dto.ProgramSearchDTO;
import com.eum.program.entity.Program;
import com.eum.program.repository.ProgramRepository;
import com.eum.program.repository.ProgramRepositoryDsl;
import com.eum.util.file.UploadFileUtil;
import com.eum.util.file.image.UploadResourceImageFileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgramService {

    @Autowired
    ProgramRepository programRepository;
    @Autowired
    ProgramRepositoryDsl programRepositoryDsl;
    @Autowired
    AuthService authService;
    @Autowired
    AttachFileRepository attachFileRepository;
    @Autowired
    UploadFileUtil uploadFileUtil;
    @Autowired
    UploadResourceImageFileUtil uploadResourceImageFileUtil;
    @Autowired
    ModelMapper modelMapper;

    public Page<ProgramDTO> list(ProgramSearchDTO programDTO){
        Page<ProgramDTO> list = programRepositoryDsl.programList(programDTO).map(pro-> modelMapper.map(pro, ProgramDTO.class));
        return list;
    }

    public ProgramDTO one(Long id){
        return programRepository.findById(id).map(pro -> modelMapper.map(pro, ProgramDTO.class)).get();
    }

    public Map<String, Object> save(Program program, List<MultipartFile> files, MultipartFile image){
        Map<String, Object> map = new HashMap<>();

        Optional<AuthDTO> auth = Optional.ofNullable(authService.getAuth());
        try {
            if (auth.map(AuthDTO::isAdmin).orElse(false)) {
                program.setRegId(auth.map(AuthDTO::getMemberId).orElse(null));
                Program save = programRepository.save(program);

                //이미지 저장 시작
                if(image != null){
                    AttachFile imageFile = uploadResourceImageFileUtil.imageUploadWithThumbnail(image.getOriginalFilename(), image.getBytes());
                    save.setImageFile(imageFile);
                }
                //이미지 저장 종료

                //첨부파일 저장 시작
                List<AttachFile> attachFiles = files.stream().map(mf -> {
                    AttachFile attachFile = null;
                    try {
                        attachFile = uploadFileUtil.fileUpload(mf.getOriginalFilename(), mf.getBytes());
                        attachFile = attachFileRepository.save(attachFile);
                        return attachFile;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());
                save.setAttachFile(attachFiles);
                //첨부파일 저장 종료

                map.put("id", save.getId());
                map.put("msg", "저장 되었습니다.");
                map.put("result", "success");

            } else {
                map.put("msg", "권한이 없습니다. 관리자가 아닙니다.");
                map.put("result", "fail");
            }
        } catch (Exception e) {
            map.put("result", "fail");
            map.put("msg", "exception");
            return 	map;
        }

        return map;
    };

    public Map<String, Object> modify(Program program, List<MultipartFile> files, MultipartFile image) {
        Map<String, Object> map = new HashMap<>();
        final Optional<AuthDTO> auth = Optional.ofNullable(authService.getAuth());
        try {
            if(auth.map(AuthDTO::isAdmin).orElse(false)) {
                Optional<Program> modify = programRepository.findById(program.getId());
                modify.ifPresent(p ->{
                    p.setAddress(program.getAddress());
                    p.setAddressDetail(program.getAddressDetail());
                    p.setAddressNumber(program.getAddressNumber());
                    p.setContent(program.getContent());
                    p.setStrAppDate(program.getStrAppDate());
                    p.setEndAppDate(program.getEndAppDate());
                    p.setStrDate(program.getStrDate());
                    p.setEndDate(program.getEndDate());
                    p.setKind(program.getKind());
                    p.setOption(program.getOption());
                    p.setTitle(program.getTitle());
                    p.setOpenType(program.getOpenType());
                    p.setRegNumber(program.getRegNumber());
                    p.setRegDate(program.getRegDate());
                    p.setRegId(auth.get().getMemberId());
                    p.setAddressNumber(program.getAddressNumber());
                    p.setLimitNumber(program.getLimitNumber());
                });
                //Program modify = programRepository.save(program);

                //이미지 저장 시작
                if(image != null){
                    AttachFile imageFile = uploadResourceImageFileUtil.imageUploadWithThumbnail(image.getOriginalFilename(), image.getBytes());
                    modify.get().setImageFile(imageFile);
                }
                //이미지 저장 종료

                //첨부파일 저장 시작
                files.stream().forEach(mf -> {
                    AttachFile attachFile = null;
                    try {
                        attachFile = uploadFileUtil.fileUpload(mf.getOriginalFilename(), mf.getBytes());
                        attachFile = attachFileRepository.save(attachFile);
                        modify.get().getAttachFile().add(attachFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                //첨부파일 저장 종료

                map.put("msg", "저장 되었습니다.");
                map.put("result", "success");
            }else {
                map.put("msg", "권한이 없습니다. 관리자가 아닙니다.");
                map.put("result", "fail");
            }

        } catch (Exception e) {
            map.put("result", "fail");
            map.put("msg", "exception");
            return 	map;
        }

        return map;
    }

    public Map<String, Object> delete(long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Optional<Program> program = programRepository.findById(id);
        try {
            if(authService.getAuth().isAdmin()) {
                program.ifPresent(p -> {
                    p.setDeleteYN("Y");
                    for(AttachFile aFile : p.getAttachFile()){
                        File file = new File(aFile.getFullPath()+File.separator+aFile.getVirtualName());
                        if(file.exists()) {
                            file.delete();
                        }
                        attachFileRepository.delete(aFile);
                    }
                });
                map.put("msg", "삭제 되었습니다.");
                map.put("result", "success");
            }else {
                map.put("msg", "권한이 없습니다. 관리자가 아닙니다.");
                map.put("result", "fail");
            }
        } catch (Exception e) {
            map.put("result", "fail");
            map.put("msg", "exception");
            return 	map;
        }
        return map;
    }

    public Map<String, Object> authFileDelete(Long programId, Long fileId) {
        HashMap<String, Object> map = new HashMap<>();

        Optional<AuthDTO> auth = Optional.ofNullable(authService.getAuth());
        Optional<Program> program = programRepository.findById(programId);
        Optional<AttachFile> attachFile = attachFileRepository.findById(fileId);

        if(auth.map(AuthDTO::isAdmin).orElse(false)) {
            List<AttachFile> attachFiles = attachFile.map(af->{
                File file = new File(af.getFullPath()+File.separator+af.getVirtualName());
                if(file.exists()) {
                    file.delete();
                }
                program.get().getAttachFile().remove(af);

                return program.get().getAttachFile();
            }).get();
            map.put("result", "success");
            map.put("msg", "삭제되었습니다.");
            map.put("attachFile", attachFiles);
        }else {
            map.put("result", "fail");
            map.put("msg", "관리자만 삭제할 수 있습니다.");
        }
        return map;

    }
}
