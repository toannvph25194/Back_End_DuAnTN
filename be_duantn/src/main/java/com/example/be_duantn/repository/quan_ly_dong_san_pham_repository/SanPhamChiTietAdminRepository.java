package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamChiTietAdminRespon;
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
}
