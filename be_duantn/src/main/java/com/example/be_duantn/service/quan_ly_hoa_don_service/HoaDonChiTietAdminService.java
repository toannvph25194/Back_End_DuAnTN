package com.example.be_duantn.service.quan_ly_hoa_don_service;

import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;

import java.util.UUID;

public interface HoaDonChiTietAdminService {
    // hiển thị hoá đơn theo id
    HoaDonChiTietRespon finByIdHDCT(UUID IdHD);
}
