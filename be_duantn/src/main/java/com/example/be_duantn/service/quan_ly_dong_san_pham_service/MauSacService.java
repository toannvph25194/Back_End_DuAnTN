package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MauSacRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.MauSacRespon;
import com.example.be_duantn.entity.MauSac;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MauSacService {
    Page<MauSacRespon> getMauSac(Integer page);

    List<MauSac> getAllMauSac();

    Optional<MauSac> getMauSacById(UUID id);

    MauSac createMauSac(MauSacRequest mausacRequest);

    MauSac updateMauSac(UUID id, MauSacRequest mausacRequest);

    MauSac chuyenTrangThai(UUID id, Integer trangThaiMoi);

    // Hiển thị danh sách màu sắc load combobox
    List<MauSacRespon> getMauSacLoadComboBox();

}
