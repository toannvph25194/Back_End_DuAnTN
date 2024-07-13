package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.service.mua_hang_online_service.Impl.ThongTinHoaDonSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/thong-tin/hoa-don-khach-hang")
public class ThongTinHoaDonController {
    @Autowired
    ThongTinHoaDonSeviceImpl thongTinHoaDonSevice;

    // ToDo load thông tin hóa đơn của khách hàng
    @GetMapping("/load-hoa-don")
    public ResponseEntity<?> LoadTTHoaDonKhachHang(@RequestParam("idkh") UUID idkh, @RequestParam("trangthai") Integer trangthai){
        try {
            return ResponseEntity.ok(thongTinHoaDonSevice.LoadTTHoaDonKhachHang(idkh, trangthai));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load thông tin hóa đơn của khách hàng !");
        }
    }

    // ToDo load thông tin sản phẩm trong hóa đơn của khách hàng
    @GetMapping("/load-san-pham")
    public ResponseEntity<?> LoadTTSPHoaDonKhachHang(@RequestParam("idhoadon") UUID idhoadon){
        try {
            return ResponseEntity.ok(thongTinHoaDonSevice.LoadTTSPHoaDonKhachHang(idhoadon));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load thông tin sản phẩm trong hóa đơn của khách hàng !");
        }
    }

    // ToDo tìm kiếm thông tin hóa đơn của khách theo mahoadon, tennguoinhan, tensanpham
    @GetMapping("/tim-kiem-hoa-don")
    public ResponseEntity<?> TimKiemHoaDonKhachHang(@RequestParam("idkh") UUID idkh, @RequestParam("keyword") String keyword, Integer trangthai){
        try {
            return ResponseEntity.ok(thongTinHoaDonSevice.TimKiemHoaDonKhachHang(idkh, keyword, trangthai));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi tìm thông tin hóa đơn của khách hàng !");
        }
    }

    // ToDo Find thông tin hóa đơn chi tiết
    @GetMapping("/find-hoa-don/chi-tiet")
    public ResponseEntity<?> FinTTHoaDonCTKhachHang(@RequestParam("idhoadon") UUID idhoadon){
        try {
            return ResponseEntity.ok(thongTinHoaDonSevice.FinTTHoaDonCTKhachHang(idhoadon));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi find thông tin hóa đơn chi tiết của khách hàng !");
        }
    }
}
