package com.eum.web;

import com.eum.program.dto.ProgramApplicationSearchDTO;
import com.eum.program.dto.ProgramSearchDTO;
import com.eum.program.entity.Program;
import com.eum.program.entity.ProgramApplication;
import com.eum.program.service.ProgramApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/web")
public class ProgramApplicationController {

    @Autowired
    ProgramApplicationService programApplicationService;


    @RequestMapping(value = "/programApp", method = RequestMethod.GET)
    public Map<String, Object> list(ProgramApplicationSearchDTO programApplicationSearchDTO) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", programApplicationService.list(programApplicationSearchDTO));
        return map;
    }

    @RequestMapping(value = "/programApp/{id}", method = RequestMethod.GET)
    public Map<String, Object> one(@PathVariable long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", programApplicationService.one(id));
        return map;
    }

    @RequestMapping(value = "/programApp", method = RequestMethod.POST)
    public Map<String, Object> insert(@RequestBody ProgramApplication programApplication) {
        return programApplicationService.save(programApplication);
    }

    @RequestMapping(value = "/programApp/{id}", method = RequestMethod.PUT)
    public Map<String, Object> modify(@PathVariable Long id,@RequestBody ProgramApplication programApplication) {
        return programApplicationService.modify(programApplication);

    }

    @RequestMapping(value = "/programApp/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@PathVariable long id) {

        return programApplicationService.delete(id);

    }

    @RequestMapping(value = "/programApp/exists/{programId}", method = RequestMethod.GET)
    public Map<String, Object> exists(@PathVariable Long programId) {
        return programApplicationService.findByMemberProgram(programId);
    }

}
