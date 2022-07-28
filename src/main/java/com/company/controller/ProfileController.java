package com.company.controller;


import com.company.dto.ProfileDTO;
import com.company.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody ProfileDTO dto){

        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "100") int size){
        return ResponseEntity.ok(profileService.getList(page, size));
    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(profileService.getById(id));
    }

    @GetMapping("/adm/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name")String name){
        return ResponseEntity.ok(profileService.getByName(name));
    }

    @GetMapping("/adm/surname/{surname}")
    public ResponseEntity<?> getBySurname(@PathVariable("surname") String surname){
        return ResponseEntity.ok(profileService.getBySurname(surname));
    }

    @GetMapping("/adm/username/{username}")
    public ResponseEntity<?> getByUserName(@PathVariable("username") String userName){
        return ResponseEntity.ok(profileService.getByUserName(userName));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        return ResponseEntity.ok(profileService.delete(id));
    }

}
