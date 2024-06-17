package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;
import com.example.be_duantn.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HoaDonBanTaiQuayRepository extends JpaRepository<HoaDon, UUID> {

    // load thông tin hóa đơn bán tại quầy
    @Query(value = "SELECT hd.Id, hd.IdKH, hd.MaHoaDon, nv.HoVaTenNV, hd.TenNguoiNhan, hd.NgayTao, hd.LoaiHoaDon, hd.TrangThai FROM HoaDon hd \n" +
            "JOIN NhanVien nv on nv.Id = hd.IdNV\n" +
            "Where hd.LoaiHoaDon = 2 AND hd.TrangThai = 1",nativeQuery = true)
    List<LoadHoaDonRespon> LoadHoaDonTaiQuay();

    // Tìm kiếm hóa đơn bán tại quầy
    @Query(value = "SELECT hd.Id, hd.IdKH, hd.MaHoaDon, nv.HoVaTenNV, hd.TenNguoiNhan, hd.NgayTao, hd.LoaiHoaDon, hd.TrangThai FROM HoaDon hd \n" +
            "JOIN NhanVien nv on nv.Id = hd.IdNV\n" +
            "Where hd.LoaiHoaDon = 2 AND hd.TrangThai = 1 AND hd.MaHoaDon LIKE %:mahoadon%\n" +
            "ORDER BY hd.NgayTao DESC" , nativeQuery = true)
    List<LoadHoaDonRespon> TimKiemHoaDonTaiQuay(@Param("mahoadon") String mahoadon);


    //Hùng Làm Update khách hàng ở hóa đơn
    @Modifying
    @Query("UPDATE HoaDon h SET h.khachhang.idkh = :idkh, h.tennguoinhan = :hovatenkh WHERE h.idhoadon = :idhoadon")
    int updateKhachHangByIdHoaDon(@Param("idkh") UUID idkh, @Param("idhoadon") UUID idhoadon, @Param("hovatenkh") String hovatenkh);
}
