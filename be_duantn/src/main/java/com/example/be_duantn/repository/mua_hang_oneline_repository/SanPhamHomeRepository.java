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
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0 \n" +
            "AND dm.TrangThai = 1 AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan", nativeQuery = true)
    Page<SanPhamRespon> getALlSPHome(Pageable pageable);

    // getALl sp Nam Nữ home phân trang
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id,MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan FROM SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0  AND sp.TheLoai = ?1\n" +
            "AND dm.TrangThai = 1 AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan", nativeQuery = true)
    Page<SanPhamRespon> getALlSPNamNuHome(Pageable pageable,@Param("theloai") Integer theloai);

    // getALl 6 sp new home
    @Query(value = "SELECT DISTINCT TOP 6 sp.Id,MaSP,TenSP,ImageDefaul,TheLoai,NgayThem ,sp.GiaBan  from SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN SanPhamChiTiet spct on spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0\n" +
            "AND dm.TrangThai = 1 AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "ORDER BY sp.NgayThem DESC", nativeQuery = true)
    List<SanPhamRespon> getALlSPNewHome();

    // getAll 6 sp giảm giá home
    @Query(value = "SELECT DISTINCT TOP 6 sp.MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, spgg.Id, spgg.DonGia, spgg.DonGiaKhiGiam FROM SPGiamGia spgg\n" +
            "            JOIN SanPham sp on sp.id = spgg.IdSP\n" +
            "            JOIN GiamGia gg on gg.Id = spgg.IdGG\n" +
            "            JOIN SanPhamChiTiet spct on spct.IdSP = sp.Id\n" +
            "            Where GETDATE() >= gg.NgayBatDau AND gg.NgayKetThuc >= GETDATE() \n" +
            "              AND spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND gg.TrangThai = 1\n" +
            "              AND spct.TrangThai = 1",nativeQuery = true)
    List<SanPhamGiamGiaRespon> getAllSPGGHome();
}
