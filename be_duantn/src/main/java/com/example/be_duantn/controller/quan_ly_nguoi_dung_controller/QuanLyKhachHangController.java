package com.example.be_duantn.controller.quan_ly_nguoi_dung_controller;

import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.KhachHangRequest;
import com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl.QuanLyQuanLyKhachHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/quan-ly-khach-hang")
public class QuanLyKhachHangController {
    @Autowired
    QuanLyQuanLyKhachHangServiceImpl quanLyQuanLyKhachHangService;

    // ToDo Load khách hàng phân trang
    @GetMapping("/hien-thi")
    public ResponseEntity<?> LoadSPBanTaiQuay(@RequestParam(defaultValue = "0", value = "page") Integer page){
        try {
            return ResponseEntity.ok(quanLyQuanLyKhachHangService.LoadAllKhachHang(page));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load quản lý khách hàng !");
        }
    }

    // ToDo Thêm mới khách hàng
    @PostMapping("/them-khach-hang")
    public ResponseEntity<?> ThemKhachHang(@RequestBody KhachHangRequest khachHangRequest) {
        try {
            return ResponseEntity.ok( quanLyQuanLyKhachHangService.ThemMoiKhachHang(khachHangRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi thêm mới quản lý khách hàng !");
        }
    }

    @PutMapping("/update-khach-hang")
    public ResponseEntity<?> UpdateKhachHang(@RequestBody KhachHangRequest khachHangRequest) {
        try {
            return ResponseEntity.ok(quanLyQuanLyKhachHangService.UpdateKhachHang(khachHangRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi cập nhật quản lý khách hàng !");
        }
    }

    @PutMapping("/update-trang-thai")
    public ResponseEntity<?>UupdateTrangThaiKhachHang(@RequestParam("id") UUID id, @RequestParam Integer trangthai) {
        try {
            return ResponseEntity.ok(quanLyQuanLyKhachHangService.UpdateTrangThaiKhachHang(id, trangthai));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi cập nhật trạng thái quản lý khách hàng !");
        }
    }

    // ToDo Lọc khách hàng theo nhiều tiêu chí hovatenkh, sdt, email
    @GetMapping("/loc-tieu-chi")
    public ResponseEntity<?> LocKhachHangTheoNhieuTieuChi(@RequestParam(defaultValue = "0", value = "page") Integer page, @RequestParam("hovatenkh") String hovatenkh,
                                                          @RequestParam("sodienthoai") String sodienthoai,  @RequestParam("email") String email){
        try {
            return ResponseEntity.ok(quanLyQuanLyKhachHangService.LocKhachHangTheoNhieuTieuChi(page, hovatenkh, sodienthoai, email));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi lọc nhiều tiêu chí quản lý khách hàng !");
        }
    }

    // ToDo Lọc khách hàng theo trạng thái
    @GetMapping("/loc-trang-thai")
    public ResponseEntity<?> LocKhachHangTheoNhieuTieuChi(@RequestParam(defaultValue = "0", value = "page") Integer page, Integer trangthai){
        try {
            return ResponseEntity.ok(quanLyQuanLyKhachHangService.LocKhachHangTheoTrangThai(page, trangthai));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi lọc trạng thái quản lý khách hàng !");
        }
    }



}
