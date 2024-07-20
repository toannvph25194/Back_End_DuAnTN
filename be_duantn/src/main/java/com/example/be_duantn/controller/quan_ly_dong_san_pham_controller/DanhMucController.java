package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.DanhMucServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/danhmuc")
public class DanhMucController {
    @Autowired
    DanhMucServiceImpl danhMucService;

    // ToDo hiển thị danh sách danh mục
    @GetMapping("/hien-thi")
    public ResponseEntity<?> GetAllDanhMuc(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(danhMucService.GetAllDanhMuc(page));
    }

    // ToDo findby danh mục theo id
    @GetMapping("/find-by")
    public ResponseEntity<?> FindByIdDanhMuc(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(danhMucService.FindByDanhMucID(id));
    }

    // ToDo thêm mới danh mục
    @PostMapping("/add-danhmuc")
    public ResponseEntity<?> AddDanhMuc(@Valid @RequestBody DanhMucRequest danhMucRequest) {
        return ResponseEntity.ok(danhMucService.AddDanhMuc(danhMucRequest));
    }

    // ToDo update danh mục theo id
    @PutMapping("/update-danhmuc")
    public ResponseEntity<DanhMuc> CapNhatDanhMuc(@RequestBody DanhMucRequest danhMucRequest) {
        return ResponseEntity.ok(danhMucService.UpdateDanhMuc(danhMucRequest));
    }

    // ToDo chuyển trạng thái danh mục theo id
    @PutMapping("/update-trang-thai")
    public ResponseEntity<DanhMuc> ChuyenTrangThai(@RequestParam("id") UUID id, @RequestParam Integer trangthai) {
        try {
            DanhMuc danhMuc = danhMucService.ChuyenTrangThai(id, trangthai);
            if (danhMuc != null) {
                return ResponseEntity.ok(danhMuc);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ToDo hiển thị danh sách danh mục load combobox
    @GetMapping("/hien-thi-combobox")
    public ResponseEntity<?> getAllThuongHieuLoadCOmbobox() {
        return ResponseEntity.ok(danhMucService.GetAllDanhMucLoadComboBox());
    }
}
