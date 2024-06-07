package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NhanVienBanTaiQuayRepository extends JpaRepository<NhanVien, UUID> {

    // finBy tài khoản nhân viên tại hóa đơn
    NhanVien findByTaikhoan(String taikhoan);
}
