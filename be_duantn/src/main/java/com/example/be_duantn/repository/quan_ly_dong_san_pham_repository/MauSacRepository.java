package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;


import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.MauSacRespon;
import com.example.be_duantn.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MauSacRepository extends JpaRepository<MauSac, UUID> {
    // Hiển thị ra danh sách màu sắc
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenMauSac]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[MauSac]\n", nativeQuery = true)
    Page<MauSacRespon> GetAllMauSac(Pageable pageable);

    // Findby màu sắc theo id
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenMauSac]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[MauSac]  where id = ? \n", nativeQuery = true)
    Optional<MauSacRespon> FindByMauSacID(UUID id);

    //  Màu sắc load combobox
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenMauSac]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[MauSac]\n where trangthai = 1", nativeQuery = true)
    List<MauSacRespon> GetAllMauSacLoadComboBox();

}
