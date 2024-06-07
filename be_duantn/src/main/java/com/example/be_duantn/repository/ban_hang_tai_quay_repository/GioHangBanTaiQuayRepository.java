package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface GioHangBanTaiQuayRepository extends JpaRepository<GioHang, UUID> {
    // finby idgh theo idkh
    @Query(value = "SELECT gh.Id FROM GioHang gh\n" +
            "Where gh.IdKH = ?", nativeQuery = true)
    UUID TimKiemIdGioHang(UUID idkh);
}
