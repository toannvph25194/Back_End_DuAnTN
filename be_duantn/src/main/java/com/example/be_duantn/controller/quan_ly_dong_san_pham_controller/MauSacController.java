package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.ChatLieuServiceImpl;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.MauSacServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin-mausac")
public class MauSacController {
    @Autowired
    MauSacServiceImpl mauSacService;

    //api Load Table
    @GetMapping("/hienthitatcamausac")
    public ResponseEntity<?> getAllmausac() {
        return ResponseEntity.ok(mauSacService.getMauSac());
    }

}
