package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

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
    // Hiển thị ra danh sách danh mục
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenDanhMuc]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[DanhMuc]", nativeQuery = true)
    Page<DanhMucRespon> GetAllDanhMuc(Pageable pageable);

    // Findby danh mục theo id
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenDanhMuc]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[DanhMuc] where id = ?", nativeQuery = true)
    Optional<DanhMucRespon> FindByDanhMucID(UUID id);

    // Danh mục load combobox
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenDanhMuc]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[DanhMuc] where trangthai = 1", nativeQuery = true)
    List<DanhMucRespon> GetAllDanhMucLoadComboBox();
}
