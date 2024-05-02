package com.example.be_duantn.controller.quan_ly_thong_ke_controller;

import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.dto.respon.thong_ke_respon.DoanhThuResponse;

import com.example.be_duantn.service.quan_ly_thong_ke_service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/thong-ke/")
public class ThongKeController {

    @Autowired
    private ThongKeService thongKeService;

    @GetMapping("doanhthu")
    public ResponseEntity<List<DoanhThuResponse>> doanhThu(
            @RequestParam(name = "ngayBd") Date ngayBd,
            @RequestParam(name = "ngayKt") Date ngayKt) {
        return new ResponseEntity<>(thongKeService.doanhThu(ngayBd, ngayKt), HttpStatus.OK);
    }

//    @GetMapping("topsanpham")
//    public ResponseEntity<List<TopSanPham>> sanPhamBanChay() {
//        return new ResponseEntity<>(thongKeService.listSanPhamBanChay(), HttpStatus.OK);
//    }

    @GetMapping("banchay")
    public List<Object[]> getSanPhamBanChay() {
        return thongKeService.SanPhamBanChay();
    }

    @GetMapping("tongtienhomnay")
    public ResponseEntity<BigDecimal> tongTienDonHomNay() {
        return new ResponseEntity<>(thongKeService.tongTienDonHomNay(), HttpStatus.OK);
    }

    @GetMapping("tongtientuannay")
    public ResponseEntity<BigDecimal> tongTienDonTuanNay() {
        return new ResponseEntity<>(thongKeService.tongTienDonTuanNay(), HttpStatus.OK);
    }

    @GetMapping("tongtiendonthangnay")
    public ResponseEntity<BigDecimal> tongTienDonThangNay() {
        return new ResponseEntity<>(thongKeService.tongTienDonThangNay(), HttpStatus.OK);
    }

    @GetMapping("tongtiennamnay")
    public ResponseEntity<BigDecimal> tongTienDonNamNay() {
        return new ResponseEntity<>(thongKeService.tongTienDonNamNay(), HttpStatus.OK);
    }
    @GetMapping("all")
    public List<HoaDon> getAllHoaDon() {
        return thongKeService.getAllHoaDon();
    }

    @GetMapping("sodonhomnay")
    public ResponseEntity<Integer> soDonHomNay() {
        return new ResponseEntity<>(thongKeService.soDonHomNay(), HttpStatus.OK);
    }

    @GetMapping("sodontuannay")
    public ResponseEntity<Integer> soDonTuanNay() {
        return new ResponseEntity<>(thongKeService.soDonTuanNay(), HttpStatus.OK);
    }

    @GetMapping("sodonthangnay")
    public ResponseEntity<Integer> soDonThangNay() {
        return new ResponseEntity<>(thongKeService.soDonThangNay(), HttpStatus.OK);
    }

    @GetMapping("sodonnamnay")
    public ResponseEntity<Integer> soDonNamNay() {
        return new ResponseEntity<>(thongKeService.soDonNamNay(), HttpStatus.OK);
    }


    @GetMapping("sodonhuyhomnay")
    public ResponseEntity<Integer> soDonHuyHomNay() {
        return new ResponseEntity<>(thongKeService.soDonHuyHomNay(), HttpStatus.OK);
    }

    @GetMapping("sodonhuytuannay")
    public ResponseEntity<Integer> soDonHuyTuanNay() {
        return new ResponseEntity<>(thongKeService.soDonHuyTuanNay(), HttpStatus.OK);
    }

    @GetMapping("sodonhuythangnay")
    public ResponseEntity<Integer> soDonHuyThangNay() {
        return new ResponseEntity<>(thongKeService.soDonHuyThangNay(), HttpStatus.OK);
    }

    @GetMapping("sodonhuynamnay")
    public ResponseEntity<Integer> soDonHuyNamNay() {
        return new ResponseEntity<>(thongKeService.soDonHuyNamNay(), HttpStatus.OK);
    }

    @GetMapping("sanphambanhomnay")
    public ResponseEntity<Integer> soSanPhamBanRaHomNay() {
        return new ResponseEntity<>(thongKeService.soSanPhamBanRaHomNay(), HttpStatus.OK);
    }

    @GetMapping("sanphambantuannay")
    public ResponseEntity<Integer> soSanPhamBanRaTuanNay() {
        return new ResponseEntity<>(thongKeService.soSanPhamBanRaTuanNay(), HttpStatus.OK);
    }

    @GetMapping("sanphambanthangnay")
    public ResponseEntity<Integer> soSanPhamBanRaThangNay() {
        return new ResponseEntity<>(thongKeService.soSanPhamBanRaThangNay(), HttpStatus.OK);
    }

    @GetMapping("sanphambannamnay")
    public ResponseEntity<Integer> soSanPhamBanNamNay() {
        return new ResponseEntity<>(thongKeService.soSanPhamBanNamNay(), HttpStatus.OK);
    }

    @GetMapping("choxacnhan")
    public ResponseEntity<Integer> demSoHoaDonChoXacNhan() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonChoXacNhan(), HttpStatus.OK);
    }

    @GetMapping("xacnhan")
    public ResponseEntity<Integer> demSoHoaDonXacNhan() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonXacNhan(), HttpStatus.OK);
    }

    @GetMapping("chogiaohang")
    public ResponseEntity<Integer> demSoHoaDonChoGiaoHang() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonChoGiaoHang(), HttpStatus.OK);
    }

    @GetMapping("danggiao")
    public ResponseEntity<Integer> demSoHoaDonDangGiao() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonDangGiao(), HttpStatus.OK);
    }

    @GetMapping("thanhcong")
    public ResponseEntity<Integer> demSoHoaDonThanhCong() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonThanhCong(), HttpStatus.OK);
    }

    @GetMapping("dahuy")
    public ResponseEntity<Integer> demSoHoaDonDaHuy() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonDaHuy(), HttpStatus.OK);
    }

    @GetMapping("trahang")
    public ResponseEntity<Integer> demSoHoaDonTraHang() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonTraHang(), HttpStatus.OK);
    }
}
