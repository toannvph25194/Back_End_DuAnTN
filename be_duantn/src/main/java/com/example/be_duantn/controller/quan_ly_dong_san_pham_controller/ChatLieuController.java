package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.entity.ChatLieu;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.ChatLieuServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/chatlieu")
public class ChatLieuController {
    @Autowired
    ChatLieuServiceImpl chatLieuService;

    //api Load Table
    @GetMapping("/hienthitatcachatlieu")
    public ResponseEntity<?> getAllThuongHieu(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(chatLieuService.getChatLieu(page));
    }
    @GetMapping("/hienthitatcachatlieutheid")
    public ResponseEntity<?> getAllsize(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(chatLieuService.getdanhmucById(id));
    }
    //add
    @PostMapping("/add-chatlieu")
    public ResponseEntity<?> addChatLieu(@Valid @RequestBody ChatLieuRequest chatLieuRequest) {
        return ResponseEntity.ok(chatLieuService.addChatLieu(chatLieuRequest));
    }
    @PutMapping("/update-chatlieu/{id}")
    public ResponseEntity<ChatLieu> capNhatchatlieu(@PathVariable UUID id, @RequestBody ChatLieuRequest chatLieuRequest) {
        return ResponseEntity.ok(chatLieuService.updateChatlieu(id, chatLieuRequest));
    }
    @PutMapping("/ctt-chatlieu/{id}")
    public ResponseEntity<ChatLieu> changeStatus(@PathVariable(value = "id") UUID id, @RequestParam int trangthai) {
        try {
            ChatLieu chatLieu = chatLieuService.chuyenTrangThai(id, trangthai);
            // return ResponseEntity.ok(updatedMauSac);
            if (chatLieu != null) {
                return ResponseEntity.ok(chatLieu);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Invalid UUID format
        }
    }
}
