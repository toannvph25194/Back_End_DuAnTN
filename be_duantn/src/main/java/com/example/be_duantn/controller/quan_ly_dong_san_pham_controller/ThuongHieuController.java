package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ThuongHieuRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.entity.ThuongHieu;
import com.example.be_duantn.entity.XuatXu;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.DanhMucServiceImpl;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.ThuongHieuServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/thuonghieu")
public class ThuongHieuController {
    @Autowired
    ThuongHieuServiceImpl thuongHieuService;

    //api Load Table
    @GetMapping("/hienthitatcathuonghieu")
    public ResponseEntity<?> getAllThuongHieu(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(thuongHieuService.getThuongHIeu(page));
    }

    //add
    @PostMapping("/add-thuonghieu")
    public ResponseEntity<?> addChatLieu(@Valid @RequestBody ThuongHieuRequest thuongHieuRequest) {
        return ResponseEntity.ok(thuongHieuService.addThuongHieu(thuongHieuRequest));
    }

    @GetMapping("/hienthitatcathuonghieutheid")
    public ResponseEntity<?> getAllsize(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(thuongHieuService.getthuonghieuById(id));
    }

    @PutMapping("/update-thuonghieu/{id}")
    public ResponseEntity<ThuongHieu> capNhatthuonghieu(@PathVariable UUID id, @RequestBody ThuongHieuRequest thuongHieuRequest) {
        return ResponseEntity.ok(thuongHieuService.updateThuonghieu(id, thuongHieuRequest));
    }

    @PutMapping("/ctt-thuonghieu/{id}")
    public ResponseEntity<ThuongHieu> changeStatus(@PathVariable(value = "id") UUID id, @RequestParam int trangthai) {
        try {
            ThuongHieu thuongHieu = thuongHieuService.chuyenTrangThai(id, trangthai);
            // return ResponseEntity.ok(updatedMauSac);
            if (thuongHieu != null) {
                return ResponseEntity.ok(thuongHieu);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Invalid UUID format
        }
    }
}
