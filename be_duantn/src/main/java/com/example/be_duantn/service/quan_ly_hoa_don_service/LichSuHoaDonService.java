package com.example.be_duantn.service.quan_ly_hoa_don_service;

import com.example.be_duantn.entity.LichSuHoaDon;

import java.util.List;
import java.util.UUID;

public interface LichSuHoaDonService {
    // hiển thị hoá đơn theo id
    public List<LichSuHoaDon> finByIdLSTT(UUID IdHD);


}
