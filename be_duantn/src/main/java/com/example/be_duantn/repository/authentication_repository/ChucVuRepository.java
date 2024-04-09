package com.example.be_duantn.repository.authentication_repository;

import com.example.be_duantn.entity.ChucVu;
import com.example.be_duantn.enums.QuyenEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChucVuRepository extends JpaRepository<ChucVu,Long> {

    Optional<ChucVu> findByTenchucvu(QuyenEnum name);

}
