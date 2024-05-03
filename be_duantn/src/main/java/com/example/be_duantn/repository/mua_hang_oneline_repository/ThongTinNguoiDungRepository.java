package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ThongTinNguoiDungRepository extends JpaRepository<KhachHang, UUID> {
}
