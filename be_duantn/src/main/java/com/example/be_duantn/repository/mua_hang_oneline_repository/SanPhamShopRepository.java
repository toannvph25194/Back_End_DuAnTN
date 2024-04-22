package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;
import com.example.be_duantn.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SanPhamShopRepository extends JpaRepository<SanPham , UUID> {

    // Load danh mục sp shop theo trạng thái
    @Query(value = "SELECT * FROM DanhMuc Where TrangThai = 1",nativeQuery = true)
    List<DanhMucSPShopRespon> loadDanhMucSPShop();

    // Load màu sắc sp shop theo trạng thái
    @Query(value = "SELECT * FROM MauSac Where TrangThai = 1",nativeQuery = true)
    List<MauSacSPShopRespon> loadMauSacSPShop();

    // Load size sp shop theo trạng thái
    @Query(value = "SELECT * FROM Size Where TrangThai = 1",nativeQuery = true)
    List<SizeSPShopRespon> loadSizeSPShop();

    // load sp shop phân trang
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
    Page<SanPhamShopRespon> loadSPShop(Pageable pageable);

    // Lọc sp shop theo khoanggia
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id,MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan FROM SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0 AND dm.TrangThai = 1\n" +
            "AND xx.TrangThai = 1 AND th.TrangThai = 1 AND cl.TrangThai = 1\n" +
            "AND ms.TrangThai = 1 AND s.TrangThai = 1 AND \n" +
            "sp.GiaBan BETWEEN :key1 AND :key2\n" +
            " GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.ImageDefaul, sp.TheLoai, sp.GiaBan" , nativeQuery = true)
    Page<SanPhamShopRespon> locKhoangGiaSPShop(Pageable pageable, @Param("key1") Double key1, @Param("key2") Double key2);

    // Lọc theo tensp
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan FROM SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct on spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "Where spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND dm.TrangThai = 1\n" +
            "AND xx.TrangThai = 1 AND th.TrangThai = 1 AND cl.TrangThai = 1\n" +
            "AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "AND sp.TenSP LIKE %:tensp%\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan", nativeQuery = true)
    Page<SanPhamShopRespon> locTenSPShop(Pageable pageable, @Param("tensp") String tensp);

    // Lọc theo nhiều tiêu chí. tendanhmuc, tenmausac, tensize
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan FROM SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct on spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "Where spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND dm.TrangThai = 1\n" +
            "AND xx.TrangThai = 1 AND th.TrangThai = 1 AND cl.TrangThai = 1\n" +
            "AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "AND (dm.TenDanhMuc Like :tendanhmuc OR ms.TenMauSac Like :tenmausac\n" +
            "OR s.TenSize Like :tensize)\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan", nativeQuery = true)
    Page<SanPhamShopRespon> locSPShopNTC(Pageable pageable, @Param("tendanhmuc") String tendanhmuc,
                                      @Param("tenmausac") String tenmausac,
                                      @Param("tensize") String tensize);


}
