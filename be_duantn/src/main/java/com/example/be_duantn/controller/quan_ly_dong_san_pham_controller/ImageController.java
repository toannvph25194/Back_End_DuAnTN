package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ImageRequest;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin-image")
public class ImageController {
    @Autowired
    ImageServiceImpl imageService;

    //api Load Table
    @GetMapping("/hienthitatcaimage")
    public ResponseEntity<?> sanphamchitiet(@RequestParam("IdSP") UUID IdSP) {
        return ResponseEntity.ok(imageService.getImage(IdSP));
    }
    // them moi 1 san pham
    @PostMapping("/add-imge")
    public ResponseEntity<?> addSanPham(@RequestBody List<ImageRequest> imageRequest) {
        return ResponseEntity.ok(imageService.addImage(imageRequest));
    }
    // API Xóa Image theo ID
    @DeleteMapping("/delete-image/{imageId}")
    public ResponseEntity<String> deleteImageById(@PathVariable UUID imageId) {
        try {
            imageService.deleteImageById(imageId);
            return ResponseEntity.ok("Id Image " + imageId + " delete thành công.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
