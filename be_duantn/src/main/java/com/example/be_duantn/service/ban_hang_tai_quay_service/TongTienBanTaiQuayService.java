package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.FinGioHangBanTaiQuay;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.TongTienBanTaiQuayRespon;

import java.util.UUID;

public interface TongTienBanTaiQuayService {

    TongTienBanTaiQuayRespon tongtien(UUID id);
}
