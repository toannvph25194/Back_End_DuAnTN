package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.HinhThucThanhToanBanTaiQuayRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HinhThucThanhToanAdminBanTaiQuayRepository extends JpaRepository<HinhThucThanhToan, UUID> {

    @Query(value = "SELECT dbo.HinhThucThanhToan.*, dbo.NhanVien.*\n" +
            "FROM dbo.HinhThucThanhToan \n" +
            "LEFT JOIN dbo.NhanVien \n" +
            "    ON dbo.HinhThucThanhToan.IdNV = dbo.NhanVien.Id\n" +
            "\twhere dbo.HinhThucThanhToan.IdHD= ?", nativeQuery = true)
    List<HinhThucThanhToanBanTaiQuayRespon> finByidHTTT(UUID idHD);

    List<HinhThucThanhToan> findHinhThucThanhToanByHoadonIdhoadon(UUID idhd);


}
