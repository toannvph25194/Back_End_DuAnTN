package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SizeRequest;
import com.example.be_duantn.entity.Size;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.SizeServiceImpl;
import jakarta.validation.Valid;
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

    // ToDo hiển thị danh sách size
    @GetMapping("/hien-thi")
    public ResponseEntity<?> GetAllSize(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(sizeService.GetAllSize(page));
    }

    // ToDo findby size theo id
    @GetMapping("/find-by")
    public ResponseEntity<?> FindByIdSize(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(sizeService.FindBySizeID(id));
    }

    // ToDo thêm mới size
    @PostMapping("/add-size")
    public ResponseEntity<?> AddSize(@Valid @RequestBody SizeRequest sizeRequest) {
        return ResponseEntity.ok(sizeService.AddSize(sizeRequest));
    }

    // ToDo update size theo id
    @PutMapping("/update-size")
    public ResponseEntity<Size> CapNhatSize(@RequestBody SizeRequest sizeRequest) {
        return ResponseEntity.ok(sizeService.UpdateSize(sizeRequest));
    }

    // ToDo chuyển trạng thái size theo id
    @PutMapping("/update-trang-thai")
    public ResponseEntity<Size> ChuyenTrangThai(@RequestParam("id") UUID id, @RequestParam Integer trangthai) {
        try {
            Size size = sizeService.ChuyenTrangThai(id, trangthai);
            if (size != null) {
                return ResponseEntity.ok(size);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ToDo hiển thị danh sách size load combobox
    @GetMapping("/hien-thi-combobox")
    public ResponseEntity<?> GetAllSizeLoadComboBox() {
        return ResponseEntity.ok(sizeService.GetAllSizeLoadComboBox());
    }
}
