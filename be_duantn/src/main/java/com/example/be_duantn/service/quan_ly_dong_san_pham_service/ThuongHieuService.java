package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ThuongHieuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ThuongHieuRespon;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.ThuongHieu;

import java.util.List;

public interface ThuongHieuService {
    public List<ThuongHieuRespon> getThuongHIeu();
    // Thêm thương hiệu
    ThuongHieu addThuongHieu(ThuongHieuRequest thuonghieu);

}
