package com.eum.web;

import com.eum.notice.entity.Notice;
import com.eum.program.dto.ProgramSearchDTO;
import com.eum.program.entity.Program;
import com.eum.program.service.ProgramService;
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
public class ProgramController {

    @Autowired
    ProgramService programService;

    @RequestMapping(value = "/program", method = RequestMethod.GET)
    public Map<String, Object> list(ProgramSearchDTO programSearchDTO) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", programService.list(programSearchDTO));
        return map;
    }

    @RequestMapping(value = "/program/{id}", method = RequestMethod.GET)
    public Map<String, Object> one(@PathVariable long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", programService.one(id));
        return map;
    }

    @RequestMapping(value = "/program", method = RequestMethod.POST, consumes = "multipart/form-data")
    public Map<String, Object> insert(@RequestPart Program program, MultipartHttpServletRequest mtfRequest) throws IOException, Exception {

        List<MultipartFile> files = mtfRequest.getFiles("files");
        MultipartFile image = mtfRequest.getFile("image");
        return programService.save(program,files,image);
    }

    @RequestMapping(value="/program/{id}", method=RequestMethod.POST, consumes = {"multipart/mixed","multipart/form-data"})
    public Map<String, Object> modify(@PathVariable Long id, @RequestPart Program program, MultipartHttpServletRequest mtfRequest){

        List<MultipartFile> files = mtfRequest.getFiles("files");
        MultipartFile image = mtfRequest.getFile("image");
        return programService.modify(program, files,image);
    }

    @RequestMapping(value="/program/{id}", method=RequestMethod.DELETE)
    public Map<String, Object> delete(@PathVariable long id){
        return programService.delete(id);
    }

    @RequestMapping(value = "/program/file/{id}/{fileId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteFile(@PathVariable Long id, @PathVariable Long fileId){
        return  programService.authFileDelete(id,fileId);
    }
}
