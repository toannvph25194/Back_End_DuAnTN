package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MauSacRequest;
import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.MauSacServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/mausac")
public class MauSacController {
    @Autowired
    MauSacServiceImpl mauSacService;

    // ToDo hiển thị danh sách màu sắc
    @GetMapping("/hien-thi")
    public ResponseEntity<?> GetAllMauSac(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(mauSacService.GetAllMauSac(page));
    }

    // ToDo findby màu sắc theo id
    @GetMapping("/find-by")
    public ResponseEntity<?> FindByIdMauSac(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(mauSacService.FindByMauSacID(id));
    }

    // ToDo thêm mới màu sắc
    @PostMapping("/add-mausac")
    public ResponseEntity<?> AddMauSac(@Valid @RequestBody MauSacRequest mauSacRequest) {
        return ResponseEntity.ok(mauSacService.AddMauSac(mauSacRequest));
    }

    // ToDo update mausac theo id
    @PutMapping("/update-mausac")
    public ResponseEntity<MauSac> CapNhatMauSac(@RequestBody MauSacRequest mauSacRequest) {
        return ResponseEntity.ok(mauSacService.UpdateMauSac(mauSacRequest));
    }

    // ToDo chuyển trạng thái màu sắc theo id
    @PutMapping("/update-trang-thai")
    public ResponseEntity<MauSac> ChuyenTrangThai(@RequestParam("id") UUID id, @RequestParam Integer trangthai) {
        try {
            MauSac mauSac = mauSacService.ChuyenTrangThai(id, trangthai);
            if (mauSac != null) {
                return ResponseEntity.ok(mauSac);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ToDo hiển thị danh sách màu sắc load combobox
    @GetMapping("/hien-thi-combobox")
    public ResponseEntity<?> getAllmausacLoadComboBox() {
        return ResponseEntity.ok(mauSacService.getMauSacLoadComboBox());
    }
}
