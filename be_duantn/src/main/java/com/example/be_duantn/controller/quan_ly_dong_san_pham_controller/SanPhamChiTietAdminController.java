package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamChiTietAdminRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.SanPhamChiTietAdminServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin-sanphamchitiet")
public class SanPhamChiTietAdminController {
    @Autowired
    SanPhamChiTietAdminServiceImpl sanPhamChiTietService;

    //api Load Table
    @GetMapping("/hienthitatcasanphamchitiet")
    public ResponseEntity<?> sanphamchitiet(@RequestParam("IdSP") UUID IdSP) {
        return ResponseEntity.ok(sanPhamChiTietService.getSanPhamCT(IdSP));
    }

    //add
    @PostMapping("/add-sanphamct")
    public ResponseEntity<?> addSanPhamct(@Valid @RequestBody SanPhamChiTietAdminRequest sanphamct) {
        return ResponseEntity.ok(sanPhamChiTietService.addSanPhamCT(sanphamct));
    }

    @PutMapping("/update-ctsp-trangthai")
    public ResponseEntity<String> updateTrangThai(
            @RequestParam("idsp") UUID idsp,
            @RequestParam("trangthai") Integer trangthai) {
        try {
            sanPhamChiTietService.updateCTSP(idsp, trangthai);
            return ResponseEntity.ok("Cập nhật trạng thái thành công!");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm chi tiết!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi cập nhật trạng thái sản phẩm chi tiết!");
        }
    }

    @PutMapping("/update-ctsp-soluongton")
    public ResponseEntity<String> updateSoLuong(
            @RequestParam("idsp") UUID idsp,
            @RequestParam("soluongton") Integer soluong) {
        try {
            sanPhamChiTietService.updateCTSPsl(idsp, soluong);
            return ResponseEntity.ok("Cập số lượng thái thành công!");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm chi tiết!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi cập nhật số lượng sản phẩm chi tiết!");
        }
    }

    // ToDo finBySP theo idsp
    @GetMapping("/findbyid/san-pham")
    public ResponseEntity<?> finByIdSP(@RequestParam("IdSP") UUID IdSP){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.finByIdSP(IdSP));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi finById SP !"));
        }
    }





}
