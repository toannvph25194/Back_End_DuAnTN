package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonTaiQuayRequest;
import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.KhachHangDiaChiRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.KhachHangTaiQuayRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadDiaChiTaiQuayRespon;
import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.service.ban_hang_tai_quay_service.HoaDonBanTaiQuayService;
import com.example.be_duantn.service.ban_hang_tai_quay_service.KhachHangBanTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/khachhangbantaiquay/")
public class KhachHangBanTaiQuayController {

    @Autowired
    KhachHangBanTaiQuayService service;

    @Autowired
    HoaDonBanTaiQuayService hoaDonBanTaiQuayService;


    @GetMapping("khachhang")
    public Page<LoadDiaChiTaiQuayRespon> getKhachHangByTrangThai(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return service.getKhachHangByTrangThai(page, size);
    }

    @PutMapping("updateKhachHang")
    public ResponseEntity<Map<String, String>> updateKhachHang(@RequestBody HoaDonTaiQuayRequest request) {
        int updatedRows = hoaDonBanTaiQuayService.updateKhachHang(request.getIdhoadon(), request.getIdkh(), request.getHovatenkh());
        if (updatedRows == 0) {
            return ResponseEntity.notFound().build();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cập nhật thành công");
        return ResponseEntity.ok(response);
    }


    //api Load Table
    @GetMapping("hienthikhtheoid")
    public ResponseEntity<?> hoadonchitiet(@RequestParam("Idkh") UUID idkh) {
        return ResponseEntity.ok(service.finByIdKh(idkh));
    }

    @GetMapping("khachhangtimkiem")
    public Page<LoadDiaChiTaiQuayRespon> getKhachHangBySDT(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return service.LocTenKHBanTaiQuay(keyword, page, size);
    }




    @PostMapping("addkhachhang")
    public ResponseEntity<KhachHang> addKhachHang(@RequestBody KhachHangDiaChiRequest khachHangRequest) {
        DiaChi diaChi = new DiaChi();
        diaChi.setDiachichitiet(khachHangRequest.getDiachichitiet());
        diaChi.setPhuongxa(khachHangRequest.getPhuongxa());
        diaChi.setQuanhuyen(khachHangRequest.getQuanhuyen());
        diaChi.setTinhthanh(khachHangRequest.getTinhthanh());
        diaChi.setQuocgia(khachHangRequest.getQuocgia());
        diaChi.setTrangthai(khachHangRequest.getTrangthaiDiaChi());


        KhachHang khachHang = new KhachHang();
        khachHang.setHovatenkh(khachHangRequest.getHovatenkh());
        khachHang.setTaikhoan(khachHangRequest.getTaikhoan());
        khachHang.setMatkhau(khachHangRequest.getMatkhau());
        khachHang.setTrangthai(khachHangRequest.getTrangthai());
        khachHang.setSodienthoai(khachHangRequest.getSodienthoai());
        khachHang.setEmail(khachHangRequest.getEmail());

        KhachHang savedKhachHang = service.addKhachHang(khachHang, diaChi);
        return ResponseEntity.ok(savedKhachHang);
    }

}
