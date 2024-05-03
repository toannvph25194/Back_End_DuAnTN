package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.service.mua_hang_online_service.Impl.ThongTinNguoiDungServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/check-out/thong-tin-nguoi-dung")
public class ThongTinNguoiDungController {
    @Autowired
    ThongTinNguoiDungServiceImpl thongTinNguoiDungService;

    @GetMapping("/load/thong-tin")
    public ResponseEntity<?> finByTTNguoiDung(@RequestParam("idkh") UUID idkh){
        try {
            if (idkh != null) {
                return ResponseEntity.ok(thongTinNguoiDungService.findByKhachhang(idkh));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy tài khoản người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi finby tài khoản người dùng !");
        }
    }
}
