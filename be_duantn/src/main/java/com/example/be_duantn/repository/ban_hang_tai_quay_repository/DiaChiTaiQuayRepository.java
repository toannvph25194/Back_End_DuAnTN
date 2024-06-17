package com.example.be_duantn.repository.ban_hang_tai_quay_repository;

import com.example.be_duantn.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;
public interface DiaChiTaiQuayRepository extends JpaRepository<DiaChi, UUID> {

}
