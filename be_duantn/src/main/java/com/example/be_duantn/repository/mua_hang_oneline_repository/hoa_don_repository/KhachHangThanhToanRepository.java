package com.example.be_duantn.repository.mua_hang_oneline_repository.hoa_don_repository;

import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KhachHangThanhToanRepository extends JpaRepository<KhachHang, UUID> {

    // lấy thông tin email và sodienthoai của khách hàng
    @Query(value = "SELECT * FROM KhachHang where Email = ? AND sodienthoai = ?",nativeQuery = true)
    List<KhachHang> getKhachHangByEmailAndSdt(String email, String sodienthoai);
}
