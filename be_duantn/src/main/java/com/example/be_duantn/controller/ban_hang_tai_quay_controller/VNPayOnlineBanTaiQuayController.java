package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.VNPayOnlineBanTaiQuayServiceImpl;
import com.example.be_duantn.service.mua_hang_online_service.Impl.hoa_don_service_impl.VNPayOnlineServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Admin/BanTaiQuay/vnpay")
public class VNPayOnlineBanTaiQuayController {

    @Autowired
    VNPayOnlineBanTaiQuayServiceImpl vnPayOnlineService;

    @PostMapping("/payment")
    public ResponseEntity<?> creatPayment(HttpServletRequest req, @RequestParam("tongtienamout") Long amountParam) {
        try{
            return ResponseEntity.ok(vnPayOnlineService.callPaymentApiOnline(req,amountParam));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi tạo cổng thanh toán vnpay !");
        }
    }

}
