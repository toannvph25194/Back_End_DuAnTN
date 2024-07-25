package com.example.be_duantn.controller.quan_ly_nguoi_dung_controller;

import com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl.QuanLyQuanLyKhachHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/quan-ly-khach-hang")
public class QuanLyKhachHangController {
    @Autowired
    QuanLyQuanLyKhachHangServiceImpl khachHangService;
}
