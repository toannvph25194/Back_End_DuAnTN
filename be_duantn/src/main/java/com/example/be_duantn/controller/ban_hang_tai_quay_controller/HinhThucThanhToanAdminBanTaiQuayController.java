package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.HinhThucThanhToanAdminBanTaiQuayServiceImpl;
import com.example.be_duantn.service.quan_ly_hoa_don_service.Impl.HinhThucThanhToanAdminAdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/bantaiquay/hinhthucthanhtoan")
public class HinhThucThanhToanAdminBanTaiQuayController {
    @Autowired
    HinhThucThanhToanAdminBanTaiQuayServiceImpl hinhThucThanhToanService;

    //api Load Table
    @GetMapping("/hienthihinhthucthanhtoantheoid")
    public ResponseEntity<?> hoadonchitiet(@RequestParam("IdHD") UUID IdHD) {
        return ResponseEntity.ok(hinhThucThanhToanService.finByIdHTTT(IdHD));
    }

    @PostMapping("/Addhttttienmat")
    public ResponseEntity<HinhThucThanhToan> AddHTTTKhiHT(
            @RequestParam("idhd") UUID idhd,
            @RequestParam("TienCuoiCung") Double TienCuoiCung,
            @RequestParam("TienKhachDua") Double TienKhachDua,
            Principal principal
    ) {
        String username = principal.getName();
        return new ResponseEntity<>(hinhThucThanhToanService.AddHTTTMoiKhiThanhToanTienMat(idhd, TienCuoiCung, TienKhachDua, username), HttpStatus.OK);
    }

    @PostMapping("/Addhtttchuyenkhoan")
    public ResponseEntity<HinhThucThanhToan> AddHTTTchuyenkhoan(
            @RequestParam("idhd") UUID idhd,
            @RequestParam("TienCuoiCung") Double TienCuoiCung,
            @RequestParam("magiaodich") String magiaodich,
            Principal principal
    ) {
        String username = principal.getName();
        return new ResponseEntity<>(hinhThucThanhToanService.AddHTTTMoiKhiThanhToanChuyenKhoan(idhd, TienCuoiCung, magiaodich, username), HttpStatus.OK);
    }


}
