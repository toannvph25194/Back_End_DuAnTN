package com.example.be_duantn.repository.ban_hang_tai_quay_repository;


import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.KhachHangDiaChiRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadDiaChiTaiQuayRespon;
import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.UUID;

public interface KhachHangBanTaiQuayRepository extends JpaRepository<KhachHang, UUID> {
    // finBy tài khoản khách lẻ
    KhachHang findByTaikhoan(String taikhoan);

    //Load khách hàng
    @Query(value = "SELECT \n" +
            " kh.id AS idkh, \n" +
            " kh.hovatenkh, \n" +
            " kh.taikhoan, \n" +
            " kh.sodienthoai, \n" +
            " kh.email, \n" +
            " dc.id AS iddiachi, \n" +
            " dc.diachichitiet, \n" +
            " dc.phuongxa, \n" +
            " dc.quanhuyen, \n" +
            " dc.tinhthanh, \n" +
            " dc.quocgia, \n" +
            " kh.trangthai \n" +
            "FROM \n" +
            " KhachHang kh\n" +
            "LEFT JOIN \n" +
            " DiaChi dc \n" +
            "ON \n" +
            " kh.id = dc.idKH\n" +
            "WHERE \n" +
            " kh.trangthai IN (1, 2) \n" +
            "ORDER BY \n" +
            " CASE WHEN kh.trangthai = 2 THEN 0 ELSE 1 END", nativeQuery = true)
    Page<LoadDiaChiTaiQuayRespon> findAllWithTrangThai(Pageable pageable);


//Load Địa chị trạng thái bằng 1
    @Query(value = "SELECT \n" +
            "    KhachHang.*, \n" +
            "    DiaChi.*\n" +
            "FROM \n" +
            "    dbo.KhachHang\n" +
            "LEFT JOIN \n" +
            "    dbo.DiaChi \n" +
            "ON \n" +
            "    dbo.KhachHang.Id = dbo.DiaChi.IdKH\n" +
            "WHERE  \n" +
            "    (dbo.DiaChi.trangthai = 1 OR dbo.DiaChi.IdKH IS NULL)\n" +
            "    AND dbo.KhachHang.Id = ?;", nativeQuery = true)
    LoadDiaChiTaiQuayRespon finByidKh(UUID Idkh);



//Tìm kiếm theo tên khách hàng và sdt
    @Query(value = "SELECT \n" +
            " kh.id AS idkh, \n" +
            " kh.hovatenkh, \n" +
            " kh.taikhoan, \n" +
            " kh.sodienthoai, \n" +
            " kh.email, \n" +
            " dc.id AS iddiachi, \n" +
            " dc.diachichitiet, \n" +
            " dc.phuongxa, \n" +
            " dc.quanhuyen, \n" +
            " dc.tinhthanh, \n" +
            " dc.quocgia, \n" +
            " kh.trangthai \n" +
            "FROM \n" +
            " KhachHang kh\n" +
            "LEFT JOIN \n" +
            " DiaChi dc \n" +
            "ON \n" +
            " kh.id = dc.idKH\n" +
            "WHERE \n" +
            " kh.trangthai IN (1, 2) \n" +
            " AND (kh.sodienthoai LIKE %:keyword% OR kh.hovatenkh LIKE %:keyword%) \n" +
            "ORDER BY \n" +
            " CASE WHEN kh.trangthai = 2 THEN 0 ELSE 1 END",
            nativeQuery = true)
    Page<LoadDiaChiTaiQuayRespon> LocTenKHBanTaiQuay(@Param("keyword") String keyword, Pageable pageable);



}
