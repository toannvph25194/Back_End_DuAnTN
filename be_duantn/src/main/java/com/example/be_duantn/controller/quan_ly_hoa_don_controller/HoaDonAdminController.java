package com.example.be_duantn.controller.quan_ly_hoa_don_controller;


import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MessageRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HoaDonTrangThaiAdminRequest;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.service.quan_ly_hoa_don_service.Impl.HoaDonAdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/hoadon")
public class HoaDonAdminController {
    @Autowired
    HoaDonAdminServiceImpl hoaDonService;

    @GetMapping("/tongSoHoaDonChoXacNhan")
    public ResponseEntity<Integer> TongSoHoaDonChoXacNhan() {
        return new ResponseEntity<>(hoaDonService.TongSoHoaDonChoXacNhan(), HttpStatus.OK);
    }

    @GetMapping("/tongSoHoaDonXacNhan")
    public ResponseEntity<Integer> TongSoHoaDonXacNhan() {
        return new ResponseEntity<>(hoaDonService.TongSoHoaDonXacNhan(), HttpStatus.OK);
    }

    @GetMapping("/tongSoHoaDonChoGiao")
    public ResponseEntity<Integer> TongSoHoaDonChoChoGiao() {
        return new ResponseEntity<>(hoaDonService.TongSoHoaDonChoGiao(), HttpStatus.OK);
    }

    @GetMapping("/tongSoHoaDonDangGiao")
    public ResponseEntity<Integer> TongSoHoaDonDangGiao() {
        return new ResponseEntity<>(hoaDonService.TongSoHoaDonDangGiao(), HttpStatus.OK);
    }

    @GetMapping("/tongSoHoaDonHoanThanh")
    public ResponseEntity<Integer> TongSoHoaDonHoanThanh() {
        return new ResponseEntity<>(hoaDonService.TongSoHoaDonHoanThanh(), HttpStatus.OK);
    }

    @GetMapping("/tongSoHoaDonHuy")
    public ResponseEntity<Integer> TongSoHoaDonHuy() {
        return new ResponseEntity<>(hoaDonService.TongSoHoaDonHuy(), HttpStatus.OK);
    }

    // hiển thị hoadon
    @GetMapping("/hienthihoadon")
    public ResponseEntity<?> ShowHoaDonPhanTrang(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(hoaDonService.HienThiHoaDonPhanTrang(page));
    }

    // hiển thị hoadon theo trạng thái
    @GetMapping("/loc/trangthaihoadon")
    public ResponseEntity<?> locTrangthaiHD(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                            @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize,
                                            @RequestParam("trangthai") Integer trangthai) {
        return ResponseEntity.ok(hoaDonService.LocHoaDonTheoTrangThai(pageNumber, pageSize, trangthai));
    }

    // hiển thị hoadon theo ma
    @GetMapping("/loc/mahoadon")
    public ResponseEntity<?> locMaHD(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                     @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize,
                                     @RequestParam("mahoadon") String mahoadon) {
        return ResponseEntity.ok(hoaDonService.TimKiemTheoMa(pageNumber, pageSize, mahoadon));
    }

    // hiển thị hoadon theo trạng thái
    @GetMapping("/loc/loaihoadon")
    public ResponseEntity<?> locTrangthaiLoaiHD(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize,
                                                @RequestParam("loaihoadon") Integer loaihoadon) {
        return ResponseEntity.ok(hoaDonService.LocHoaDonTheoLoaiHoaDon(pageNumber, pageSize, loaihoadon));
    }

    //api Load Table
    @GetMapping("/hienthihoadontheoid")
    public ResponseEntity<?> sanphamchitiet(@RequestParam("IdHD") UUID IdHD) {
        return ResponseEntity.ok(hoaDonService.finByIdHD(IdHD));
    }

    @PutMapping("/updatethongtinhoadon/{idhoadon}")
    public ResponseEntity<HoaDon> updateThongTinHoaDon(
            @PathVariable("idhoadon") UUID IDHD,
            @Valid @RequestBody HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest
            ,
            Principal principal,
            Principal principal01
    ) {
        String username = principal.getName();
        return new ResponseEntity<>(hoaDonService.updateThongTinHoaDon(IDHD, hoaDonTrangThaiAdminRequest, username,principal01), HttpStatus.OK);
    }

    @PutMapping("/updatetrangthaihoadon/{idhoadon}")
    public ResponseEntity<HoaDon> updateTrangThaiHoaDon(
            @PathVariable("idhoadon") UUID IDHD,
            @Valid @RequestBody HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest
            ,
            Principal principal   ,
            Principal principal01
    ) {
        String username = principal.getName();
        return new ResponseEntity<>(hoaDonService.updateTrangThaiHoaDon(IDHD, hoaDonTrangThaiAdminRequest, username,principal01), HttpStatus.OK);
    }

    @PutMapping("/updatethongtinnguoigiao/{idhoadon}")
    public ResponseEntity<HoaDon> updatethongtinnguoigiao(
            @PathVariable("idhoadon") UUID IDHD,
            @Valid @RequestBody HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest
            ,
            Principal principal,
            Principal principal01
    ) {
        String username = principal.getName();
        return new ResponseEntity<>(hoaDonService.updateThongTinNguoiGiao(IDHD, hoaDonTrangThaiAdminRequest, username,principal01), HttpStatus.OK);
    }

    @PutMapping("/updatehoadonhoanthanh/{idhoadon}")
    public ResponseEntity<HoaDon> updatehoadonhoanthanh(
            @PathVariable("idhoadon") UUID IDHD,
            @Valid @RequestBody HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest
            ,
            Principal principal,
            Principal principal01
    ) {
        String username = principal.getName();
        return new ResponseEntity<>(hoaDonService.updatehoanthanh(IDHD, hoaDonTrangThaiAdminRequest, username,principal01), HttpStatus.OK);
    }


    @PutMapping("/updatehuyhoadon/{idhoadon}")
    public ResponseEntity<HoaDon> updatehuyhoadon(
            @PathVariable("idhoadon") UUID IDHD,
            @Valid @RequestBody HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest
            ,
            Principal principal,Principal principal01
    ) {
        String username = principal.getName();
        return new ResponseEntity<>(hoaDonService.updatehuyhoadon(IDHD, hoaDonTrangThaiAdminRequest, username,principal01), HttpStatus.OK);
    }

    @PutMapping("/updatehanhtien")
    public ResponseEntity<HoaDon> updatethanhtien(
            @RequestParam("idhoadon") UUID IDHD

    ) {
        return new ResponseEntity<>(hoaDonService.updateThanhTien(IDHD), HttpStatus.OK);
    }

}
