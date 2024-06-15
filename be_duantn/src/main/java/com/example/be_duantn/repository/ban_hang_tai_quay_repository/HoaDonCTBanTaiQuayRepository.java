package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHDCTTaiQuay;
import com.example.be_duantn.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HoaDonCTBanTaiQuayRepository extends JpaRepository<HoaDonChiTiet, UUID> {
    // Tìm hóa đơn chi tiết của khách hàng bán tại quầy theo idhoadon
    List<HoaDonChiTiet> findByHoadon_Idhoadon(UUID idhd);

    // load hóa đơn chi tiết tại quầy theo idgh của khác
    @Query(value = "SELECT hdct.Id AS Idhdct, hdct.DonGia, hdct.DonGiaKhiGiam, hdct.SoLuong, sp.Id AS Idsp, sp.TenSP, sp.ImageDefaul, ms.TenMauSac, s.TenSize, cl.TenChatLieu, spct.SoLuongTon FROM HoaDonChiTiet hdct\n" +
            "                   JOIN HoaDon hd on hd.Id = hdct.IdHD\n" +
            "                   JOIN SanPhamChiTiet spct on spct.Id = hdct.IdSPCT\n" +
            "                   JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "                   JOIN Size s on s.Id = spct.IdSize\n" +
            "                   JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "                   JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "                   Where hdct.TrangThai = 1 AND hdct.SoLuong > 0 AND hd.TrangThai = 1 \n" +
            "                   AND hd.Id = ?",nativeQuery = true)
    List<LoadHDCTTaiQuay> loadHDCTBanTaiQuay(UUID idhd);

    // finBy hdct theo idhd và idspct bán hàng tại quầy
    HoaDonChiTiet findByHoadon_IdhoadonAndSanphamchitiet_Idspct(UUID idhd, UUID idspct);
}
