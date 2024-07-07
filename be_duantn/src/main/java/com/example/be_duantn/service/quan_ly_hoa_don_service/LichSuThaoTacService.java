package com.example.be_duantn.service.quan_ly_hoa_don_service;

import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.entity.LichSuTaoTac;

import java.util.List;
import java.util.UUID;

public interface LichSuThaoTacService {
    // hiển thị hoá đơn theo id
    public List<LichSuTaoTac> finByIdLSTT(UUID IdHD);
}
