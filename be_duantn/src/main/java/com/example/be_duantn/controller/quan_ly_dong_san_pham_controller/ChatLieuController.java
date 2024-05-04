package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.ChatLieuServiceImpl;
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
@RequestMapping("/api/admin-chatlieu")
public class ChatLieuController {
    @Autowired
    ChatLieuServiceImpl chatLieuService;

    //api Load Table
    @GetMapping("/hienthitatcachatlieu")
    public ResponseEntity<?> getAllThuongHieu(){
        return ResponseEntity.ok(chatLieuService.getChatLieu());
    }
    //add
    @PostMapping("/add-chatlieu")
    public ResponseEntity<?> addChatLieu(@Valid @RequestBody ChatLieuRequest chatLieuRequest) {
        return ResponseEntity.ok(chatLieuService.addChatLieu(chatLieuRequest));
    }
}
