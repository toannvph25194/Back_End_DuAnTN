package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;
import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KhachHangBanTaiQuayRepository extends JpaRepository<KhachHang, UUID> {
    // finBy tài khoản khách lẻ
    KhachHang findByTaikhoan(String taikhoan);
}
