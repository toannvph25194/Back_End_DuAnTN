package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadSPTaiQuayRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.TongTienBanTaiQuayRespon;
import com.example.be_duantn.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TongTienBanTaiquayRepository extends JpaRepository<SanPhamChiTiet, UUID> {

    @Query(value = "SELECT \n" +
            "    hd.Id AS HoaDonId, \n" +
            "    SUM(COALESCE(hdct.dongiakhigiam, hdct.dongia) * hdct.soluong) AS TongTien\n" +
            "FROM dbo.HoaDon hd\n" +
            "INNER JOIN dbo.HoaDonChiTiet hdct ON hd.Id = hdct.IdHD\n" +
            "WHERE hd.Id = ?\n" +
            "GROUP BY \n" +
            "    hd.Id\n", nativeQuery = true)
    TongTienBanTaiQuayRespon TongTienBanTaiQuay(UUID id);

}
