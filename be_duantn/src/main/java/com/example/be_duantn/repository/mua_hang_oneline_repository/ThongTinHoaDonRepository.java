package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.TTHoaDonCTRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.TTHoaDonRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.TTSPHoaDonRespon;
import com.example.be_duantn.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ThongTinHoaDonRepository extends JpaRepository<HoaDon, UUID> {

    // Load thông tin hóa đơn của khách hàng
    @Query(value = "SELECT hd.Id, hd.MaHoaDon, hd.TenNguoiNhan, hd.NgayTao, hd.ThanhTien , hd.TrangThai From HoaDon hd\n" +
            "Where hd.LoaiHoaDon = 1 AND hd.IdKH = ? AND hd.TrangThai = ?", nativeQuery = true)
    List<TTHoaDonRespon> LoadTTHoaDonKhachHang(UUID idkh, Integer trangthai);

    // Load thông tin sản phẩm trong hóa đơn của khách hàng
    @Query(value = "SELECT sp.TenSP, sp.ImageDefaul, cl.TenChatLieu, ms.TenMauSac, s.TenSize, hdct.SoLuong, hdct.DonGia, hdct.DonGiaKhiGiam \n" +
            "FROM SanPhamChiTiet spct\n" +
            "JOIN HoaDonChiTiet hdct on hdct.IdSPCT = spct.Id\n" +
            "JOIN HoaDon hd on hd.Id = hdct.IdHD\n" +
            "JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "Where hd.LoaiHoaDon = 1 AND hd.id = ?", nativeQuery = true)
    List<TTSPHoaDonRespon> LoadTTSPHoaDonKhachHang(UUID idhoadon);

    // Tìm kiếm thông tin hóa đơn của khách theo mahoadon, tennguoinhan, tensanpham
    @Query(value = "SELECT DISTINCT hd.Id, hd.MaHoaDon, hd.TenNguoiNhan, hd.NgayTao, hd.ThanhTien, hd.TrangThai FROM HoaDon hd " +
            "JOIN HoaDonChiTiet hdct ON hdct.IdHD = hd.Id " +
            "JOIN SanPhamChiTiet spct ON spct.Id = hdct.IdSPCT " +
            "JOIN SanPham sp ON sp.Id = spct.IdSP " +
            "WHERE (hd.MaHoaDon LIKE %:keyword% OR hd.TenNguoiNhan LIKE %:keyword% OR sp.TenSP LIKE %:keyword%) " +
            "AND hd.TrangThai = :trangthai AND hd.LoaiHoaDon = 1 AND hd.IdKH = :idkh ", nativeQuery = true)
    List<TTHoaDonRespon> TimKiemHoaDonKhachHang(@Param("idkh") UUID idkh, @Param("keyword") String keyword, @Param("trangthai") Integer trangthai);

    // Find thông tin hóa đơn chi tiết
    @Query(value = "SELECT hd.MaHoaDon, hd.TenNguoiNhan, hd.SDTNguoiNhan, hd.DiaChiNhanHang, hd.TienGiaoHang, hd.GiaTriGiam, hd.ThanhTien From HoaDon hd\n" +
            "Where hd.LoaiHoaDon = 1 AND hd.Id = ?", nativeQuery = true)
    TTHoaDonCTRespon FinTTHoaDonCTKhachHang(UUID idhoadon);
}
