package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.service.mua_hang_online_service.Impl.DiaChiNguoiDungServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/check-out/dia-chi-nguoi-dung")
public class DiaChiNguoiDungController {

    @Autowired
    DiaChiNguoiDungServiceImpl ttNguoiDungService;

    @GetMapping("/load/dia-chi")
    public ResponseEntity<?> finByDiaChiNguoiDung(@RequestParam("idkh") UUID idkh){
        try {
            if (idkh != null) {
                return ResponseEntity.ok(ttNguoiDungService.finByDiaChiNguoiDung(idkh));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy địa chỉ người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi finby thông tin người dùng !");
        }
    }
}
