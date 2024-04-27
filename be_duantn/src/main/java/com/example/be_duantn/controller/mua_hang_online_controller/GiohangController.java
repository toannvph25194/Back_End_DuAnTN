package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHang;
import com.example.be_duantn.entity.GioHang;
import com.example.be_duantn.service.mua_hang_online_service.Impl.GioHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/gio-hang")
public class GiohangController {

    @Autowired
    GioHangServiceImpl gioHangService;

    // ToDo tạo mới giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<?> addGioHang(GioHang gh, @RequestParam(value = "idkh", required = false) UUID idkh){
        try{
            // Nếu idkh == null thì sẽ nhận chuỗi mặc định;
            if (idkh == null){
                idkh = UUID.fromString("11111111-1111-1111-1111-1111111111");
            }
            return ResponseEntity.ok(gioHangService.TaoGioHang(gh, idkh));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Lỗi k tạo được giỏ hàng !");
        }
    }

    // ToDo tìm kiếm giảo hàng theo Idkh và trạng thái gh
    @GetMapping("/tim-kiem/gio-hang")
    public ResponseEntity<?> finByIdGioHang(@RequestParam("idkh") UUID idkh){
        try {
            return ResponseEntity.ok(gioHangService.finByIdGioHang(idkh));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("K tìm thấy gh có trạng thái bằng 1 !");
        }
    }
}
