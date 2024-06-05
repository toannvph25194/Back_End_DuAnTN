package com.example.be_duantn.controller.quan_ly_hoa_don_controller;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HoaDonTrangThaiAdminRequest;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.service.quan_ly_hoa_don_service.Impl.HinhThucThanhToanAdminAdminServiceImpl;
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
@RequestMapping("/api/admin/hinhthucthanhtoan")
public class HinhThucThanhToanAdminController {
    @Autowired
    HinhThucThanhToanAdminAdminServiceImpl hinhThucThanhToanService;

    //api Load Table
    @GetMapping("/hienthihinhthucthanhtoantheoid")
    public ResponseEntity<?> hoadonchitiet(@RequestParam("IdHD") UUID IdHD) {
        return ResponseEntity.ok(hinhThucThanhToanService.finByIdHTTT(IdHD));
    }

    @PutMapping("/updatehinhthucthanhtoan/{idhttt}")
    public ResponseEntity<HinhThucThanhToan> updateHTTT(
            @PathVariable("idhttt") UUID idhttt,
            @Valid @RequestBody HinhThucThanhToanAdminRequest hoaDonTrangThaiAdminRequest

    ) {
        return new ResponseEntity<>(hinhThucThanhToanService.updateHTTT(idhttt, hoaDonTrangThaiAdminRequest), HttpStatus.OK);
    }

}
