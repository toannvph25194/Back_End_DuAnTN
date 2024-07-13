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
    @Query(value = "SELECT \n" +
            "    dbo.HinhThucThanhToan.Id, \n" +
            "    dbo.HinhThucThanhToan.IdHD, \n" +
            "    dbo.HinhThucThanhToan.IdKH, \n" +
            "    dbo.HinhThucThanhToan.IdNV, \n" +
            "    dbo.HinhThucThanhToan.MaGiaoDich, \n" +
            "    dbo.HinhThucThanhToan.NgayThanhToan, \n" +
            "    dbo.HinhThucThanhToan.SoTienTra, \n" +
            "    dbo.HinhThucThanhToan.NgayTao, \n" +
            "    dbo.HinhThucThanhToan.NgayCapNhat, \n" +
            "    dbo.HinhThucThanhToan.GhiChu, \n" +
            "    dbo.HinhThucThanhToan.TrangThai, \n" +
            "    dbo.HinhThucThanhToan.HinhThucThanhToan, \n" +
            "    dbo.NhanVien.Id AS Expr1, \n" +
            "    dbo.NhanVien.IdCV, \n" +
            "    dbo.NhanVien.MaNV, \n" +
            "    dbo.NhanVien.HoVaTenNV, \n" +
            "    dbo.NhanVien.GioiTinh, \n" +
            "    dbo.NhanVien.NgaySinh, \n" +
            "    dbo.NhanVien.TaiKhoan, \n" +
            "    dbo.NhanVien.MatKhau, \n" +
            "    dbo.NhanVien.SoDienThoai, \n" +
            "    dbo.NhanVien.Email, \n" +
            "    dbo.NhanVien.Image, \n" +
            "    dbo.NhanVien.MoTa, \n" +
            "    dbo.NhanVien.TrangThai AS Expr2, \n" +
            "    dbo.NhanVien.DiaChi\n" +
            "FROM \n" +
            "    dbo.HinhThucThanhToan \n" +
            "LEFT JOIN \n" +
            "    dbo.NhanVien ON dbo.HinhThucThanhToan.IdNV = dbo.NhanVien.Id\n" +
            "WHERE \n" +
            "    dbo.HinhThucThanhToan.IdHD = ?\n", nativeQuery = true)
    List<HinhThucThanhToanRespon> finByidHTTT(UUID idHD);

    List<HinhThucThanhToan> findHinhThucThanhToanByHoadonIdhoadon(UUID idhd);




}
