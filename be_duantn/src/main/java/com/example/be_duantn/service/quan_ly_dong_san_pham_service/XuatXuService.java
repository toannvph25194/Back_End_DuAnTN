package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.XuatXuRespon;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.XuatXu;

import java.util.List;

public interface XuatXuService {
    public List<XuatXuRespon> getXuatXu();
    // Thêm xuất xứ
    XuatXu addXuatXu(XuatXuRequest xuatxu);

}
