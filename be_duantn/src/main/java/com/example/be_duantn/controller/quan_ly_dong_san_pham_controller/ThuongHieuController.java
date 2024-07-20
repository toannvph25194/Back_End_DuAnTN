package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ThuongHieuRequest;
import com.example.be_duantn.entity.ThuongHieu;
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

    // ToDo hiển thị danh sách thương hiệu
    @GetMapping("/hien-thi")
    public ResponseEntity<?> GetAllThuongHieu(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(thuongHieuService.GetAllThuongHieu(page));
    }

    // ToDo findby thương hiệu theo id
    @GetMapping("/find-by")
    public ResponseEntity<?> FindByIdThuongHieu(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(thuongHieuService.FindByThuongHieuID(id));
    }

    // ToDo thêm mới thương hiệu
    @PostMapping("/add-thuonghieu")
    public ResponseEntity<?> AddThuongHieu(@Valid @RequestBody ThuongHieuRequest thuongHieuRequest) {
        return ResponseEntity.ok(thuongHieuService.AddThuongHieu(thuongHieuRequest));
    }

    // ToDo update thương hiệu theo id
    @PutMapping("/update-thuonghieu")
    public ResponseEntity<ThuongHieu> CapNhatThuongHieu(@RequestBody ThuongHieuRequest thuongHieuRequest) {
        return ResponseEntity.ok(thuongHieuService.UpdateThuongHieu(thuongHieuRequest));
    }

    // ToDo chuyển trạng thái thương hiệu theo id
    @PutMapping("/update-trang-thai")
    public ResponseEntity<ThuongHieu> ChuyenTrangThai(@RequestParam("id") UUID id, @RequestParam Integer trangthai) {
        try {
            ThuongHieu thuongHieu = thuongHieuService.ChuyenTrangThai(id, trangthai);
            if (thuongHieu != null) {
                return ResponseEntity.ok(thuongHieu);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ToDo hiển thị danh sách thương hiệu load combobox
    @GetMapping("/hien-thi-combobox")
    public ResponseEntity<?> GetAllThuongHieuLoadComboBox() {
        return ResponseEntity.ok(thuongHieuService.GetAllThuongHieuLoadComboBox());
    }
}
