package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ImageRespon;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface ImageRepository extends JpaRepository<Image, UUID> {
    //xuất ra các trạng thái danh muc là 1
    @Query(value = "SELECT [Id]\n" +
            "      ,[IdSP]\n" +
            "      ,[TenImage]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[Image]\n" +
            "  where IdSP = ? and TrangThai = 1", nativeQuery = true)
    List<ImageRespon> Getallimage(UUID idsp);
}
