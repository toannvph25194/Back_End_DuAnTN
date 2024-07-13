package com.example.be_duantn.controller.quan_ly_hoa_don_controller;

import com.example.be_duantn.service.quan_ly_hoa_don_service.Impl.LichSuHoaDonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/lichsuthaotac")
public class LichSuThaoTacController {
    @Autowired
    LichSuHoaDonServiceImpl lichSuThaoTacService;

    //api Load Table
    @GetMapping("/hienthilichsuthaotactheoid")
    public ResponseEntity<?> hoadonchitiet(@RequestParam("IdHD") UUID IdHD) {
        return ResponseEntity.ok(lichSuThaoTacService.finByIdLSTT(IdHD));
    }
}
