package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;


import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.entity.XuatXu;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.DanhMucServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin-danhmuc")
public class DanhMucController {
    @Autowired
    DanhMucServiceImpl danhMucService;

    //api Load Table
    @GetMapping("/hienthitatcadanhmuc")
    public ResponseEntity<?> getAllThuongHieu(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(danhMucService.getDanhMuc(page));
    }
    @GetMapping("/hienthitatcadanhmuctheoid")
    public ResponseEntity<?> getAllsize(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(danhMucService.getdanhmucById(id));
    }
    //add
    @PostMapping("/add-danhmuc")
    public ResponseEntity<?> addChatLieu(@Valid @RequestBody DanhMucRequest danhMucRequest) {
        return ResponseEntity.ok(danhMucService.addDanhMuc(danhMucRequest));
    }

    @PutMapping("/update-danhmuc/{id}")
    public ResponseEntity<DanhMuc> capNhatdanhmuc(@PathVariable UUID id, @RequestBody DanhMucRequest danhMucRequest) {
        return ResponseEntity.ok(danhMucService.updateDanhmuc(id, danhMucRequest));
    }
    @PutMapping("/ctt-danhmuc/{id}")
    public ResponseEntity<DanhMuc> changeStatus(@PathVariable(value = "id") UUID id, @RequestParam int trangthai) {
        try {
            DanhMuc danhMuc = danhMucService.chuyenTrangThai(id, trangthai);
            // return ResponseEntity.ok(updatedMauSac);
            if (danhMuc != null) {
                return ResponseEntity.ok(danhMuc);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Invalid UUID format
        }
    }
}
