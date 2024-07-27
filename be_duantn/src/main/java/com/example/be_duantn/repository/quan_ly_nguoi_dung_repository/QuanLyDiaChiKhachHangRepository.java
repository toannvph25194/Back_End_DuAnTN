package com.example.be_duantn.repository.quan_ly_nguoi_dung_repository;

import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QuanLyDiaChiKhachHangRepository extends JpaRepository<DiaChi, UUID> {

    // Tìm kiếm địa chỉ khách hàng có trạng thái là 1
    Optional<DiaChi> findByKhachhangAndTrangthai(KhachHang khachHang, Integer trangthai);
}
