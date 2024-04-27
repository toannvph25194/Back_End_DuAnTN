package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.GioHangChiTietRespon;
import com.example.be_duantn.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, UUID> {

    // load ghct theo id giỏ hàng
    @Query(value = "SELECT ghct.Id AS Idghct, ghct.DonGia, ghct.DonGiaKhiGiam, ghct.SoLuong, sp.Id AS Idsp, sp.TenSP, sp.ImageDefaul, ms.TenMauSac, s.TenSize, spct.SoLuongTon FROM GioHangChiTiet ghct\n" +
            "JOIN GioHang gh on gh.Id = ghct.IdGH\n" +
            "JOIN SanPhamChiTiet spct on spct.Id = ghct.IdSPCT\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize \n" +
            "JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "Where ghct.SoLuong > 0 AND gh.TrangThai = 1\n" +
            "AND gh.Id = ?", nativeQuery = true)
    List<GioHangChiTietRespon> loadGioHangChiTiet(UUID idgh);

    // Tìm kiếm idgh và idspct trong ghct
    GioHangChiTiet findByGiohang_IdghAndSanphamchitiet_Idspct(UUID idgh, UUID idspct);

}
