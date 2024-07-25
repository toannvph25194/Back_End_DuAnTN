package com.example.be_duantn.repository.quan_ly_nguoi_dung_repository;

import com.example.be_duantn.entity.KhachHang;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuanLyKhachHangRepository extends JpaRepository<KhachHang, UUID> {

    // Load khách hàng phân trang

}
