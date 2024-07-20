package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ThuongHieuRespon;
import com.example.be_duantn.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, UUID> {
    // Hiển thị ra danh sách thương hiệu
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenThuongHieu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[ThuongHieu]\n", nativeQuery = true)
    Page<ThuongHieuRespon> GetAllThuongHieu(Pageable pageable);


    // Findby thương hiệu theo id
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenThuongHieu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[ThuongHieu]\n" +
            "  where [id]= ?\n", nativeQuery = true)
    Optional<ThuongHieuRespon> FindByThuongHieuID(UUID id);

    //  thương hiệu load combobox
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenThuongHieu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[ThuongHieu]\n where trangthai = 1", nativeQuery = true)
    List<ThuongHieuRespon> GetAllThuongHieuLoadComboBox();
}
