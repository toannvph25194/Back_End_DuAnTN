package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.entity.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, UUID> {
    // detail hoá đơn bằng id sp
    @Query(value = "SELECT \n" +
            "      [Id],\n" +
            "      [IdHD],\n" +
            "      [NguoiThaoTac],\n" +
            "      [GhiChu],\n" +
            "      [TrangThai],\n" +
            "      [NgayTao]\n" +
            "  FROM [dbo].[LichSuHoaDon]\n" +
            "  WHERE IdHD = ?\n" +
            "  ORDER BY [NgayTao] DESC\n", nativeQuery = true)
    List<LichSuHoaDon> finByidLstt(UUID idHD);
}
