package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ThuongHieuRespon;
import com.example.be_duantn.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, UUID> {
    //xuất ra các trạng thái thương hiệu là 1
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenThuongHieu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[ThuongHieu]\n" +
            "  where TrangThai = 1", nativeQuery = true)
    List<ThuongHieuRespon> Getallthuonghieu();
}
