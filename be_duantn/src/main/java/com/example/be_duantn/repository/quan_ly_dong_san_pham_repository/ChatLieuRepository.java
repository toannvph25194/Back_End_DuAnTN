package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.entity.ChatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatLieuRepository extends JpaRepository<ChatLieu, UUID> {
    // Hiển thị danh sách chất liệu
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenChatLieu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[ChatLieu]", nativeQuery = true)
    Page<ChatLieuRespon> GetAllChatlieu(Pageable pageable);

    // Findby chất liệu theo Id
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenChatLieu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[ChatLieu] where id = ?", nativeQuery = true)
    Optional<ChatLieuRespon> FindByChatLieuID(UUID id);

    //  chất liệu load combobox
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenChatLieu]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[ChatLieu] where trangthai = 1", nativeQuery = true)
    List<ChatLieuRespon> GetAllChatLieuLoadComboBox();

}
