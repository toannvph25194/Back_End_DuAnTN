package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface HinhThucThanhToanAdminRepository extends JpaRepository<HinhThucThanhToan, UUID> {
    // detail hình thức thanh toán bằng id hd
    @Query(value = "SELECT [Id]\n" +
            "      ,[IdHD]\n" +
            "      ,[IdKH]\n" +
            "      ,[IdNV]\n" +
            "      ,[MaGiaoDich]\n" +
            "      ,[NgayThanhToan]\n" +
            "      ,[SoTienTra]\n" +
            "      ,[NgayTao]\n" +
            "      ,[NgayCapNhat]\n" +
            "      ,[GhiChu]\n" +
            "      ,[TrangThai]\n" +
            "      ,[HinhThucThanhToan]\n" +
            "  FROM [dbo].[HinhThucThanhToan]\n" +
            "  where IdHD = ?", nativeQuery = true)
    HinhThucThanhToanRespon finByidHTTT(UUID idHD);
    


}
