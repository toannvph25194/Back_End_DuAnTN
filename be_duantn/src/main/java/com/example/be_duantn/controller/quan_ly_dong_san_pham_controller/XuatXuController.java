package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.entity.XuatXu;
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

    // ToDo hiển thị danh sách xuất xứ
    @GetMapping("/hien-thi")
    public ResponseEntity<?> GetAllXuatXu(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(xuatXuService.GetAllXuatXu(page));
    }

    // ToDo findby xuất xứ theo id
    @GetMapping("/find-by")
    public ResponseEntity<?> FindByIdXuatXu(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(xuatXuService.FindByXuatXuID(id));
    }

    // ToDo thêm mới xuất xứ
    @PostMapping("/add-xuatxu")
    public ResponseEntity<?> AddXuatXu(@Valid @RequestBody XuatXuRequest xuatXuRequest) {
        return ResponseEntity.ok(xuatXuService.AddXuatXu(xuatXuRequest));
    }

    // ToDo update xuất xứ theo id
    @PutMapping("/update-xuatxu")
    public ResponseEntity<XuatXu> CapNhatXuatXu(@RequestBody XuatXuRequest xuatXuRequest) {
        return ResponseEntity.ok(xuatXuService.UpdateXuatXu(xuatXuRequest));
    }

    // ToDo chuyển trạng thái xuất xứ theo id
    @PutMapping("/update-trang-thai")
    public ResponseEntity<XuatXu> ChuyenTrangThai(@RequestParam("id") UUID id, @RequestParam Integer trangthai) {
        try {
            XuatXu xuatXu = xuatXuService.ChuyenTrangThai(id, trangthai);
            if (xuatXu != null) {
                return ResponseEntity.ok(xuatXu);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ToDo hiển thị danh sách xuất xứ load combobox
    @GetMapping("/hien-thi-combobox")
    public ResponseEntity<?> GetallXuatXuLoadCombobox() {
        return ResponseEntity.ok(xuatXuService.GetallXuatXuLoadCombobox());
    }
}
