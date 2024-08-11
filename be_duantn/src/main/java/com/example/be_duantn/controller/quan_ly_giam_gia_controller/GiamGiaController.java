package com.example.be_duantn.controller.quan_ly_giam_gia_controller;

import com.example.be_duantn.dto.request.quan_ly_giam_gia_request.MaGiamGiaRequest;
import com.example.be_duantn.entity.GiamGia;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.service.quan_ly_giam_gia_service.Impl.GiamGiaServiceImpl;
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
@RequestMapping("/api/admin/giamgia")
public class GiamGiaController {
    @Autowired
    GiamGiaServiceImpl giamGiaService;

    // ToDo hiển thị danh sách giảm giá
    @GetMapping("/hien-thi")
    public ResponseEntity<?> getAllGiamGia(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(giamGiaService.GetAllGiamGia(page));
    }

    // ToDo hiển thị danh sách giảm giá theo ma
    @GetMapping("/loc/magiamgia")
    public ResponseEntity<?> locMaGG(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                     @RequestParam("magiamgia") String magiamgia) {
        return ResponseEntity.ok(giamGiaService.TimKiemTheoMa(pageNumber, pageSize, magiamgia));
    }

    // ToDo: Hiển thị danh sách giảm giá theo khoảng ngày tạo
    @GetMapping("/loc/khoangngay")
    public ResponseEntity<?> locKhoangNgay(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(giamGiaService.TimKiemTheoKhoangNgay(pageNumber, pageSize, startDate, endDate));
    }

    // ToDo thêm mới ma giảm giá
    @PostMapping("/add-giamgia")
    public ResponseEntity<?> AddGiamGia(@Valid @RequestBody MaGiamGiaRequest giamGiaRequest) {
        return ResponseEntity.ok(giamGiaService.AddGiamGia(giamGiaRequest));
    }

    // ToDo findby chất liệu theo id
    @GetMapping("/find-by")
    public ResponseEntity<?> FindByIdChatLieu(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(giamGiaService.FindByGiamGiaID(id));
    }

    // ToDo hiển thị danh sách giảm giá sản phẩm
    @GetMapping("/hien-thi-sanpham-giamgia")
    public ResponseEntity<?> getAllGiamGiaSanPhamTheoId(@RequestParam(defaultValue = "0", value = "page") Integer page, @RequestParam("id") UUID id) {
        return ResponseEntity.ok(giamGiaService.GetAllSanPhmaTheoId(page, id));
    }

    // ToDo hiển thị danh sách giảm giá sản phẩm thêm
    @GetMapping("/hien-thi-them")
    public ResponseEntity<?> getAllSanPhamGiamGia(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(giamGiaService.GetAllSanPhamGiamGiaThem(page));
    }

    // ToDo thêm sản phẩm
    @PutMapping("/add-sanpham-giamgia")
    public ResponseEntity<SanPham> AddSanPhamGiamGia(@RequestParam("idsp") UUID idsp, @RequestParam("idgg") UUID idgg) {
        return ResponseEntity.ok(giamGiaService.ThemSanPhamGiamGia(idsp, idgg));
    }

    // ToDo thêm sản phẩm
    @PutMapping("/delet-sanpham-giamgia")
    public ResponseEntity<SanPham> deletSanPhamGiamGia(@RequestParam("idsp") UUID idsp) {
        return ResponseEntity.ok(giamGiaService.XoaSanPhamGiamGia(idsp));
    }

    // ToDo update trạng thái
    @PutMapping("/trangthai-giamgia")
    public ResponseEntity<GiamGia> UpdatedeTrangThaiGiamGia(@RequestParam("idgg") UUID idgg) {
        return ResponseEntity.ok(giamGiaService.UpdateTrangThaiGiamGia(idgg));
    }

    // ToDo update giảm giá
    @PutMapping("/updtae-giamgia")
    public ResponseEntity<GiamGia> UpdatedeGiamGia(@RequestBody MaGiamGiaRequest giamGiaRequest, @RequestParam("idgg") UUID idgg) {
        return ResponseEntity.ok(giamGiaService.UpdateGiamGia(giamGiaRequest, idgg));
    }

    // ToDo hiển thị danh sách sản phẩm áp dụng giảm giá
    @GetMapping("/hien-thi-sanpham-daapdung-giamgia")
    public ResponseEntity<?> getAllSanPhamDaApDungGiamGia(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(giamGiaService.GetAllSanPhamGiamGia(page));
    }
}
