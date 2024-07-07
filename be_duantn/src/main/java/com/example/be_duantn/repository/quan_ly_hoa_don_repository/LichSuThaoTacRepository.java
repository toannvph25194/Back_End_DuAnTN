package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.LichSuTaoTac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LichSuThaoTacRepository extends JpaRepository<LichSuTaoTac, UUID> {
    // detail hoá đơn bằng id sp
    @Query(value = "SELECT [Id]\n" +
            "      ,[IdHD]\n" +
            "      ,[NguoiThaoTac]\n" +
            "      ,[GhiChu]\n" +
            "      ,[TrangThai]\n" +
            "      ,[NgayTao]\n" +
            "  FROM [dbo].[LichSuThaoTac]\n" +
            "  Where IdHD = ?", nativeQuery = true)
    List<LichSuTaoTac> finByidLstt(UUID idHD);
}
