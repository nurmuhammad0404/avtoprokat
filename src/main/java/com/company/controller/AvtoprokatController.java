package com.company.controller;



import com.company.dto.AvtoprokatDTO;
import com.company.enums.ProfileRole;
import com.company.service.AvtoprokatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/avtoprokat")
public class AvtoprokatController {
    @Autowired
    private AvtoprokatService avtoprokatService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody AvtoprokatDTO dto, HttpServletRequest request){
//        String pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(avtoprokatService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "100") int size,
                                     HttpServletRequest request){
//        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(avtoprokatService.getList(page, size));
    }

    @PutMapping("adm/changeStatus/{id}")
    public ResponseEntity<?>update(@PathVariable("id") Integer id){
        return ResponseEntity.ok(avtoprokatService.changeStatus(id));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody AvtoprokatDTO dto){

        return ResponseEntity.ok(avtoprokatService.update(id, dto));

    }

}

