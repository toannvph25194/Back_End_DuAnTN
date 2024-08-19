package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.VouCherRespon;
import com.example.be_duantn.entity.VouCher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VouCherBanTaiQuayRepository extends JpaRepository<VouCher, UUID> {

    // load voucher lên trang checkout
    @Query(value = "SELECT Id,MaVouCher,TenVouCher,NgayKetThuc,SoLuongMa,SoLuongDung,DieuKienToiThieuHoaDon,GiaTriGiam,HinhThucGiam FROM VOUCHER\n" +
            "WHERE TrangThai = 1 AND SoLuongMa > SoLuongDung \n" +
            "AND GETDATE() >= NgayBatDau AND NgayKetThuc >= GETDATE() ", nativeQuery = true)
    List<VouCherRespon> loadVouCher();


    // load voucher lên trang checkout
    @Query(value = "SELECT Id,MaVouCher,TenVouCher,NgayKetThuc,SoLuongMa,SoLuongDung,DieuKienToiThieuHoaDon,GiaTriGiam,HinhThucGiam FROM VOUCHER\n" +
            "WHERE TrangThai = 1 AND SoLuongMa > SoLuongDung \n" +
            "AND GETDATE() >= NgayBatDau AND NgayKetThuc >= GETDATE() AND Id = ?", nativeQuery = true)
    VouCherRespon loadVouCherTheoId(UUID id);

}
