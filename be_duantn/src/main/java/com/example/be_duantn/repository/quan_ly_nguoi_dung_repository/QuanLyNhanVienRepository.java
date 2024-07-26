package com.example.be_duantn.repository.quan_ly_nguoi_dung_repository;

import com.example.be_duantn.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface QuanLyNhanVienRepository extends JpaRepository<NhanVien, UUID> {

    Optional<NhanVien> findByTaikhoan(String taikhoan);
    boolean existsByEmail(String email);
    boolean existsBySodienthoai(String sodienthoai);

    @Query("SELECT nv FROM NhanVien nv WHERE " +
            "(:search IS NULL OR nv.hovatennv LIKE %:search% OR nv.sodienthoai LIKE %:search% OR nv.email LIKE %:search%) AND " +
            "(:trangthai IS NULL OR nv.trangthai = :trangthai)")
    Page<NhanVien> searchNhanVien(@Param("search") String search,
                                  @Param("trangthai") Integer trangthai,
                                  Pageable pageable);


}

