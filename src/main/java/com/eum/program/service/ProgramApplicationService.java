package com.eum.program.service;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
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
        Long exists = applicationRepository.exists(memberId, program);
        if(exists > 0 && !auth.get().isAdmin()){
            map.put("exists",true);
            if(exists == 1){
                map.put("programApplication", applicationRepository.findByMemberProgram(memberId,program));
            }else{
                map.put("programApplication", null);
            }
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
            if(applicationRepository.exists(memberId, programApplication.getProgram()) > 0 && !auth.get().isAdmin()){ //관리자는 중복으로 신청할 수 있다.
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
            ProgramApplication programApp = programApplication.get();
            if(authDTO.permission(programApplication.map(ProgramApplication::getMemberId).orElse(0L))){
                if("N".equals(programApplication.get().getDeleteYN())){
                    programApp.setDeleteYN("Y");
                    map.put("msg", "삭제 되었습니다.");
                    map.put("deleteYN", "Y");
                    map.put("result", "success");
                }else{
                    programApp.setDeleteYN("N");
                    map.put("msg", "복구 되었습니다.");
                    map.put("deleteYN", "N");
                    map.put("result", "success");
                }
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

    public List<ProgramApplication> findByProgram(Long programId){
        Program program = new Program();
        program.setId(programId);
        return applicationRepository.findByProgram(program);
    }
}
