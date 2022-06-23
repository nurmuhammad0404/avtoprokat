package com.company.controller;

import com.company.dto.DistrDTO;
import com.company.dto.DriverDTO;
import com.company.service.DistrService;
import com.company.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody DriverDTO dto){
        System.out.println(dto);
        return ResponseEntity.ok(driverService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "100") int size){
        return ResponseEntity.ok(driverService.getList(page, size));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody DriverDTO dto){
        return ResponseEntity.ok(driverService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(driverService.delete(id));
    }
}
