package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.entity.SanPham;

import java.util.List;

public interface DanhMucService {
    public List<DanhMucRespon> getDanhMuc();
    // Thêm danh mục
    DanhMuc addDanhMuc(DanhMucRequest danhmuc);

}
