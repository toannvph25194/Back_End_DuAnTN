package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;
import com.example.be_duantn.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HoaDonBanTaiQuayRepository extends JpaRepository<HoaDon, UUID> {

    // load thông tin hóa đơn bán tại quầy
    @Query(value = "SELECT hd.Id, hd.MaHoaDon, nv.HoVaTenNV, hd.TenNguoiNhan, hd.NgayTao, hd.LoaiHoaDon, hd.TrangThai FROM HoaDon hd \n" +
            "JOIN NhanVien nv on nv.Id = hd.IdNV\n" +
            "Where hd.LoaiHoaDon = 2 AND hd.TrangThai = 1",nativeQuery = true)
    List<LoadHoaDonRespon> LoadHoaDonTaiQuay();

}
