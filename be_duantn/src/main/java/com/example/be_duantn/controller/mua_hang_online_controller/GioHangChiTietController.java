package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHangCTRespon;
import com.example.be_duantn.service.mua_hang_online_service.Impl.GioHangChiTietServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/gio-hang-chi-tiet")
public class GioHangChiTietController {

    @Autowired
    GioHangChiTietServiceImpl gioHangChiTietService;

    // ToDo load ghct theo id giỏ hàng
    @GetMapping("/load")
    public ResponseEntity<?> loadGioHangChiTiet(@RequestParam("idgh")UUID idgh){
        try {
            return ResponseEntity.ok(gioHangChiTietService.loadGioHangChiTiet(idgh));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi load ghct theo idgh !"));
        }
    }

    // ToDo Add sản phẩm vào ghct
    @PostMapping("/add-san-pham")
    public ResponseEntity<?> addSPGioHangCT(@RequestParam("idgh") UUID idgh , @RequestParam("idspct") UUID idspct, @RequestParam("soluong") Integer soluong,
                                            @RequestParam(value = "dongiakhigiam", required = false) Double dongiakhigiam){
        try {
            if (dongiakhigiam == null){
                dongiakhigiam = null;
            }
            return ResponseEntity.ok(gioHangChiTietService.addSPGioHangCT(idgh,idspct, soluong, dongiakhigiam));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon ("Lỗi không thêm được sp vào ghct !"));
        }
    }

    // ToDo Update giỏ hàng chi tiết
    @PutMapping("/update-so-luong")
    public ResponseEntity<?> updateSPGioHangCT(@RequestParam("idghct") UUID idghct, @RequestParam("soluong") Integer soluong){
        try {
            return ResponseEntity.ok(gioHangChiTietService.updateGioHangCT(idghct, soluong));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon ("Lỗi không thể cập nhật ghct !"));
        }
    }

    // ToDo Delete giỏ hàng chi tiết
    @DeleteMapping("/delete-san-pham")
    public ResponseEntity<?> deleteSPGioHangCT(@RequestParam("idghct") UUID idghct){
        try {
            return ResponseEntity.ok(gioHangChiTietService.deleteGioHangCT(idghct));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon ("Lỗi không thế xóa 1 sp trong ghct !"));
        }
    }

    // ToDo Delete all giỏ hàng chi tiết
    @DeleteMapping("/delete-all-san-pham")
    public ResponseEntity<?> deleteAllSPGioHangCT(@RequestParam("idgh") UUID idgh){
        try {
            return ResponseEntity.ok(gioHangChiTietService.deleteAllGioHangCT(idgh));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon ("Lỗi k thể xóa tất cả sp trong ghct !"));
        }
    }
}
