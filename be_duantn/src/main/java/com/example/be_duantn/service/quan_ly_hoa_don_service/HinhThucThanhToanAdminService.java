package com.example.be_duantn.service.quan_ly_hoa_don_service;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HoaDonTrangThaiAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;

import java.util.UUID;

public interface HinhThucThanhToanAdminService {
    // hiển thị hoá đơn theo id
    HinhThucThanhToanRespon finByIdHTTT(UUID IdHD);

    // update hình thức thanh toán
    HinhThucThanhToan updateHTTT(UUID IDHD, HinhThucThanhToanAdminRequest hinhThucThanhToanAdminRequest);
}
