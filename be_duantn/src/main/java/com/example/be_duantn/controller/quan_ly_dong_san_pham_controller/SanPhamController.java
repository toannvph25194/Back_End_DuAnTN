package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MessageRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.SanPhamServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin-sanpham")
public class SanPhamController {

    @Autowired
    SanPhamServiceImpl sanPhamService;

    // hiển thị sản phẩm
    @GetMapping("/hienthisanpham")
    public ResponseEntity<?> ShowSanPhamPhanTrang(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(sanPhamService.HienThiSanPhamPhanTrang(page));
    }

    //add
    @PostMapping("/add-sanpham")
    public ResponseEntity<?> addSanPham(@Valid @RequestBody SanPhamRequest sanpham) {
        return ResponseEntity.ok(sanPhamService.addSanPham(sanpham));
    }

    //api Load sp theo id
    @GetMapping("/hienthisanphamtheoid")
    public ResponseEntity<?> sanphamchitiet(@RequestParam("IdSP") UUID IdSP) {
        return ResponseEntity.ok(sanPhamService.getSanPhamTheoID(IdSP));
    }

    // TODO Lọc theo tensp
    @GetMapping("/loc/ten-san-pham")
    public ResponseEntity<?> locTenSP(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                      @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize,
                                      @RequestParam("tensp") String tensp) {
        return ResponseEntity.ok(sanPhamService.locTenSPShop(pageNumber, pageSize, tensp));
    }

    // TODO Lọc theo nhiều tiêu chí. tendanhmuc, tenmausac, tensize
    @GetMapping("/loc/san-pham")
    public ResponseEntity<?> locSPShop(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                       @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize,
                                       @RequestParam("tendanhmuc") String tendanhmuc,
                                       @RequestParam("tenmausac") String tenmausac,
                                       @RequestParam("tensize") String tensize,
                                       @RequestParam("tenchatlieu") String tenchatlieu,
                                       @RequestParam("tenxuatxu") String tenxuatxu,
                                       @RequestParam("tenthuonghieu") String tenthuonghieu
    ) {
        return ResponseEntity.ok(sanPhamService.locSPShopNTC(pageNumber, pageSize, tendanhmuc, tenmausac, tensize, tenchatlieu, tenxuatxu, tenthuonghieu));
    }

    @PutMapping("/update-san-pham")
    public ResponseEntity<MessageRequest> updateSanPham(
            @RequestParam(name = "id") UUID id,
            @Valid @RequestBody SanPhamRequest sanPhamRequest
    ) {
        return new ResponseEntity<>(sanPhamService.udatesanpham(sanPhamRequest, id), HttpStatus.CREATED);
    }

    @PutMapping("/update-sp-trangthai")
    public ResponseEntity<String> updateSoLuong(
            @RequestParam("idsp") UUID idsp,
            @RequestParam("trangthai") Integer trangthai) {
        try {
            sanPhamService.updatesp(idsp, trangthai);
            return ResponseEntity.ok("Cập nhập trang thái thành công!");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm chi tiết!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra !");
        }
    }
    // hiển thị sản phẩm thêm hoá đơn
    @GetMapping("/hienthisanphamthemhoadon")
    public ResponseEntity<?> ShowSanPhamPhanTrangThemHD(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(sanPhamService.HienThiSanPhamPhanTrangThemHD(page));
    }

    // TODO Lọc theo tenspThemHD
    @GetMapping("/loc/ten-san-pham-them-hd")
    public ResponseEntity<?> locTenSPThemHD(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                      @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
                                      @RequestParam("tensp") String tensp) {
        return ResponseEntity.ok(sanPhamService.locTenSPShopThemHD(pageNumber, pageSize, tensp));
    }
    // TODO Lọc theo nhiều tiêu chí. tendanhmuc, tenmausac, tensize thêm hoá đơn
    @GetMapping("/loc/san-pham-themhd")
    public ResponseEntity<?> locSPShopThemHD(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                       @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
                                       @RequestParam("tendanhmuc") String tendanhmuc,
                                       @RequestParam("tenmausac") String tenmausac,
                                       @RequestParam("tensize") String tensize,
                                       @RequestParam("tenchatlieu") String tenchatlieu,
                                       @RequestParam("tenxuatxu") String tenxuatxu,
                                       @RequestParam("tenthuonghieu") String tenthuonghieu
    ) {
        return ResponseEntity.ok(sanPhamService.locSPShopNTCThemHD(pageNumber, pageSize, tendanhmuc, tenmausac, tensize, tenchatlieu, tenxuatxu, tenthuonghieu));
    }

}
