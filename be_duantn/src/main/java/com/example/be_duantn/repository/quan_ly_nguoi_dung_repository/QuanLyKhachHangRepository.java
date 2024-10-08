package com.example.be_duantn.repository.quan_ly_nguoi_dung_repository;

import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.KhachHangRespon;
import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface QuanLyKhachHangRepository extends JpaRepository<KhachHang, UUID> {

    // Load khách hàng phân trang
    @Query(value = "SELECT COUNT(DISTINCT kh.Id), kh.Id, kh.MaKH, kh.Image, kh.HoVaTenKH, kh.GioiTinh, dc.DiaChiChiTiet, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh,\n" +
            "\t   kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.MoTa, kh.TrangThai\n" +
            "FROM KhachHang kh\n" +
            "FULL JOIN DiaChi dc on dc.IdKH = kh.Id\n" +
            "WHERE kh.TrangThai IN (0,1) AND (dc.TrangThai = 1 OR dc.TrangThai IS NULL) \n" +
            "GROUP BY kh.Id, kh.MaKH, kh.Image, kh.HoVaTenKH, kh.GioiTinh, dc.DiaChiChiTiet, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh,  kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.MoTa, kh.TrangThai\n", nativeQuery = true)
    Page<KhachHangRespon> LoadAllKhachHang(Pageable pageable);

    // Find by khách hàng theo id
    @Query(value = "SELECT DISTINCT kh.Id, kh.Id, kh.MaKH, kh.Image, kh.HoVaTenKH, kh.GioiTinh, dc.DiaChiChiTiet, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh,\n" +
            "             kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.MoTa, kh.TrangThai\n" +
            "            FROM KhachHang kh\n" +
            "            FULL JOIN DiaChi dc on dc.IdKH = kh.Id\n" +
            "            WHERE kh.TrangThai IN (0,1) AND (dc.TrangThai = 1 OR dc.TrangThai IS NULL) \n" +
            "            AND kh.Id = ?\n" +
            "            GROUP BY kh.Id, kh.MaKH, kh.Image, kh.HoVaTenKH, kh.GioiTinh, dc.DiaChiChiTiet, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh,  kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.MoTa, kh.TrangThai\n", nativeQuery = true)
    KhachHangRespon FindByKhachHang(UUID idkh);

    // Lọc khách hàng theo tên, sdt, email
    @Query(value = "SELECT COUNT(DISTINCT kh.Id), kh.Id, kh.MaKH, kh.Image, kh.HoVaTenKH, kh.GioiTinh, dc.DiaChiChiTiet, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh,\n" +
            "            kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.MoTa, kh.TrangThai\n" +
            "            FROM KhachHang kh\n" +
            "            FULL JOIN DiaChi dc on dc.IdKH = kh.Id\n" +
            "            WHERE kh.TrangThai IN (0,1) AND (dc.TrangThai = 1 OR dc.TrangThai IS NULL)\n" +
            "            AND (kh.HoVaTenKH LIKE %:keyword% OR kh.SoDienThoai LIKE %:keyword% OR kh.Email LIKE %:keyword%)\n" +
            "            GROUP BY kh.Id, kh.MaKH, kh.Image, kh.HoVaTenKH, kh.GioiTinh, dc.DiaChiChiTiet, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh,  kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.MoTa, kh.TrangThai", nativeQuery = true)
    Page<KhachHangRespon> LocKhachHangTheoNhieuTieuChi(Pageable pageable, @Param("keyword") String keyword);

    // Lọc khách hàng theo trạng thái
    @Query(value = "SELECT COUNT(DISTINCT kh.Id), kh.Id, kh.MaKH, kh.Image, kh.HoVaTenKH, kh.GioiTinh, dc.DiaChiChiTiet, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh,\n" +
            "            kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.MoTa, kh.TrangThai\n" +
            "            FROM KhachHang kh\n" +
            "            FULL JOIN DiaChi dc on dc.IdKH = kh.Id\n" +
            "            WHERE (dc.TrangThai = 1 OR dc.TrangThai IS NULL) AND kh.TrangThai = ? \n" +
            "            GROUP BY kh.Id, kh.MaKH, kh.Image, kh.HoVaTenKH, kh.GioiTinh, dc.DiaChiChiTiet, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh,  kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.MoTa, kh.TrangThai", nativeQuery = true)
    Page<KhachHangRespon> LocKhachHangTheoTrangThai(Pageable pageable, Integer trangthai);

}