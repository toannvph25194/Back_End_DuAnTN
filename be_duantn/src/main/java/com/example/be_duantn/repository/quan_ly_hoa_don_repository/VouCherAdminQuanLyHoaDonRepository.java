package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.entity.VouCher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VouCherAdminQuanLyHoaDonRepository extends JpaRepository<VouCher, UUID> {
    List<VouCher> findByTrangthai(Integer trangthai);

}
