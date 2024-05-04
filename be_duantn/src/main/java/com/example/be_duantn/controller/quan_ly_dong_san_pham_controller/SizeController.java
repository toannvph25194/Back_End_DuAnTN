package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.SizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin-size")
public class SizeController {
    @Autowired
    SizeServiceImpl sizeService;

    //api Load Table
    @GetMapping("/hienthitatcasize")
    public ResponseEntity<?> getAllSize(){
        return ResponseEntity.ok(sizeService.getSize());
    }

}
