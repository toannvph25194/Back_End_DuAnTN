package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.GioHangBanTaiQuayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/gio-hang/ban-tai-quay")
public class GioHangBanTaiQuayController {
    @Autowired
    GioHangBanTaiQuayServiceImpl gioHangBanTaiQuayService;

    // ToDo tìm kiếm giỏ hàng tại quầy theo id khách hàng
    @GetMapping("/tim-gio-hang")
    public ResponseEntity<?> LoadSPBanTaiQuay(@RequestParam("idkh")UUID idkh){
        try {
            return ResponseEntity.ok(gioHangBanTaiQuayService.TimKiemGioHangTaiQuay(idkh));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi tìm kiếm giỏ hàng bán tại quầy !");
        }
    }
}
