package com.example.be_duantn.controller.mua_hang_online_controller.hoa_don_controller;

import com.example.be_duantn.dto.request.mua_hang_online_request.hoa_don_request.ThongTinHTTTRequest;
import com.example.be_duantn.dto.request.mua_hang_online_request.hoa_don_request.ThongTinThanhToanRequest;
import com.example.be_duantn.service.mua_hang_online_service.Impl.hoa_don_service_impl.HoaDonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/hoa-don")
public class HoaDonController {

    @Autowired
    HoaDonServiceImpl hoaDonService;

    // ToDo thanh toán login
    @PostMapping("/thanh-toan-login")
    public ResponseEntity<?> thanhToanDangNhap(@RequestBody ThongTinThanhToanRequest ttttrequest, @RequestParam(value ="idkh", required = false) UUID idkh) {
        try {
            if (idkh == null){
                idkh = UUID.fromString("11111111-1111-1111-1111-1111111111");
            }
            return ResponseEntity.ok(hoaDonService.ThanhToanLogin(ttttrequest,idkh));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi thanh toán login !");
        }
    }

    // ToDo thanh toán not login
    @PostMapping("/thanh-toan-not-login")
    public ResponseEntity<?> thanhToanDangNhap(@RequestBody ThongTinThanhToanRequest ttttrequest) {
        try {
            return ResponseEntity.ok(hoaDonService.ThanhToanNotLogin(ttttrequest));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi thanh toán not login !");
        }
    }

    // ToDo tạo mới hình thức thanh toán
    @PostMapping("/hinh-thuc-tt/vn-pay")
    public ResponseEntity<?> hinhThucTTVNPay(
            @RequestBody ThongTinHTTTRequest request,
            @RequestParam(name = "idhoadon") UUID idhoadon,
            @RequestParam("sotientra") Double sotientra,
            @RequestParam("magiaodinh") String magiaodinh,
            @RequestParam(value = "idkh", required = false) UUID idkh) {
        try {
            if(idkh == null){
                idkh = UUID.fromString("11111111-1111-1111-1111-1111111111");
            }
            return ResponseEntity.ok(hoaDonService.hinhThucTT(idhoadon, sotientra, magiaodinh,idkh));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi tạo hình thức thanh toán !");
        }
    }
}
