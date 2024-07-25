package com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl;

import com.example.be_duantn.repository.quan_ly_nguoi_dung_repository.QuanLyKhachHangRepository;
import com.example.be_duantn.service.quan_ly_nguoi_dung_service.QuanLyKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuanLyQuanLyKhachHangServiceImpl implements QuanLyKhachHangService {
    @Autowired
    QuanLyKhachHangRepository quanLyKhachHangRepository;
}
