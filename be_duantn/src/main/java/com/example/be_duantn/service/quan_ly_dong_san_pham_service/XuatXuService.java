package com.example.be_duantn.service.quan_ly_dong_san_pham_service;


import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.XuatXuRespon;
import com.example.be_duantn.entity.XuatXu;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface XuatXuService {

    // Hiển thị ra danh sách xuất xứ
    Page<XuatXuRespon> GetAllXuatXu(Integer page);

    // Findby xuất xứ theo id
    Optional<XuatXuRespon> FindByXuatXuID(UUID id);

    // Thêm xuất xứ
    XuatXu AddXuatXu(XuatXuRequest xuatXu);

    // Update xuất xứ theo id
    XuatXu UpdateXuatXu(XuatXuRequest xuatXuRequest);

    // Chuyển trạng thái xuất xứ
    XuatXu ChuyenTrangThai(UUID id, Integer trangThaiMoi);

    // Hiển thị danh sách xuất xứ load combobox
    List<XuatXuRespon> GetallXuatXuLoadCombobox();
}
