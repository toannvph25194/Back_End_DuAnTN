package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SizeRespon;
import com.example.be_duantn.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SizeRepository extends JpaRepository<Size, UUID> {
    // Hiển thị ra danh sách size
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenSize]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[Size]\n", nativeQuery = true)
    Page<SizeRespon> GetAllSize(Pageable pageable);

    // Findby size theo id
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenSize]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[Size] where id = ?", nativeQuery = true)
    Optional<SizeRespon> FindBySizeID(UUID id);

    //  Size load combobox
    @Query(value = "SELECT [Id]\n" +
            "      ,[TenSize]\n" +
            "      ,[MoTa]\n" +
            "      ,[TrangThai]\n" +
            "  FROM [dbo].[Size]\n where trangthai = 1", nativeQuery = true)
    List<SizeRespon> GetAllSizeLoadComboBox();
}
