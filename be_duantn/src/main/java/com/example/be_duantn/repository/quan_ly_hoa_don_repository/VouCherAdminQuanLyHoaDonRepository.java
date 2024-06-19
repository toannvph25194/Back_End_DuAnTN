package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.VouCherRespon;
import com.example.be_duantn.entity.VouCher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VouCherAdminQuanLyHoaDonRepository extends JpaRepository<VouCher, UUID> {


    List<VouCher> findByTrangthai(Integer trangthai);


}
