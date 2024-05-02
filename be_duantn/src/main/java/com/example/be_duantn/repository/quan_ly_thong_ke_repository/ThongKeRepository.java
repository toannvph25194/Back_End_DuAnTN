package com.example.be_duantn.repository.quan_ly_thong_ke_repository;

import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.dto.respon.thong_ke_respon.DoanhThuResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ThongKeRepository extends JpaRepository<HoaDon, UUID> {

    //Lấy Tổng Doanh Thu Mỗi Ngày
    @Query(value = "SELECT CONVERT(date, CONVERT(date, hoadon.ngaycapnhat)) AS Ngay, SUM(hoadon.thanhtien) AS DoanhThu\n" +
            "FROM dbo.hoadon \n" +
            "WHERE hoadon.trangthai = 5 AND CONVERT(date, hoadon.ngaycapnhat) BETWEEN ? AND ?\n" +
            "GROUP BY CONVERT(date, CONVERT(date, hoadon.ngaycapnhat))\n" +
            "ORDER BY Ngay;", nativeQuery = true)
    List<DoanhThuResponse> doanhThu(Date ngayBd,Date ngayKt);

    //Lấy Tổng tiền ngày hôm nay
    @Query(value = "select SUM(hoadon.thanhtien) from\n" +
            "dbo.hoadon " +
            "where hoadon.trangthai = 5 AND CONVERT(date, hoadon.ngaycapnhat) = CONVERT(DATE, GETDATE())", nativeQuery = true)
    BigDecimal tongTienDonHomNay();

    //Lấy Tổng tiền tuần này
    @Query(value = "select SUM(hoadon.thanhtien) from\n" +
            "dbo.hoadon " +
            "where hoadon.trangthai = 5 AND DATEPART(WEEK, hoadon.ngaycapnhat) = DATEPART(WEEK, GETDATE()) AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());\n", nativeQuery = true)
    BigDecimal tongTienDonTuanNay();

    //Lấy Tổng tiền tháng này
    @Query(value = "select SUM(hoadon.thanhtien) from\n" +
            "dbo.hoadon " +
            "where hoadon.trangthai = 5 AND MONTH(hoadon.ngaycapnhat) = MONTH(GETDATE()) AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());\n", nativeQuery = true)
    BigDecimal tongTienDonThangNay();

    //Lấy Tổng tiền năm này
    @Query(value = "select SUM(hoadon.thanhtien) from\n" +
            "dbo.hoadon " +
            "where hoadon.trangthai = 5 AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE())", nativeQuery = true)
    BigDecimal tongTienDonNamNay();

    //Lấy Số Hóa Đơn Hôm nay
    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon WHERE CONVERT(date, hoadon.ngaycapnhat) = CONVERT(DATE, GETDATE())", nativeQuery = true)
    Integer soDonHomNay();

    //Lấy số hóa tuần này
    @Query(value = "SELECT COUNT(*) AS TongSoDonHang\n" +
            "FROM dbo.hoadon \n" +
            "WHERE DATEPART(WEEK, hoadon.ngaycapnhat) = DATEPART(WEEK, GETDATE()) \n" +
            "  AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonTuanNay();

    //Lấy số hóa đơn tháng này
    @Query(value = "SELECT COUNT(*) AS TongSoDonHang\n" +
            "FROM dbo.hoadon \n" +
            "WHERE MONTH(hoadon.ngaycapnhat) = MONTH(GETDATE()) \n" +
            "AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonThangNay();

    //Lấy số hóa đơn năm này
    @Query(value = "SELECT count(*)\n" +
            "FROM dbo.hoadon \n" +
            "WHERE YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());\n", nativeQuery = true)
    Integer soDonNamNay();

    //Lấy số hóa đơn hủy hôm nay
    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon WHERE hoadon.trangthai = 6 AND CONVERT(date, hoadon.ngaycapnhat) = CONVERT(DATE, GETDATE());", nativeQuery = true)
    Integer soDonHuyHomNay();

    //Lấy số hóa đơn hủy tuần này
    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon\n" +
            "WHERE hoadon.trangthai = 6\n" +
            "AND DATEPART(WEEK, hoadon.ngaycapnhat) = DATEPART(WEEK, GETDATE())\n" +
            "AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonHuyTuanNay();

    //Lấy số hóa đơn hủy tháng này
    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon \n" +
            "WHERE hoadon.trangthai = 6 AND MONTH(hoadon.ngaycapnhat) = MONTH(GETDATE()) \n" +
            "AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonHuyThangNay();

    //Lấy số hóa đơn hủy năm này
    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon \n" +
            "WHERE hoadon.trangthai = 6 AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonHuyNamNay();

    //Lấy số sản phẩm hôm nay
    @Query(value = "SELECT SUM(dbo.hoadonchitiet.soluong) FROM \n" +
            "dbo.hoadon INNER JOIN dbo.hoadonchitiet \n" +
            "ON dbo.hoadon.id = dbo.hoadonchitiet.idhd WHERE CONVERT(date, hoadon.ngaycapnhat) = CONVERT(DATE, GETDATE())", nativeQuery = true)
    Integer soSanPhamBanRaHomNay();

    //Lấy số sản phẩm tuần này
    @Query(value = "SELECT SUM(dbo.hoadonchitiet.soluong) \n" +
            "FROM dbo.hoadon \n" +
            "INNER JOIN dbo.hoadonchitiet ON dbo.hoadon.id = dbo.hoadonchitiet.idhd \n" +
            "WHERE DATEPART(WEEK, hoadon.ngaycapnhat) = DATEPART(WEEK, GETDATE()) \n" +
            "  AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());\n", nativeQuery = true)
    Integer soSanPhamBanRaTuanNay();

    //Lấy số sản phẩm tháng này
    @Query(value = "SELECT SUM(dbo.hoadonchitiet.soluong) \n" +
            "FROM dbo.hoadon \n" +
            "INNER JOIN dbo.hoadonchitiet ON dbo.hoadon.id = dbo.hoadonchitiet.idhd \n" +
            "WHERE MONTH(hoadon.ngaycapnhat) = MONTH(GETDATE()) \n" +
            "AND YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());", nativeQuery = true)
    Integer soSanPhamBanRaThangNay();

    //Lấy số sản phẩm năm này
    @Query(value = "SELECT SUM(dbo.hoadonchitiet.soluong) \n" +
            "FROM dbo.hoadon \n" +
            "INNER JOIN dbo.hoadonchitiet ON dbo.hoadon.id = dbo.hoadonchitiet.idhd \n" +
            "WHERE YEAR(hoadon.ngaycapnhat) = YEAR(GETDATE());\n", nativeQuery = true)
    Integer soSanPhamBanRaNamNay();


    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangthai = 1")
    Integer demSoHoaDonChoXacNhan();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangthai = 2")
    Integer demSoHoaDonXacNhan();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangthai = 3")
    Integer demSoHoaDonChoGiaoHang();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangthai = 4")
    Integer demSoHoaDonDangGiao();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangthai = 5")
    Integer demSoHoaDonThanhCong();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangthai = 6")
    Integer demSoHoaDonDaHuy();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangthai = 7")
    Integer demSoHoaDonTraHang();



    @Query(value = "SELECT " +
            "sp.imageDefaul AS tenimage, " +
            "sp.tensp AS tensp, " +
            "sp.giaban AS giaban, " +
            "SUM(hdct.soluong) AS soluong, " +
            "SUM(hdct.soluong * COALESCE(hdct.dongiakhigiam, hdct.dongia)) AS doanhthu " +
            "FROM " +
            "HoaDonChiTiet hdct " +
            "JOIN SanPhamChiTiet spct ON hdct.IdSPCT = spct.id " +
            "JOIN SanPham sp ON spct.IdSP = sp.id " +
            "JOIN HoaDon hd ON hdct.IdHD = hd.id " +
            "WHERE " +
            "hd.trangthai = 5 AND " +
            "hdct.trangthai = 1 " +
            "GROUP BY " +
            "sp.imageDefaul, sp.tensp, sp.giaban " +
            "ORDER BY " +
            "SUM(hdct.soluong) DESC", nativeQuery = true)
    List<Object[]> SanPhamBanChay();


}
