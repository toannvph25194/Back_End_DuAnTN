package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ThuongHieuRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.XuatXuRespon;
import com.example.be_duantn.entity.XuatXu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface XuatXuRepository extends JpaRepository<XuatXu, UUID> {
    //xuất ra các trạng thái xuất xứ là 1
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenXuatXu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[XuatXu]", nativeQuery = true)
    Page<XuatXuRespon> Getallxuatxu(Pageable pageable);

    @Query(value = "SELECT [Id]\n" +
            "      ,[TenXuatXu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[XuatXu]\n" +
            "  where id = ?", nativeQuery = true)
    Optional<XuatXuRespon> Getallxuatxutheoid(UUID id);

    //  xuất xứ load combobox
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenXuatXu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[XuatXu] where trangthai = 1", nativeQuery = true)
    List<XuatXuRespon> GetallxuatxuLoadCombobox();

}
