package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.FinGioHangBanTaiQuay;
import com.example.be_duantn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GioHangBanTaiQuayRepository extends JpaRepository<GioHang, UUID> {

    // Tìm kiếm giỏ hàng theo idkh bán tại quầy
    @Query(value = "SELECT gh.Id FROM GioHang gh\n" +
            "JOIN KhachHang kh on kh.Id = gh.IdKH\n" +
            "where IdKH = ?", nativeQuery = true)
    FinGioHangBanTaiQuay TimKiemGioHangBanTaiQuay(UUID idkh);
}
