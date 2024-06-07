package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinTTNguoiDungRespon;
import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ThongTinNguoiDungRepository extends JpaRepository<KhachHang, UUID> {

    // finby thông tin người dùng lên trang tài khoản
    @Query(value = "SELECT kh.TaiKhoan, kh.HoVaTenKH, kh.Email, kh.SoDienThoai, kh.NgaySinh, kh.GioiTinh, kh.Image FROM KhachHang kh\n" +
            "Where Id = ?",nativeQuery = true)
    FinTTNguoiDungRespon finByTaiKhoanND(UUID idkh);
}
