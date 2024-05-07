package com.example.be_duantn.repository.mua_hang_oneline_repository.hoa_don_repository;

import com.example.be_duantn.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiaChiThanhToanRepository extends JpaRepository<DiaChi, UUID> {
}
