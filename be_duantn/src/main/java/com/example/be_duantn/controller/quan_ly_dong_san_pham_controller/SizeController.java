package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SizeRequest;
import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.entity.Size;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.SizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/size")
public class SizeController {
    @Autowired
    SizeServiceImpl sizeService;

    //api Load Table
    @GetMapping("/hienthitatcasize")
    public ResponseEntity<?> getAllSize(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(sizeService.getSize(page));
    }


    @PostMapping("/create-size")
    public ResponseEntity<Size> taoSize(@RequestBody SizeRequest sizeRequest) {
        return ResponseEntity.ok(sizeService.createSize(sizeRequest));
    }
    @GetMapping("/hienthitatcasizetheid")
    public ResponseEntity<?> getAllsize(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(sizeService.getSizeById(id));
    }


    @PutMapping("/update-size/{id}")
    public ResponseEntity<Size> capNhatSize(@PathVariable UUID id, @RequestBody SizeRequest sizeRequest) {
        return ResponseEntity.ok(sizeService.updateSize(id, sizeRequest));
    }
    @PutMapping("/ctt-size/{id}")
    public ResponseEntity<Size> changeStatus(@PathVariable(value = "id") UUID id, @RequestParam int trangthai) {
        try {
            Size updatedsize = sizeService.chuyenTrangThai(id, trangthai);
            // return ResponseEntity.ok(updatedMauSac);
            if (updatedsize != null) {
                return ResponseEntity.ok(updatedsize);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Invalid UUID format
        }
    }
}
