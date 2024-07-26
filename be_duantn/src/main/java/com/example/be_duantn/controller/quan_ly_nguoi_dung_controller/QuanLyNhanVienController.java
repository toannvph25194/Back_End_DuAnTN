package com.example.be_duantn.controller.quan_ly_nguoi_dung_controller;

import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienRegisterRequest;
import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.NhanVienUpdateRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienMessageResponse;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl.QuanLyNhanVienServiceImpl;
import com.example.be_duantn.service.quan_ly_nguoi_dung_service.QuanLyNhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/quan-ly-nhan-vien/")
public class QuanLyNhanVienController {
    @Autowired
    private QuanLyNhanVienService nhanVienService;

    @Autowired
    private QuanLyNhanVienServiceImpl service;

    @GetMapping("all")
    public Page<NhanVien> getAllNhanVien(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return nhanVienService.getAllNhanVien(page, size);
    }

    @GetMapping("{id}")
    public NhanVien getNhanVienById(@PathVariable UUID id) {
        return nhanVienService.getNhanVienById(id);
    }

    @GetMapping("search")
    public Page<NhanVien> searchNhanVien(@RequestParam(required = false) String search,
                                         @RequestParam(required = false) Integer trangthai,
                                         Pageable pageable) {
        return nhanVienService.searchNhanVien(search, trangthai, pageable);
    }

    @PutMapping("update-nhan-vien")
    public ResponseEntity<NhanVien> updateNhanVien(
            @RequestParam(name = "id") UUID id,
            @Valid @RequestBody NhanVienUpdateRequest nhanVien
    ) {
        NhanVien updatedNhanVien = nhanVienService.updateNhanVien(nhanVien, id);
        return ResponseEntity.ok(updatedNhanVien);
    }

    @GetMapping("check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = nhanVienService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("check-phone")
    public ResponseEntity<Boolean> checkPhoneExists(@RequestParam String phone) {
        boolean exists = nhanVienService.phoneExists(phone);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("{id}/updateTrangThai")
    public ResponseEntity<NhanVien> updateStatus(@PathVariable String id, @RequestBody NhanVienRegisterRequest request) {
        try {
            UUID uuid = UUID.fromString(id);
            NhanVien updatedNhanVien = nhanVienService.updateStatus(uuid, request.getTrangthai());
            if (updatedNhanVien != null) {
                return ResponseEntity.ok(updatedNhanVien);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Invalid UUID format
        }
    }

    @PostMapping("themNhanVien")
    public ResponseEntity<NhanVienMessageResponse> register(@Valid @RequestBody NhanVienRegisterRequest nhanVienRegisterRequest) {
        return new ResponseEntity<>(service.themNhanVien(nhanVienRegisterRequest), HttpStatus.CREATED);
    }
}
