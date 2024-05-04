package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.MauSacRespon;

import java.util.List;

public interface MauSacService {
    public List<MauSacRespon> getMauSac();
}
