package com.company.controller;

import com.company.Util.JwtUtil;
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
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(avtoprokatService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "100") int size,
                                     HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(avtoprokatService.getList(page, size));
    }

//    @GetMapping("/adm/workDate")
//    public ResponseEntity<?> getListByWorkDate(@RequestParam(value = "workDate") String workDate,
//                                               @RequestParam(value = "page", defaultValue = "0") int page,
//                                               @RequestParam(value = "size", defaultValue = "4") int size){
//        return ResponseEntity.ok(avtoprokatService.getListByWorkDate(workDate, page, size));
//    }

}

