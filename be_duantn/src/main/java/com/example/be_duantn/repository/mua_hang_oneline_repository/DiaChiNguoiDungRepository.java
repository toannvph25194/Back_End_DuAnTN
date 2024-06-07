package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.DiaChiNguoiDungRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinByDCNguoiDungRespon;
import com.example.be_duantn.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DiaChiNguoiDungRepository extends JpaRepository<DiaChi, UUID> {

    // finby địa chỉ người dùng lên trang checkout
    @Query(value = "SELECT dc.id, dc.DiaChiChiTiet, dc.TinhThanh, dc.QuanHuyen, dc.PhuongXa\n" +
            "FROM DiaChi dc JOIN KhachHang kh ON dc.IdKH = kh.Id  \n" +
            "Where dc.TrangThai = 1 AND kh.Id = ?", nativeQuery = true)
    List<DiaChiNguoiDungRespon> finByDiaChiKhachHang(UUID idkh);

    // finby địa chỉ người dùng lên trang tài khoản
    @Query(value = "SELECT kh.HoVaTenKH,SoDienThoai, dc.Id, dc.PhuongXa, dc.QuanHuyen, dc.TinhThanh, dc.DiaChiChiTiet, dc.TrangThai FROM DiaChi dc \n" +
            "JOIN KhachHang kh on kh.Id = dc.IdKH\n" +
            "Where kh.Id = ?", nativeQuery = true)
    List<FinByDCNguoiDungRespon> finByDiaChiTaiKhoan(UUID idkh);

    List<DiaChi> findByTrangthaiAndKhachhangTaikhoan(int trangThai, String taikhoan);
}
