package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DanhMucRepository extends JpaRepository<DanhMuc, UUID> {
    //xuất ra các trạng thái danh muc là 1
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenDanhMuc]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[DanhMuc]", nativeQuery = true)
    Page<DanhMucRespon> Getalldanhmuc(Pageable pageable);

    @Query(value = "SELECT [Id]\n" +
            "      ,[TenDanhMuc]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[DanhMuc] where id = ?", nativeQuery = true)
    Optional<DanhMucRespon> Getalldanhmuctheoid(UUID id);
}
