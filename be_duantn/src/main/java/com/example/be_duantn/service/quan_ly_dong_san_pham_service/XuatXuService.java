package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SizeRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.XuatXuRespon;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.Size;
import com.example.be_duantn.entity.XuatXu;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface XuatXuService {
    public Page<XuatXuRespon> getXuatXu(Integer page);

    // Thêm xuất xứ
    XuatXu addXuatXu(XuatXuRequest xuatxu);

    Optional<XuatXuRespon> getSizeById(UUID id);

    XuatXu updateXuatxu(UUID id, XuatXuRequest xuatXuRequest);

    void deleteXuatxu(UUID id);

    XuatXu chuyenTrangThai(UUID id, Integer trangThaiMoi);

    // Hiển thị danh sách xuất xứ load combobox
    List<XuatXuRespon> getXuatXuLoadComboBox();

}
