package com.example.be_duantn.repository.authentication_repository;

import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface KhachHangRepository extends JpaRepository<KhachHang, UUID> {
    Optional<KhachHang> findByTaikhoan(String taikhoan);

}
