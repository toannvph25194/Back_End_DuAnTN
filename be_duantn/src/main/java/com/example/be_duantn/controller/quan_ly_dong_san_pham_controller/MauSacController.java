package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ImageRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MauSacRequest;
import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.ChatLieuServiceImpl;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.MauSacServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/mausac")
public class MauSacController {
    @Autowired
    MauSacServiceImpl mauSacService;

    //api Load Table
    @GetMapping("/hienthitatcamausac")
    public ResponseEntity<?> getAllmausac(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(mauSacService.getMauSac(page));
    }

    @PostMapping("/create-mausac")
    public MauSac createMauSac(@RequestBody MauSacRequest mausacRequest) {
        return mauSacService.createMauSac(mausacRequest);
    }

    @GetMapping("/hienthitatcamausactheid")
    public ResponseEntity<?> getAllmausac(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(mauSacService.getMauSacById(id));
    }


    @PutMapping("/update-mausac")
    public ResponseEntity<MauSac> updateNhanVien(
            @RequestParam(name = "id") UUID id,
            @Valid @RequestBody MauSacRequest nhanVien
    ) {
        MauSac updatedNhanVien = mauSacService.updateMauSac(id, nhanVien);
        return ResponseEntity.ok(updatedNhanVien);
    }

    @PutMapping("/ctt-mausac/{mausacid}")
    public ResponseEntity<MauSac> changeStatus(@PathVariable(value = "mausacid") UUID id, @RequestParam int trangthai) {
        try {
            MauSac updatedMauSac = mauSacService.chuyenTrangThai(id, trangthai);
            // return ResponseEntity.ok(updatedMauSac);
            if (updatedMauSac != null) {
                return ResponseEntity.ok(updatedMauSac);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Invalid UUID format
        }
    }

}
