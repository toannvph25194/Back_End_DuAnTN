package com.example.be_duantn.controller.quan_ly_nguoi_dung_controller;

import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienRegisterRequest;
import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.NhanVienRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienMessageResponse;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl.QuanLyNhanVienServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/quan-ly-nhan-vien/")
public class QuanLyNhanVienController {

    @Autowired
    private QuanLyNhanVienServiceImpl service;

    // ToDo check email
    @GetMapping("check-email")
    public ResponseEntity<?> CheckEmailExists(@RequestParam String email) {
        boolean exists = service.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    // ToDo check số điện thoại
    @GetMapping("check-phone")
    public ResponseEntity<?> CheckPhoneExists(@RequestParam String phone) {
        boolean exists = service.phoneExists(phone);
        return ResponseEntity.ok(exists);
    }

    // ToDo load nhân viên phân trang
    @GetMapping("hien-thi")
    public ResponseEntity<?> GetAllNhanVien(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        try {
            return ResponseEntity.ok(service.LoadAllNhanVien(page));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load quản lý nhân viên !");
        }
    }

    // ToDo find by nhân viên theo id
    @GetMapping("find-nhan-vien")
    public ResponseEntity<?> FindByNhanVien(@RequestParam("id") UUID id) {
        try {
            return ResponseEntity.ok(service.FindNhanVienById(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi find by quản lý nhân viên !");
        }
    }

    // ToDo lọc nhân viên
    @GetMapping("loc-tieu-chi")
    public ResponseEntity<?> LocNhanVienTieuChi(@RequestParam(defaultValue = "0", value = "page") Integer page, @RequestParam("search") String search) {
        try {
            return ResponseEntity.ok(service.LocNhanVienTieuChi(page, search));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi lọc tiêu chí quản lý nhân viên !");
        }
    }

    // ToDo lọc nhân viên
    @GetMapping("loc-trang-thai")
    public ResponseEntity<?> LocNhanVienTrangThai(@RequestParam(defaultValue = "0", value = "page") Integer page, Integer trangthai) {
        try {
            return ResponseEntity.ok(service.LocNhanVienTrangThai(page, trangthai));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi lọc trạng thái quản lý nhân viên !");
        }
    }

    // ToDo Cập nhật nhân viên
    @PutMapping("update-nhan-vien")
    public ResponseEntity<?> UpdateNhanVien(@RequestBody NhanVienRequest nhanVienRequest) {
        try {
            return ResponseEntity.ok(service.UpdateNhanVien(nhanVienRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi cập nhật quản lý nhân viên !");
        }
    }

    // ToDo cập nhật trạng thái nhân viên
    @PutMapping("update-trang-thai")
    public ResponseEntity<?> UpdateStatus(@RequestParam UUID id, Integer trangthai) {
        try {
            return ResponseEntity.ok(service.UpdateTrangThai(id, trangthai));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi cập nhật trạng thái quản lý nhân viên !");
        }
    }

    // ToDo thêm mới nhân viên
    @PostMapping("them-nhan-vien")
    public ResponseEntity<?> ThemNhanVien(@RequestBody NhanVienRequest nhanVienRequest) {
        try {
            return ResponseEntity.ok(service.ThemNhanVien(nhanVienRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi thêm quản lý nhân viên !");
        }
    }
}
