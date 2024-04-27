package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinByGioHangRespon;
import com.example.be_duantn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GioHangRepository extends JpaRepository<GioHang, UUID> {

    // Tìm kiếm kh đã có giỏ hàng bằng 1
    @Query(value = "SELECT gh.Id, kh.Id, gh.GhiChu, gh.TrangThai  FROM GioHang gh\n" +
            "INNER JOIN KhachHang kh on kh.Id = gh.IdKH\n" +
            "WHERE gh.IdKH = ? AND gh.TrangThai = 1", nativeQuery = true)
    List<FinByGioHangRespon> finByKhAndTrangThai(UUID Idkh, Integer trangthai);

    // Tìm kiếm IdGH theo Idkh và trạng thái gh
    @Query(value = "SELECT gh.Id ,gh.TrangThai FROM GioHang gh\n" +
            "INNER JOIN KhachHang kh on kh.Id = gh.IdKH\n" +
            "WHERE kh.Id = ? AND gh.TrangThai = 1", nativeQuery = true)
    FinByGioHangRespon finByIdGioHang(UUID idkh);


}
