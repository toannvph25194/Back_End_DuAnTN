package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadGHCTTaiQuay;
import com.example.be_duantn.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GiohangChiTietBanTaiQuayRepository extends JpaRepository<GioHangChiTiet, UUID> {
    // finBy ghct theo idgh dùng cho giỏ hàng chi tiết và hóa đơn
    List<GioHangChiTiet> findByGiohang_Idgh(UUID idgh);

}
