package com.example.be_duantn.controller.quan_ly_giam_gia_controller;

import com.example.be_duantn.dto.request.quan_ly_giam_gia_request.VouCherAdminRequest;
import com.example.be_duantn.entity.GiamGia;
import com.example.be_duantn.entity.VouCher;
import com.example.be_duantn.service.quan_ly_giam_gia_service.Impl.VouCherServiceAdminImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/quan-ly-voucher")
public class VouCherAdminController {
    @Autowired
    VouCherServiceAdminImpl vouCherService;

    // ToDo hiển thị danh sách voucher
    @GetMapping("/hien-thi")
    public ResponseEntity<?> GetAllVouCher(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(vouCherService.GetAllVoucher(page));
    }

    // ToDo hiển thị danh sách voucher theo ma
    @GetMapping("/loc/ma")
    public ResponseEntity<?> LocMaVC(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                     @RequestParam("mavoucher") String ma) {
        return ResponseEntity.ok(vouCherService.TimKiemTheoMa(pageNumber, pageSize, ma));
    }

    // ToDo: Hiển thị danh sách voucher theo khoảng ngày tạo
    @GetMapping("/loc/khoangngay")
    public ResponseEntity<?> LocKhoangNgay(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(vouCherService.TimKiemTheoKhoangNgay(pageNumber, pageSize, startDate, endDate));
    }

    // ToDo findby theo id
    @GetMapping("/find-by")
    public ResponseEntity<?> FindByI(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(vouCherService.FindByVoucherID(id));
    }

    // ToDo thêm mới voucher
    @PostMapping("/add-voucher")
    public ResponseEntity<?> AddVouCher(@Valid @RequestBody VouCherAdminRequest vouCherAdminRequest) {
        return ResponseEntity.ok(vouCherService.AddVoucher(vouCherAdminRequest));
    }
    // ToDo update trạng thái
    @PutMapping("/trangthai-voucher")
    public ResponseEntity<VouCher> UpdatedeTrangThaiVouCher(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(vouCherService.UpdateTrangThai(id));
    }
    // ToDo update voucher
    @PutMapping("/updtae-voucher")
    public ResponseEntity<VouCher> UpdatedeVouCher(@RequestBody VouCherAdminRequest vouCherAdminRequest, @RequestParam("id") UUID id) {
        return ResponseEntity.ok(vouCherService.UpdateVoucher(vouCherAdminRequest, id));
    }
}
