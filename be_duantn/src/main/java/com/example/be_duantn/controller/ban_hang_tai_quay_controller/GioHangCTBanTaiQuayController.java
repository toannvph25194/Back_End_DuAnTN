package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHangCTRespon;
import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.GioHangCTBanTaiQuayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/ghct/ban-tai-quay")
public class GioHangCTBanTaiQuayController {

    @Autowired
    GioHangCTBanTaiQuayServiceImpl gioHangCTBanTaiQuayService;

    //Todo load giỏ hàng chi tiết tại quầy theo idgh của khách
    @GetMapping("/load-ghct")
    public ResponseEntity<?> LoadSPBanTaiQuay(@RequestParam("idgh") UUID idgh){
        try {
            return ResponseEntity.ok(gioHangCTBanTaiQuayService.loadGHCTBanTaiQuay(idgh));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load ghct bán tại quầy !");
        }
    }

    // ToDo thêm sản phẩm vào giỏ hàng chi tiết bán tại quầy
    @PostMapping("/them-san-pham-ghct")
    public ResponseEntity<?> ThemSPVaoGHCTBanTaiQuay(Principal principal, @RequestParam("idgh")UUID idgh, @RequestParam("idspct") UUID idspct,
                                              int soluong, Double dongiakhigiam){
        try {
            if (principal != null){
                return  ResponseEntity.ok(gioHangCTBanTaiQuayService.ThemSPVaoGHCTBanTaiQuay(principal, idgh, idspct, soluong, dongiakhigiam));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhân viên thêm sản phẩm vào giỏ hàng chi tiết bán tại quầy !");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Lỗi thêm sản phẩm vào ghct bán tại quầy !");
        }
    }

    //Todo Update giỏ hàng chi tiết
    @PutMapping("/update-so-luong-ghct")
    public ResponseEntity<?> updateSPGioHang(Principal principal, @RequestParam("idghct") UUID idghct, @RequestParam("soluong") Integer soluong){
        try {
            if (principal != null){
                return  ResponseEntity.ok(gioHangCTBanTaiQuayService.UpdateGioHangCTBanTaiQuay(principal, idghct, soluong));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhân viên update số lượng vào ghct bán tại quầy !");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon("Lỗi không thể cập nhật ghct bán tại quầy !"));
        }
    }

    //Todo Delete giỏ hàng chi tiết
    @DeleteMapping("/delete-sp-ghct")
    public ResponseEntity<?> deleteSPGioHangCT(Principal principal, @RequestParam("idghct") UUID idghct){
        try {
            if (principal != null){
                return ResponseEntity.ok(gioHangCTBanTaiQuayService.DeleteGioHangCTBanTaiQuay(principal, idghct));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhân viên delete ghct bán tại quầy !");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon("Lỗi không thể xóa sản phẩm trong ghct bán tại quầy !"));
        }
    }

}
