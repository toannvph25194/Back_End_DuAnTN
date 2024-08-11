package com.example.be_duantn.repository.quan_ly_giam_gia_repository;

import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.VouCherAdminRespon;
import com.example.be_duantn.entity.VouCher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface VouCherAdminRepository extends JpaRepository<VouCher, UUID> {

    @Query(value = "SELECT [Id]\n" +
            "      ,[MaVouCher]\n" +
            "      ,[TenVouCher]\n" +
            "      ,[NgayTao]\n" +
            "      ,[NgayBatDau]\n" +
            "      ,[NgayCapNhat]\n" +
            "      ,[NgayKetThuc]\n" +
            "      ,[SoLuongMa]\n" +
            "      ,[SoLuongDung]\n" +
            "      ,[GiaTriGiam]\n" +
            "      ,[DieuKienToiThieuHoaDon]\n" +
            "      ,[HinhThucGiam]\n" +
            "      ,[LoaiVouCher]\n" +
            "      ,[GhiChu]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[VouCher]", nativeQuery = true)
    Page<VouCherAdminRespon> GetAllVoucher(Pageable pageable);

    @Query(value = "SELECT [Id]\n" +
            "      ,[MaVouCher]\n" +
            "      ,[TenVouCher]\n" +
            "      ,[NgayTao]\n" +
            "      ,[NgayBatDau]\n" +
            "      ,[NgayCapNhat]\n" +
            "      ,[NgayKetThuc]\n" +
            "      ,[SoLuongMa]\n" +
            "      ,[SoLuongDung]\n" +
            "      ,[GiaTriGiam]\n" +
            "      ,[DieuKienToiThieuHoaDon]\n" +
            "      ,[HinhThucGiam]\n" +
            "      ,[LoaiVouCher]\n" +
            "      ,[GhiChu]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[VouCher] where MaVouCher LIKE '%' + :mavoucher +'%'", nativeQuery = true)
    Page<VouCherAdminRespon> TimKiemTheoMa(Pageable pageable, @Param("mavoucher") String magiamgia);

    @Query(value = "SELECT [Id]\n" +
            "      ,[MaVouCher]\n" +
            "      ,[TenVouCher]\n" +
            "      ,[NgayTao]\n" +
            "      ,[NgayBatDau]\n" +
            "      ,[NgayCapNhat]\n" +
            "      ,[NgayKetThuc]\n" +
            "      ,[SoLuongMa]\n" +
            "      ,[SoLuongDung]\n" +
            "      ,[GiaTriGiam]\n" +
            "      ,[DieuKienToiThieuHoaDon]\n" +
            "      ,[HinhThucGiam]\n" +
            "      ,[LoaiVouCher]\n" +
            "      ,[GhiChu]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[VouCher] WHERE CAST([NgayTao] AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)", nativeQuery = true)
    Page<VouCherAdminRespon> TimKiemTheoKhoangNgay(Pageable pageable, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT [Id]\n" +
            "      ,[MaVouCher]\n" +
            "      ,[TenVouCher]\n" +
            "      ,[NgayTao]\n" +
            "      ,[NgayBatDau]\n" +
            "      ,[NgayCapNhat]\n" +
            "      ,[NgayKetThuc]\n" +
            "      ,[SoLuongMa]\n" +
            "      ,[SoLuongDung]\n" +
            "      ,[GiaTriGiam]\n" +
            "      ,[DieuKienToiThieuHoaDon]\n" +
            "      ,[HinhThucGiam]\n" +
            "      ,[LoaiVouCher]\n" +
            "      ,[GhiChu]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[VouCher] WHERE [Id] = ?", nativeQuery = true)
    Optional<VouCherAdminRespon> GetAllVouCherTheoID(@Param("id") UUID id);

}
