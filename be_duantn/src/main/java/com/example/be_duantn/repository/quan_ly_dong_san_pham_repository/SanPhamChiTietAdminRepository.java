package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamChiTietRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamChiTietAdminRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamChiTietManThemHoaDonRespon;
import com.example.be_duantn.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SanPhamChiTietAdminRepository extends JpaRepository<SanPhamChiTiet, UUID> {
    //xuất ra các spct
    @Query(value = "SELECT dbo.SanPhamChiTiet.Id, dbo.SanPhamChiTiet.SoLuongTon, dbo.SanPhamChiTiet.MoTa, dbo.SanPhamChiTiet.QrCode, dbo.SanPhamChiTiet.TrangThai, dbo.SanPhamChiTiet.NgayTao, dbo.Size.TenSize, dbo.MauSac.TenMauSac\n" +
            "FROM     dbo.SanPhamChiTiet INNER JOIN\n" +
            "                  dbo.Size ON dbo.SanPhamChiTiet.IdSize = dbo.Size.Id INNER JOIN\n" +
            "                  dbo.MauSac ON dbo.SanPhamChiTiet.IdMS = dbo.MauSac.Id where dbo.SanPhamChiTiet.IdSP = ?", nativeQuery = true)
    List<SanPhamChiTietAdminRespon> GetAllSanPhamChiTiet(UUID idsp);

    // detail sản phẩm bằng id sp
    @Query(value = "SELECT DISTINCT sp.Id, sp.TenSP, sp.TheLoai, sp.ImageDefaul, xx.TenXuatXu, th.TenThuongHieu, cl.TenChatLieu, sp.GiaBan,\n" +
            "CASE WHEN tgg.sotiengiamcuoicung IS NOT NULL THEN sp.GiaBan - tgg.sotiengiamcuoicung ELSE NULL END AS DonGiaKhiGiam\n" +
            "FROM SanPham sp\n" +
            "JOIN SanPhamChiTiet spct ON sp.Id = spct.IdSP\n" +
            "JOIN XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th ON th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "LEFT JOIN (\n" +
            "    SELECT spgg.IdSP, SUM(spgg.DonGia - spgg.DonGiaKhiGiam) AS sotiengiamcuoicung FROM SPGiamGia spgg\n" +
            "    GROUP BY spgg.IdSP\n" +
            ") tgg ON sp.Id = tgg.IdSP\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0 AND sp.Id = ?1", nativeQuery = true)
    SanPhamChiTietManThemHoaDonRespon finByidSP(UUID idsp);




}
