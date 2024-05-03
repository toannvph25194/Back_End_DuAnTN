package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.service.mua_hang_online_service.Impl.VouCherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ol/voucher")
public class VoucherController {

    @Autowired
    VouCherServiceImpl vouCherService;

    // ToDo load voucher lên trang chekout
    @GetMapping("/load")
    public ResponseEntity<?> loadVoucher(){
        try {
            return ResponseEntity.ok(vouCherService.loadVouCher());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi load voucher lên trang checkout !");
        }
    }
}
