package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HinhThucThanhToanAdminRepository extends JpaRepository<HinhThucThanhToan, UUID> {

    // detail hình thức thanh toán bằng id hd
    @Query(value = "SELECT dbo.HinhThucThanhToan.*, dbo.NhanVien.*\n" +
            "FROM dbo.HinhThucThanhToan \n" +
            "LEFT JOIN dbo.NhanVien \n" +
            "    ON dbo.HinhThucThanhToan.IdNV = dbo.NhanVien.Id\n" +
            "\twhere dbo.HinhThucThanhToan.IdHD= ?", nativeQuery = true)
    List<HinhThucThanhToanRespon> finByidHTTT(UUID idHD);

    List<HinhThucThanhToan> findHinhThucThanhToanByHoadonIdhoadon(UUID idhd);




}
