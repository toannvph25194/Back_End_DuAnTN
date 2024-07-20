package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MauSacRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.MauSacRespon;
import com.example.be_duantn.entity.MauSac;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MauSacService {

    // Hiển thị ra danh sách màu sắc
    Page<MauSacRespon> GetAllMauSac(Integer page);

    // Findby màu sắc theo id
    Optional<MauSacRespon> FindByMauSacID(UUID id);

    // Thêm màu sắc
    MauSac AddMauSac(MauSacRequest mauSacRequest);

    // Update màu sắc theo id
    MauSac UpdateMauSac(MauSacRequest mauSacRequest);

    // Chuyển trạng thái màu sắc
    MauSac ChuyenTrangThai(UUID id, Integer trangThaiMoi);

    // Hiển thị danh sách màu sắc load combobox
    List<MauSacRespon> GetAllMauSacLoadComboBox();
}
