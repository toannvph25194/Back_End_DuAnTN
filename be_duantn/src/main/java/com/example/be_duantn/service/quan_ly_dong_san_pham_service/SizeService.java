package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SizeRespon;
import com.example.be_duantn.entity.Size;

import java.util.List;

public interface SizeService {
    public List<SizeRespon> getSize();
}
