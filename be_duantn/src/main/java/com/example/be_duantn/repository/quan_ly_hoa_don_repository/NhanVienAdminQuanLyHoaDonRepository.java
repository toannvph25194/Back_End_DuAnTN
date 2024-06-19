package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.NhanVienAdminRespon;
import com.example.be_duantn.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NhanVienAdminQuanLyHoaDonRepository extends JpaRepository<NhanVien, UUID> {
    List<NhanVienAdminRespon> findByTrangthai(Integer trangthai);
    Optional<NhanVien> findByTaikhoan(String taikhoan);


}
