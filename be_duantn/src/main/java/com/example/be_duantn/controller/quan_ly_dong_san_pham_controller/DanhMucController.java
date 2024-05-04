package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;


import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.DanhMucServiceImpl;
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
@RequestMapping("/api/admin-danhmuc")
public class DanhMucController {
    @Autowired
    DanhMucServiceImpl danhMucService;

    //api Load Table
    @GetMapping("/hienthitatcadanhmuc")
    public ResponseEntity<?> getAllThuongHieu(){
        return ResponseEntity.ok(danhMucService.getDanhMuc());
    }
    //add
    @PostMapping("/add-danhmuc")
    public ResponseEntity<?> addChatLieu(@Valid @RequestBody DanhMucRequest danhMucRequest) {
        return ResponseEntity.ok(danhMucService.addDanhMuc(danhMucRequest));
    }
}
