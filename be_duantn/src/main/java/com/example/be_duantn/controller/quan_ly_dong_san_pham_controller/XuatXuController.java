package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.DanhMucServiceImpl;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.XuatXuServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin-xuatxu")
public class XuatXuController {
    @Autowired
    XuatXuServiceImpl xuatXuService;

    //api Load Table
    @GetMapping("/hienthitatcaxuatxu")
    public ResponseEntity<?> getAllThuongHieu(){
        return ResponseEntity.ok(xuatXuService.getXuatXu());
    }
    //add
    @PostMapping("/add-xuatxu")
    public ResponseEntity<?> addChatLieu(@Valid @RequestBody XuatXuRequest xuatXuRequest) {
        return ResponseEntity.ok(xuatXuService.addXuatXu(xuatXuRequest));
    }
}
