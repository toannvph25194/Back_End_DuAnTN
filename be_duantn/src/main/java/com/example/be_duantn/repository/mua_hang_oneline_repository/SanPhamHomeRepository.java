package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamGiamGiaRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamRespon;
import com.example.be_duantn.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SanPhamHomeRepository extends JpaRepository<SanPham , UUID> {

    // getALl sp home phân trang
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id,MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan FROM SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0 \n" +
            "AND dm.TrangThai = 1 AND xx.TrangThai = 1 AND th.TrangThai = 1\n" +
            "AND cl.TrangThai = 1 AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan", nativeQuery = true)
    Page<SanPhamRespon> getALlSPHome(Pageable pageable);

    // getALl sp Nam home phân trang
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id,MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan FROM SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0  AND sp.TheLoai = 1\n" +
            "AND dm.TrangThai = 1 AND xx.TrangThai = 1 AND th.TrangThai = 1\n" +
            "AND cl.TrangThai = 1 AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan", nativeQuery = true)
    Page<SanPhamRespon> getALlSPNamHome(Pageable pageable);

    // getALl sp Nư home phân trang
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id,MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan FROM SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0  AND sp.TheLoai = 0\n" +
            "AND dm.TrangThai = 1 AND xx.TrangThai = 1 AND th.TrangThai = 1\n" +
            "AND cl.TrangThai = 1 AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan", nativeQuery = true)
    Page<SanPhamRespon> getALlSPNuHome(Pageable pageable);

    // getALl 6 sp new home
    @Query(value = "SELECT DISTINCT TOP 6 sp.Id,MaSP,TenSP,ImageDefaul,TheLoai,NgayThem ,sp.GiaBan  from SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct on spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0\n" +
            "AND dm.TrangThai = 1 AND xx.TrangThai = 1 AND th.TrangThai = 1\n" +
            "AND cl.TrangThai = 1 AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "ORDER BY sp.NgayThem DESC", nativeQuery = true)
    List<SanPhamRespon> getALlSPNewHome();

    // getAll 6 sp giảm giá home
    @Query(value = "SELECT DISTINCT TOP 6 sp.Id, sp.MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan, \n" +
            "       (sp.GiaBan - ISNULL(tgg.sotiengiamcuoicung, 0)) AS DonGiaKhiGiam, tgg.SoLuongBan\n" +
            "     FROM SanPham sp\n" +
            "     JOIN(SELECT spgg.IdSP, SUM(spgg.DonGia - spgg.DonGiaKhiGiam) AS sotiengiamcuoicung, SUM(spgg.SoLuongBan) AS SoLuongBan\n" +
            "       FROM SPGiamGia spgg\n" +
            "       JOIN GiamGia gg ON spgg.IdGG = gg.Id\n" +
            "       WHERE GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc\n" +
            "       GROUP BY spgg.IdSP\n" +
            "     )tgg ON sp.Id = tgg.IdSP\n" +
            "     JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "     WHERE spct.SoLuongTon > 0 AND sp.TrangThai = 1 \n" +
            "     ORDER BY tgg.SoLuongBan DESC;",nativeQuery = true)
    List<SanPhamGiamGiaRespon> getAllSPGGHome();
}
