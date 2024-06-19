package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.TongTienBanTaiQuayServiceImpl;
import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.VouCherBanTaiQuayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/Admin/tongtien")
public class TongTienBanTaiQuayController {
    @Autowired
    TongTienBanTaiQuayServiceImpl tongTienBanTaiQuayService;

    // ToDo load voucher lên trang chekout
    @GetMapping("/load")
    public ResponseEntity<?> loadTongTien(
            @RequestParam("id") UUID id
    ){
        try {
            return ResponseEntity.ok(tongTienBanTaiQuayService.tongtien(id));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi load voucher lên trang checkout !");
        }
    }
}
