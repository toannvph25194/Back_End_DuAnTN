package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.dto.request.mua_hang_online_request.ImageTKRequest;
import com.example.be_duantn.dto.request.mua_hang_online_request.TTTaiKhoanRequest;
import com.example.be_duantn.service.mua_hang_online_service.Impl.ThongTinNguoiDungServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/check-out/thong-tin-nguoi-dung")
public class ThongTinNguoiDungController {
    @Autowired
    ThongTinNguoiDungServiceImpl thongTinNguoiDungService;

    // ToDo finby thông tin người dùng lên trang checkout
    @GetMapping("/load/thong-tin")
    public ResponseEntity<?> findByKhachhang(@RequestParam("idkh") UUID idkh){
        try {
            if (idkh != null) {
                return ResponseEntity.ok(thongTinNguoiDungService.findByKhachhang(idkh));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy tài khoản người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi load tài khoản người dùng !");
        }
    }

    // ToDo finby thông tin người dùng lên trang tài khoản
    @GetMapping("/fin-thong-tin")
    public ResponseEntity<?> finByTaiKhoanND(@RequestParam("idkh") UUID idkh){
        try {
            if (idkh != null) {
                return ResponseEntity.ok(thongTinNguoiDungService.finByTaiKhoanND(idkh));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy tài khoản người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi finby tài khoản người dùng !");
        }
    }

    // ToDo update thông tin người dùng trang tài khoản
    @PutMapping("/update-thong-tin")
    public ResponseEntity<?> updateTaiKhoanND(@RequestBody TTTaiKhoanRequest khrequest){
        try {
            if (khrequest.getIdkh() != null) {
                return ResponseEntity.ok(thongTinNguoiDungService.updateTaiKhoanND(khrequest));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy tài khoản người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi update tài khoản người dùng !");
        }
    }

    // ToDo upload image thông tin người dùng trang tài khoản
    @PutMapping("/upload-image")
    public ResponseEntity<?> uploadImageTK(@RequestBody ImageTKRequest upload){
        try {
            if (upload.getIdkh() != null) {
                return ResponseEntity.ok(thongTinNguoiDungService.uploadImageTK(upload));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy tài khoản người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi upload image tài khoản người dùng !");
        }
    }
}
