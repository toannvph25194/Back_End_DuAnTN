package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SizeRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.entity.Size;
import com.example.be_duantn.entity.XuatXu;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.DanhMucServiceImpl;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.XuatXuServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/xuatxu")
public class XuatXuController {
    @Autowired
    XuatXuServiceImpl xuatXuService;

    //api Load Table
    @GetMapping("/hienthitatcaxuatxu")
    public ResponseEntity<?> getAllThuongHieu(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(xuatXuService.getXuatXu(page));
    }
    //add
    @PostMapping("/add-xuatxu")
    public ResponseEntity<?> addChatLieu(@Valid @RequestBody XuatXuRequest xuatXuRequest) {
        return ResponseEntity.ok(xuatXuService.addXuatXu(xuatXuRequest));
    }
    @GetMapping("/hienthitatcaxuatxutheid")
    public ResponseEntity<?> getAllsize(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(xuatXuService.getSizeById(id));
    }

    @PutMapping("/update-xuatxu/{id}")
    public ResponseEntity<XuatXu> capNhatxuatxu(@PathVariable UUID id, @RequestBody XuatXuRequest xuatXuRequest) {
        return ResponseEntity.ok(xuatXuService.updateXuatxu(id, xuatXuRequest));
    }
    @PutMapping("/ctt-xuatxu/{id}")
    public ResponseEntity<XuatXu> changeStatus(@PathVariable(value = "id") UUID id, @RequestParam int trangthai) {
        try {
            XuatXu updatedxuatxu = xuatXuService.chuyenTrangThai(id, trangthai);
            // return ResponseEntity.ok(updatedMauSac);
            if (updatedxuatxu != null) {
                return ResponseEntity.ok(updatedxuatxu);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Invalid UUID format
        }
    }
}
