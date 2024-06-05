package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface HoaDonChiTietAdminRepository extends JpaRepository<HoaDonChiTiet, UUID> {

    // detail hoá đơn bằng id sp
    @Query(value = "SELECT dbo.HoaDonChiTiet.*, dbo.SanPham.TenSP\n" +
            "FROM     dbo.HoaDonChiTiet INNER JOIN\n" +
            "                  dbo.SanPhamChiTiet ON dbo.HoaDonChiTiet.IdSPCT = dbo.SanPhamChiTiet.Id INNER JOIN\n" +
            "                  dbo.SanPham ON dbo.SanPhamChiTiet.IdSP = dbo.SanPham.Id where dbo.HoaDonChiTiet.IdHD = ?", nativeQuery = true)
    HoaDonChiTietRespon finByidHDCT(UUID idHD);

}
