package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.HoaDonBanTaiQuayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/hoa-don/ban-tai-quay")
public class HoaDonBanTaiQuayConTroller {
    @Autowired
    HoaDonBanTaiQuayServiceImpl hoaDonBanTaiQuayService;

    // ToDo load hóa đơn bán tại quầy
    @GetMapping("/load")
    public ResponseEntity<?> LoadHoaDonBanTaiQuay(){
        try {
            return ResponseEntity.ok(hoaDonBanTaiQuayService.LoadHoaDonTaiQuay());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load hóa đơn bán tại quầy !");
        }
    }

    // ToDo tìm kiếm hóa đơn bán tại quầy
    @GetMapping("/tim-kiem-hoa-don")
    public ResponseEntity<?> TimKiemHoaDonBanTaiQuay(@RequestParam("mahoadon") String mahoadon){
        try {
            return ResponseEntity.ok(hoaDonBanTaiQuayService.TimKiemHoaDonTaiQuay(mahoadon));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi tìm kiếm hóa đơn bán tại quầy !");
        }
    }

    // ToDo tạo hóa đơn bán tại quầy
    @PostMapping("/tao-hoa-don")
    public ResponseEntity<?> TaoHoaDonTaiQuay(Principal principal){
       try {
           if(principal != null){
               return ResponseEntity.ok(hoaDonBanTaiQuayService.TaoHoaDonTaiQuay(principal));
           }else {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy thông tin nhân viên tạo hóa đơn bán tại quầy !");
           }
       }catch (Exception e){
           return ResponseEntity.badRequest().body("Lỗi tạo hóa đơn bán tại quầy !");
       }
    }

    // ToDo hủy hóa đơn bán tại quầy
    @PutMapping("/huy-hoa-don")
    public ResponseEntity<?> HuyHoaDonTaiQuay(@RequestParam("idhoadon") UUID idhoadon, Principal principal){
        try {
            if(principal != null){
                return ResponseEntity.ok(hoaDonBanTaiQuayService.HuyHoaDonTaiQuay(idhoadon, principal));
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy thông tin nhân viên tạo hóa đơn bán tại quầy !");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Lỗi hủy hóa đơn bán tại quầy !");
        }
    }
}
