package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.FinGioHangBanTaiQuay;

import java.util.UUID;

public interface GioHangBanTaiQuayService {

    // Tìm kiếm giỏ hàng tại quầy theo id khách hàng
    FinGioHangBanTaiQuay TimKiemGioHangTaiQuay(UUID idkh);
}
