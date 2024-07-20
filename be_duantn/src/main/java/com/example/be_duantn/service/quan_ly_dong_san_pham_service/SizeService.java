package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SizeRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SizeRespon;
import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.entity.Size;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SizeService {
    public Page<SizeRespon> getSize(Integer page);

    Optional<Size> getSizeById(UUID id);

    Size createSize(SizeRequest sizeRequest);

    Size updateSize(UUID id, SizeRequest sizeRequest);

    void deleteSize(UUID id);

    Size chuyenTrangThai(UUID id, Integer trangThaiMoi);

    // Hiển thị danh sách size load combobox
    List<SizeRespon> getSizeLoadComboBox();
}
