package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHangCTRespon;
import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.HoaDonCTBanTaiQuayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/hoa-don-chi-tiet/ban-tai-quay")
public class HoaDonCTBanTaiQuayController {

    @Autowired
    HoaDonCTBanTaiQuayServiceImpl hoaDonCTBanTaiQuayService;

    //Todo load hóa đơn chi tiết tại quầy theo idhd của khách
    @GetMapping("/load-hdct")
    public ResponseEntity<?> loadHDCTBanTaiQuay(@RequestParam("idhoadon") UUID idhoadon){
        try {
            return ResponseEntity.ok(hoaDonCTBanTaiQuayService.loadHDCTBanTaiQuay(idhoadon));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load hdct bán tại quầy !");
        }
    }

    // ToDo thêm sản phẩm vào hóa đơn chi tiết bán tại quầy
    @PostMapping("/them-san-pham-hdct")
    public ResponseEntity<?> ThemSPVaoHDCTBanTaiQuay(Principal principal, @RequestParam("idhoadon")UUID idhoadon, @RequestParam("idspct") UUID idspct,
                                                     int soluong, Double dongiakhigiam){
        try {
            if (principal != null){
                return  ResponseEntity.ok(hoaDonCTBanTaiQuayService.ThemSPVaoHDCTBanTaiQuay(principal, idhoadon, idspct, soluong, dongiakhigiam));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhân viên thêm sản phẩm vào hóa đơn chi tiết bán tại quầy !");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Lỗi thêm sản phẩm vào hdct bán tại quầy !");
        }
    }

    //Todo Update hóa đơn chi tiết
    @PutMapping("/update-so-luong-hdct")
    public ResponseEntity<?> UpdateHDCTBanTaiQuay(Principal principal, @RequestParam("idhdct") UUID idhdct, @RequestParam("soluong") Integer soluong){
        try {
            if (principal != null){
                return  ResponseEntity.ok(hoaDonCTBanTaiQuayService.UpdateHDCTBanTaiQuay(principal, idhdct, soluong));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhân viên update số lượng vào hdct bán tại quầy !");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon("Lỗi không thể cập nhật hdct bán tại quầy !"));
        }
    }

    //Todo Delete hóa đơn chi tiết
    @DeleteMapping("/delete-sp-hdct")
    public ResponseEntity<?> DeleteHDCTBanTaiQuay(Principal principal, @RequestParam("idhdct") UUID idhdct){
        try {
            if (principal != null){
                return ResponseEntity.ok(hoaDonCTBanTaiQuayService.DeleteHDCTBanTaiQuay(principal, idhdct));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhân viên delete hdct bán tại quầy !");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon("Lỗi không thể xóa sản phẩm trong hdct bán tại quầy !"));
        }
    }
}
