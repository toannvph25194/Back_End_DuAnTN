package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GiohangChiTietBanTaiQuayRepository extends JpaRepository<GioHangChiTiet, UUID> {
    // finBy ghct theo idgh
    List<GioHangChiTiet> findByGiohang_Idgh(UUID idgh);
}
