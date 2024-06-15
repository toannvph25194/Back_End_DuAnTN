package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HoaDonCTBanTaiQuayRepository extends JpaRepository<HoaDonChiTiet, UUID> {
    // Tìm hóa đơn chi tiết của khách hàng bán tại quầy theo idhoadon
    List<HoaDonChiTiet> findByHoadon_Idhoadon(UUID idhd);
}
