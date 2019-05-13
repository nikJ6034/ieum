package com.eum.program.service;

import com.eum.attachFile.entity.AttachFile;
import com.eum.auth.dto.AuthDTO;
import com.eum.config.oAuth2.AuthService;
import com.eum.program.dto.ProgramApplicationSearchDTO;
import com.eum.program.dto.ProgramDTO;
import com.eum.program.entity.Program;
import com.eum.program.entity.ProgramApplication;
import com.eum.program.repository.ProgramApplicationRepository;
import com.eum.program.repository.ProgramApplicationRepositoryDsl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramApplicationService {
    @Autowired
    AuthService authService;
    @Autowired
    ProgramApplicationRepository applicationRepository;
    @Autowired
    ProgramApplicationRepositoryDsl programApplicationRepositoryDsl;
    @Autowired
    ProgramService programService;

    public Page<ProgramApplication> list(ProgramApplicationSearchDTO programApplicationSearchDTO){
        return programApplicationRepositoryDsl.programApplicationList(programApplicationSearchDTO);
    }

    public ProgramApplication one(Long programApplicationId){
        return applicationRepository.findById(programApplicationId).get();
    }

    public Map<String, Object > findByMemberProgram(Long programId){
        Program program = new Program();
        program.setId(programId);
        Map<String, Object >  map = new HashMap<>();
        Optional<AuthDTO> auth = Optional.ofNullable(authService.getAuth());
        Long memberId = auth.map(AuthDTO::getMemberId).orElse(null);
        if(applicationRepository.exists(memberId, program) > 0){
            map.put("exists",true);
            map.put("programApplication", applicationRepository.findByMemberProgram(memberId,program));
        }else{
            map.put("exists",false);
            map.put("programApplication", new ProgramApplication());
        }
        return map;
    }

    public Map<String, Object> save(ProgramApplication programApplication){
        Map<String, Object> map = new HashMap<>();
        Optional<AuthDTO> auth = Optional.ofNullable(authService.getAuth());
        Long memberId = auth.map(AuthDTO::getMemberId).orElse(null);
        ProgramDTO program = programService.one(programApplication.getProgram().getId());
        try {
            if(applicationRepository.exists(memberId, programApplication.getProgram()) > 0){
                map.put("msg", "이미 신청하신 프로그램 입니다.");
                map.put("result", "exists");
            }else if(program.getLimitNumber() != 0 && program.getLimitNumber() <= applicationRepository.appCount(programApplication.getProgram())){
                map.put("msg", "신청인원을 초과하였습니다.");
                map.put("result", "full");
            }else{
                programApplication.setMemberId(memberId);
                ProgramApplication save = applicationRepository.save(programApplication);

                map.put("id", save.getId());
                map.put("msg", "저장 되었습니다.");
                map.put("result", "success");
            }


        } catch (Exception e) {
            map.put("result", "fail");
            map.put("msg", "exception");
            return 	map;
        }

        return map;
    };

    public Map<String, Object> modify(ProgramApplication programApplication){
        Map<String, Object> map = new HashMap<>();
        AuthDTO authDTO = authService.getAuth();
        try {

            if(authDTO.permission(programApplication.getMemberId())){
                ProgramApplication save = applicationRepository.save(programApplication);

                map.put("msg", "저장 되었습니다.");
                map.put("result", "success");
            }else{
                map.put("msg", "권한이 없습니다.");
                map.put("result", "fail");
            }


        } catch (Exception e) {
            map.put("result", "fail");
            map.put("msg", "exception");
            return 	map;
        }

        return map;
    };

    public Map<String, Object> delete(long programApplicationId){
        Map<String, Object> map = new HashMap<>();
        AuthDTO authDTO = authService.getAuth();
        Optional<ProgramApplication> programApplication = applicationRepository.findById(programApplicationId);
        try {

            if(authDTO.permission(programApplication.map(ProgramApplication::getMemberId).orElse(0L))){
                programApplication.ifPresent(pa ->{
                    pa.setDeleteYN("Y");
                });

                map.put("msg", "삭제 되었습니다.");
                map.put("result", "success");
            }else{
                map.put("msg", "권한이 없습니다.");
                map.put("result", "fail");
            }


        } catch (Exception e) {
            map.put("result", "fail");
            map.put("msg", "exception");
            return 	map;
        }

        return map;
    };
}
