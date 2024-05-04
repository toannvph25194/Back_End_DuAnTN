package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.entity.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChatLieuRepository extends JpaRepository<ChatLieu, UUID> {
    //xuất ra các trạng thái chất lieu là 1
    @Query(value = "\n" +
            "SELECT [Id]\n" +
            "      ,[TenChatLieu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[ChatLieu] where TrangThai = 1\n", nativeQuery = true)
    List<ChatLieuRespon> GetAllChatlieu();
}
