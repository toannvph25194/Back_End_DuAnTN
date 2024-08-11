package com.example.be_duantn.repository.quan_ly_giam_gia_repository;

import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.GiamGiaRespon;
import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.SanPhamGiamGiaRespon;
import com.example.be_duantn.entity.GiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface GiamGiaRepository extends JpaRepository<GiamGia, UUID> {
    @Query(value = "SELECT [Id]\n" +
            "      ,[MaGiamGia]\n" +
            "      ,[TenGiamGia]\n" +
            "      ,[NgayTao]\n" +
            "      ,[NgayCapNhat]\n" +
            "      ,[NgayBatDau]\n" +
            "      ,[NgayKetThuc]\n" +
            "      ,[GiaTriGiam]\n" +
            "      ,[HinhThucGiam]\n" +
            "      ,[GhiChu]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[GiamGia]", nativeQuery = true)
    Page<GiamGiaRespon> GetAllMaGiamGia(Pageable pageable);

    @Query(value = "SELECT [Id]\n" +
            "      ,[MaGiamGia]\n" +
            "      ,[TenGiamGia]\n" +
            "      ,[NgayTao]\n" +
            "      ,[NgayCapNhat]\n" +
            "      ,[NgayBatDau]\n" +
            "      ,[NgayKetThuc]\n" +
            "      ,[GiaTriGiam]\n" +
            "      ,[HinhThucGiam]\n" +
            "      ,[GhiChu]\n" +
            "      ,[TrangThai] \n" +
            "  FROM [dbo].[GiamGia]\n" +
            "  WHERE [MaGiamGia] LIKE '%' + :magiamgia +'%'", nativeQuery = true)
    Page<GiamGiaRespon> TimKiemTheoMa(Pageable pageable, @Param("magiamgia") String magiamgia);

    @Query(value = "SELECT [Id], " +
            "      [MaGiamGia], " +
            "      [TenGiamGia], " +
            "      [NgayTao], " +
            "      [NgayCapNhat], " +
            "      [NgayBatDau], " +
            "      [NgayKetThuc], " +
            "      [GiaTriGiam], " +
            "      [HinhThucGiam], " +
            "      [GhiChu], " +
            "      [TrangThai] " +
            "  FROM [dbo].[GiamGia] " +
            "  WHERE CAST([NgayTao] AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)", nativeQuery = true)
    Page<GiamGiaRespon> TimKiemTheoKhoangNgay(Pageable pageable, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT [Id]\n" +
            "      ,[IdDM]\n" +
            "      ,[IdTH]\n" +
            "      ,[IdXX]\n" +
            "      ,[IdCL]\n" +
            "      ,[IdGG]\n" +
            "      ,[MaSP]\n" +
            "      ,[TenSP]\n" +
            "      ,[TheLoai]\n" +
            "      ,[ImageDefaul]\n" +
            "      ,[GiaNhap]\n" +
            "      ,[GiaBan]\n" +
            "      ,[DonGiaKhiGiam]\n" +
            "      ,[NgayThem]\n" +
            "      ,[NgayThemGiamGia]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[SanPham]\n" +
            "  where IdGG = ?", nativeQuery = true)
    Page<SanPhamGiamGiaRespon> GetAllSanPhamTheoID(Pageable pageable, UUID idgg);

    @Query(value = "SELECT dbo.SanPham.*, \n" +
            "       dbo.GiamGia.Id AS IdGG, \n" +
            "       dbo.GiamGia.TenGiamGia, \n" +
            "       dbo.GiamGia.GiaTriGiam, \n" +
            "       dbo.GiamGia.NgayBatDau, \n" +
            "       dbo.GiamGia.NgayKetThuc\n" +
            "FROM dbo.SanPham\n" +
            "LEFT JOIN dbo.GiamGia \n" +
            "    ON dbo.SanPham.IdGG = dbo.GiamGia.Id\n" +
            "WHERE dbo.GiamGia.TrangThai = 2 OR dbo.GiamGia.Id IS NULL;", nativeQuery = true)
    Page<SanPhamGiamGiaRespon> GetAllSanPhamGiamGiaThem(Pageable pageable);

    @Query(value = "SELECT dbo.SanPham.TenSP, dbo.SanPham.GiaBan,dbo.SanPham.ImageDefaul, dbo.GiamGia.MaGiamGia, dbo.GiamGia.TenGiamGia, dbo.SanPham.DonGiaKhiGiam, dbo.SanPham.NgayThemGiamGia\n" +
            "FROM     dbo.SanPham INNER JOIN\n" +
            "                  dbo.GiamGia ON dbo.SanPham.IdGG = dbo.GiamGia.Id where dbo.GiamGia.TrangThai = 1", nativeQuery = true)
    Page<SanPhamGiamGiaRespon> GetAllSanPhamGiamGia(Pageable pageable);

}
