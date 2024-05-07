package com.example.be_duantn.controller.mua_hang_online_controller.hoa_don_controller;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.hoa_don_respon.VNPayOnlineRespon;
import com.example.be_duantn.service.mua_hang_online_service.Impl.hoa_don_service_impl.VNPayOnlineServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ol/vnpay")
public class VNPayOnlineController {

    @Autowired
    VNPayOnlineServiceImpl vnPayOnlineService;

    @PostMapping("/payment")
    public ResponseEntity<?> creatPayment(HttpServletRequest req, @RequestParam("tongtienamout") Long amountParam) {
        try{
            return ResponseEntity.ok(vnPayOnlineService.callPaymentApiOnline(req,amountParam));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi tạo cổng thanh toán vnpay !");
        }
    }

}
