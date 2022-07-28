package com.company.controller;


import com.company.dto.DistrDTO;
import com.company.service.DistrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/distr")
public class DistrController {
    @Autowired
    private DistrService distrService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody DistrDTO dto, HttpServletRequest request){
//        String pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(distrService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "100") int size){
        return ResponseEntity.ok(distrService.list(page, size));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody DistrDTO dto){
        return ResponseEntity.ok(distrService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(distrService.delete(id));
    }

}
