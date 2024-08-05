package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadSPTaiQuayRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.LoadSPHoaDonChiTietRespon;
import com.example.be_duantn.entity.GioHangChiTiet;
import com.example.be_duantn.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietAdminRepository extends JpaRepository<HoaDonChiTiet, UUID> {

    // detail hoá đơn bằng id sp
    @Query(value = "SELECT dbo.HoaDonChiTiet.*, dbo.SanPham.TenSP, dbo.SanPham.ImageDefaul, dbo.MauSac.TenMauSac, dbo.Size.TenSize\n" +
            "FROM     dbo.HoaDonChiTiet INNER JOIN\n" +
            "                  dbo.SanPhamChiTiet ON dbo.HoaDonChiTiet.IdSPCT = dbo.SanPhamChiTiet.Id INNER JOIN\n" +
            "                  dbo.SanPham ON dbo.SanPhamChiTiet.IdSP = dbo.SanPham.Id INNER JOIN\n" +
            "                  dbo.MauSac ON dbo.SanPhamChiTiet.IdMS = dbo.MauSac.Id INNER JOIN\n" +
            "                  dbo.Size ON dbo.SanPhamChiTiet.IdSize = dbo.Size.Id where dbo.HoaDonChiTiet.IdHD = ?", nativeQuery = true)
    List<HoaDonChiTietRespon> finByidHDCT(UUID idHD);

    @Query(value = "SELECT COUNT(spct.Id), spct.Id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan, \n" +
            "       CASE \n" +
            "           WHEN gg.Id IS NOT NULL AND gg.TrangThai = 1 AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "           ELSE NULL \n" +
            "       END AS DonGiaKhiGiam\n" +
            "FROM SanPhamChiTiet spct\n" +
            "JOIN SanPham sp ON spct.IdSP = sp.Id\n" +
            "LEFT JOIN GiamGia gg ON gg.Id = sp.IdGG\n" +
            "JOIN MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN Size s ON s.Id = spct.IdSize\n" +
            "JOIN ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "JOIN XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN ThuongHieu th ON th.Id = sp.IdTH\n" +
            "WHERE spct.SoLuongTon > 0 \n" +
            "  AND sp.TrangThai = 1 \n" +
            "  AND spct.TrangThai = 1\n" +
            "  AND ms.TrangThai = 1 \n" +
            "  AND s.TrangThai = 1 \n" +
            "  AND cl.TrangThai = 1 \n" +
            "  AND dm.TrangThai = 1 \n" +
            "  AND th.TrangThai = 1 \n" +
            "  AND xx.TrangThai = 1\n" +
            "GROUP BY spct.Id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan,\n" +
            "         CASE \n" +
            "             WHEN gg.Id IS NOT NULL AND gg.TrangThai = 1 AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "             ELSE NULL \n" +
            "         END\n",
            nativeQuery = true)
    Page<LoadSPHoaDonChiTietRespon> LoadSPBanTaiQuay(Pageable pageable);


    // Lọc sản phẩm phân trang theo tên sản phẩm sửa hoá đơn
    @Query(value = "SELECT COUNT(spct.Id), spct.Id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan,\n" +
            "       CASE \n" +
            "           WHEN gg.Id IS NOT NULL AND gg.TrangThai = 1 AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "           ELSE NULL \n" +
            "       END AS DonGiaKhiGiam\n" +
            "FROM SanPhamChiTiet spct\n" +
            "JOIN SanPham sp ON spct.IdSP = sp.Id\n" +
            "LEFT JOIN GiamGia gg ON gg.Id = sp.IdGG AND gg.TrangThai = 1\n" +
            "JOIN MauSac ms ON ms.Id = spct.IdMS AND ms.TrangThai = 1\n" +
            "JOIN Size s ON s.Id = spct.IdSize AND s.TrangThai = 1\n" +
            "JOIN ChatLieu cl ON cl.Id = sp.IdCL AND cl.TrangThai = 1\n" +
            "JOIN XuatXu xx ON xx.Id = sp.IdXX AND xx.TrangThai = 1\n" +
            "JOIN DanhMuc dm ON dm.Id = sp.IdDM AND dm.TrangThai = 1\n" +
            "JOIN ThuongHieu th ON th.Id = sp.IdTH AND th.TrangThai = 1\n" +
            "WHERE spct.SoLuongTon > 0 \n" +
            "  AND sp.TrangThai = 1 \n" +
            "  AND spct.TrangThai = 1 \n" +
            "  AND (sp.TenSP LIKE '%' + :tensp + '%')\n" +
            "GROUP BY spct.Id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan,\n" +
            "         CASE \n" +
            "             WHEN gg.Id IS NOT NULL AND gg.TrangThai = 1 AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "             ELSE NULL \n" +
            "         END\n", nativeQuery = true)
    Page<LoadSPHoaDonChiTietRespon> LocTenSPSuaHoaDon(Pageable pageable, @Param("tensp") String tensp);

    // Lọc sản phẩm phân trang nhiều tiêu chí sửa hoá đơn
    @Query(value = "SELECT COUNT(spct.Id), spct.id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan, \n" +
            "       CASE \n" +
            "          WHEN gg.Id IS NOT NULL AND gg.TrangThai = 1 AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "          ELSE NULL \n" +
            "       END AS DonGiaKhiGiam \n" +
            "FROM SanPhamChiTiet spct\n" +
            "JOIN SanPham sp ON spct.IdSP = sp.Id\n" +
            "LEFT JOIN GiamGia gg ON gg.Id = sp.IdGG\n" +
            "JOIN MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN Size s ON s.Id = spct.IdSize\n" +
            "JOIN ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "JOIN DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN ThuongHieu th ON th.Id = sp.IdTH\n" +
            "JOIN XuatXu xx ON xx.Id = sp.IdXX\n" +
            "WHERE spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND spct.TrangThai = 1\n" +
            "AND ms.TrangThai = 1 AND s.TrangThai = 1 AND cl.TrangThai = 1\n" +
            "AND dm.TrangThai = 1 AND th.TrangThai = 1 AND xx.TrangThai = 1\n" +
            "AND (ms.TenMauSac LIKE :tenmausac OR s.TenSize LIKE :tensize OR cl.TenChatLieu LIKE :tenchatlieu \n" +
            "     OR dm.TenDanhMuc LIKE :tendanhmuc OR th.TenThuongHieu LIKE :tenthuonghieu OR xx.TenXuatXu LIKE :tenxuatxu)\n" +
            "GROUP BY spct.Id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan,\n" +
            "       CASE \n" +
            "          WHEN gg.Id IS NOT NULL AND gg.TrangThai = 1 AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "          ELSE NULL \n" +
            "       END\n", nativeQuery = true)
    Page<LoadSPHoaDonChiTietRespon> LocSPNhieuTieuChiSuaHoaDon(Pageable pageable, @Param("tenmausac") String tenmausac, @Param("tensize") String tensize,
                                                         @Param("tenchatlieu") String tenchatlieu, @Param("tendanhmuc") String tendanhmuc,
                                                         @Param("tenthuonghieu") String tenthuonghieu, @Param("tenxuatxu") String tenxuatxu);

    // Tìm kiếm idgh và idspct trong ghct
    HoaDonChiTiet findByHoadonIdhoadonAndSanphamchitietIdspct(UUID idhd, UUID idspct);

    List<HoaDonChiTiet> findByHoadon_Idhoadon(UUID idhd);

}


