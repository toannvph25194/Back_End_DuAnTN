package com.example.be_duantn.repository.quan_ly_nguoi_dung_repository;

import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.NhanVienRespon;
import com.example.be_duantn.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface QuanLyNhanVienRepository extends JpaRepository<NhanVien, UUID> {

    // Kiểm tra tài khoản đã tồn tại chưa
    Optional<NhanVien> findByTaikhoan(String taikhoan);

    // Check tồn tại email
    boolean existsByEmail(String email);

    // Check tồn tại số điện thoại
    boolean existsBySodienthoai(String sodienthoai);

    // Load all nhân viên phân trang
    @Query(value = "SELECT COUNT(nv.Id), nv.Id, nv.MaNV, HoVaTenNV, GioiTinh, NgaySinh, SoDienThoai, Email, Image, DiaChi, MoTa, TrangThai FROM NhanVien nv\n" +
            "WHERE TrangThai IN(0,1)\n" +
            "GROUP BY nv.Id, nv.MaNV, HoVaTenNV, GioiTinh, NgaySinh, SoDienThoai, Email, Image, DiaChi, MoTa, TrangThai", nativeQuery = true)
    Page<NhanVienRespon> LoadAllNhanVien(Pageable pageable);

    // Find by nhân viên theo id
    @Query(value = "SELECT COUNT(nv.Id), nv.Id, nv.MaNV, HoVaTenNV, GioiTinh, NgaySinh, SoDienThoai, Email, Image, DiaChi, MoTa, TrangThai FROM NhanVien nv\n" +
            "WHERE TrangThai IN(0,1) AND nv.Id = ?\n" +
            "GROUP BY nv.Id, nv.MaNV, HoVaTenNV, GioiTinh, NgaySinh, SoDienThoai, Email, Image, DiaChi, MoTa, TrangThai", nativeQuery = true)
    NhanVienRespon FindByNhanVien(UUID idnv);

    // Lọc nhân viên theo tiêu chí
    @Query(value = "SELECT COUNT(nv.Id), nv.Id, nv.MaNV, HoVaTenNV, GioiTinh, NgaySinh, SoDienThoai, Email, Image, DiaChi, MoTa, TrangThai FROM NhanVien nv \n" +
            "WHERE TrangThai IN(0,1) AND (nv.HoVaTenNV LIKE %:search% OR nv.Email LIKE %:search% OR nv.SoDienThoai LIKE %:search%)\n" +
            "GROUP BY nv.Id, nv.MaNV, HoVaTenNV, GioiTinh, NgaySinh, SoDienThoai, Email, Image, DiaChi, MoTa, TrangThai", nativeQuery = true)
    Page<NhanVienRespon> LocNhanVienTieuChi(Pageable pageable, @Param("search") String search);

    // Lọc nhân viên theo trạng thái
    @Query(value = "SELECT COUNT(nv.Id), nv.Id, nv.MaNV, HoVaTenNV, GioiTinh, NgaySinh, SoDienThoai, Email, Image, DiaChi, MoTa, TrangThai FROM NhanVien nv \n" +
            "WHERE TrangThai IN(0,1) AND nv.TrangThai = ?\n" +
            "GROUP BY nv.Id, nv.MaNV, HoVaTenNV, GioiTinh, NgaySinh, SoDienThoai, Email, Image, DiaChi, MoTa, TrangThai", nativeQuery = true)
    Page<NhanVienRespon> LocNhanVienTrangThai(Pageable pageable, Integer trangthai);
}

