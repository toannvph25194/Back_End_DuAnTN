package com.example.be_duantn.repository.authentication_repository;

import com.example.be_duantn.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository

public interface NhanVienRepository extends JpaRepository<NhanVien, UUID>{
    Optional<NhanVien> findByTaikhoan(String taikhoan);
}
