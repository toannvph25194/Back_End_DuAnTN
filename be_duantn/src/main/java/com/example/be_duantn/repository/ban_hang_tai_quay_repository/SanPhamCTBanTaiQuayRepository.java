package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.*;
import com.example.be_duantn.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SanPhamCTBanTaiQuayRepository extends JpaRepository<SanPhamChiTiet, UUID> {

    // Load sản phẩm phân trang lên bán hàng tại quầy
    @Query(value = "SELECT COUNT(spct.Id), spct.id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan, \n" +
            "          CASE \n" +
            "             WHEN gg.Id IS NOT NULL AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "          ELSE NULL \n" +
            "               END AS DonGiaKhiGiam            \n" +
            "               FROM SanPhamChiTiet spct\n" +
            "               JOIN SanPham sp ON spct.IdSP = sp.Id\n" +
            "               LEFT JOIN GiamGia gg on gg.Id = sp.IdGG\n" +
            "               JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "               JOIN Size s on s.Id = spct.IdSize\n" +
            "               JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "               JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "               JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "               JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "      WHERE spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND spct.TrangThai = 1\n" +
            "      AND ms.TrangThai = 1 AND s.TrangThai = 1 AND cl.TrangThai = 1 \n" +
            "      AND dm.TrangThai = 1 AND th.TrangThai = 1 AND xx.TrangThai = 1\n" +
            "      GROUP BY spct.Id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan,\n" +
            "      CASE \n" +
            "         WHEN gg.Id IS NOT NULL AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "         ELSE NULL \n" +
            "      END", nativeQuery = true)
    Page<LoadSPTaiQuayRespon> LoadSPBanTaiQuay(Pageable pageable);

    // Load màu săc lên bán hàng tại quầy
    @Query(value = "SELECT * FROM MauSac Where TrangThai = 1",nativeQuery = true)
    List<LoadMSTaiQuayRespon> LoadMauSacBanTaiQuay();

    // Load size lên bán hàng tại quầy
    @Query(value = "SELECT * FROM Size Where TrangThai = 1",nativeQuery = true)
    List<LoadSizeTaiQuayRespon> LoadSizeBanTaiQuay();

    // Load chất liệu lên bán hàng tại quầy
    @Query(value = "SELECT * FROM ChatLieu Where TrangThai = 1",nativeQuery = true)
    List<LoadChatLieuTaiQuayRespon> LoadChatLieuBanTaiQuay();

    // Load danh mục lên bán hàng tại quầy
    @Query(value = "SELECT * FROM DanhMuc Where TrangThai = 1",nativeQuery = true)
    List<LoadDanhMucTaiQuayRespon> LoadDanhMucBanTaiQuay();

    // Load thương hiệu lên bán hàng tại quầy
    @Query(value = "SELECT * FROM ThuongHieu Where TrangThai = 1",nativeQuery = true)
    List<LoadThuongHieuTaiQuayRespon> LoadThuongHieuBanTaiQuay();

    // Load xuất xứ lên bán hàng tại quầy
    @Query(value = "SELECT * FROM XuatXu Where TrangThai = 1",nativeQuery = true)
    List<LoadXuatXuTaiQuayRespon> LoadXuatXuBanTaiQuay();

    // Lọc sản phẩm phân trang theo tên sản phẩm bán hàng tạ quầy
    @Query(value = "SELECT COUNT(spct.Id), spct.id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan, \n" +
            "                   CASE \n" +
            "                      WHEN gg.Id IS NOT NULL AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "                   ELSE NULL \n" +
            "                   END AS DonGiaKhiGiam \n" +
            "                   FROM SanPhamChiTiet spct\n" +
            "                   JOIN SanPham sp ON spct.IdSP = sp.Id\n" +
            "                   LEFT JOIN GiamGia gg on gg.Id = sp.IdGG\n" +
            "                   JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "                   JOIN Size s on s.Id = spct.IdSize\n" +
            "                   JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "                   JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "                   JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "                   JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "                   WHERE spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND spct.TrangThai = 1\n" +
            "                   AND ms.TrangThai = 1 AND s.TrangThai = 1 AND cl.TrangThai = 1 \n" +
            "                   AND xx.TrangThai = 1 AND dm.TrangThai = 1 AND th.TrangThai = 1 AND sp.TenSP LIKE %:tensp%\n" +
            "                   GROUP BY spct.Id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan,\n" +
            "                   CASE \n" +
            "                      WHEN gg.Id IS NOT NULL AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "                      ELSE NULL \n" +
            "                   END", nativeQuery = true)
    Page<LoadSPTaiQuayRespon> LocTenSPBanTaiQuay(Pageable pageable, @Param("tensp") String tensp);

    // Lọc sản phẩm phân trang nhiều tiêu chí bán hàng tạ quầy
    @Query(value = "SELECT COUNT(spct.Id), spct.id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan, \n" +
            "               CASE \n" +
            "                  WHEN gg.Id IS NOT NULL AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "               ELSE NULL \n" +
            "               END AS DonGiaKhiGiam \n" +
            "               FROM SanPhamChiTiet spct\n" +
            "               JOIN SanPham sp ON spct.IdSP = sp.Id\n" +
            "               LEFT JOIN GiamGia gg on gg.Id = sp.IdGG\n" +
            "               JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "               JOIN Size s on s.Id = spct.IdSize\n" +
            "               JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "               JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "               JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "               JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "               WHERE spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND spct.TrangThai = 1\n" +
            "               AND ms.TrangThai = 1 AND s.TrangThai = 1 AND cl.TrangThai = 1\n" +
            "               AND dm.TrangThai = 1 AND th.TrangThai = 1 AND xx.TrangThai = 1\n" +
            "               AND(ms.TenMauSac LIKE :tenmausac OR s.TenSize LIKE :tensize OR cl.TenChatLieu LIKE :tenchatlieu \n" +
            "               OR dm.TenDanhMuc LIKE :tendanhmuc OR th.TenThuongHieu LIKE :tenthuonghieu OR xx.TenXuatXu LIKE :tenxuatxu)\n" +
            "               GROUP BY spct.Id, sp.TenSP, ms.TenMauSac, s.TenSize, cl.TenChatLieu, sp.ImageDefaul, sp.TheLoai, spct.SoLuongTon, sp.TrangThai, sp.GiaBan,\n" +
            "               CASE \n" +
            "                  WHEN gg.Id IS NOT NULL AND GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc THEN sp.DonGiaKhiGiam\n" +
            "                  ELSE NULL \n" +
            "               END", nativeQuery = true)
    Page<LoadSPTaiQuayRespon> LocSPNhieuTieuChiBanTaiQuay(Pageable pageable, @Param("tenmausac") String tenmausac, @Param("tensize") String tensize,
                                                          @Param("tenchatlieu") String tenchatlieu, @Param("tendanhmuc") String tendanhmuc,
                                                          @Param("tenthuonghieu") String tenthuonghieu, @Param("tenxuatxu") String tenxuatxu);
}
