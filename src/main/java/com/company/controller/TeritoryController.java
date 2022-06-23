package com.company.controller;

import com.company.dto.TeritoryDTO;
import com.company.service.TeritoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teritory")
public class TeritoryController {

    @Autowired
    private TeritoryService teritoryService;

    @PostMapping("/adm")
    public ResponseEntity<TeritoryDTO> create(@RequestBody TeritoryDTO dto){
        return ResponseEntity.ok(teritoryService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "50") int size){
        return ResponseEntity.ok(teritoryService.getList(page, size));
    }


    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(teritoryService.getById(id));
    }

    @PutMapping("/adm/{id}/{name}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @PathVariable("name") String name){
        return ResponseEntity.ok(teritoryService.update(id,name));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(teritoryService.delete(id));
    }

}
