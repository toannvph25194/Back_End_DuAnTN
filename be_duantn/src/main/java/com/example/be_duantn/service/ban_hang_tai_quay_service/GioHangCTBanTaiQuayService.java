package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHDCTTaiQuay;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageHDCTBanTaiQuay;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface GioHangCTBanTaiQuayService {
    // load giỏ hàng chi tiết tại quầy theo idgh của khác
    List<LoadHDCTTaiQuay> loadGHCTBanTaiQuay(UUID idgh);

    // Thêm sản phẩm vào giỏ hàng chi tiết bán tại quầy
    MessageHDCTBanTaiQuay ThemSPVaoGHCTBanTaiQuay(Principal principal, UUID idgh, UUID idspct, int soluong, Double dongiakhigiam);

    // update soluong ghct theo idghct
    MessageHDCTBanTaiQuay UpdateGioHangCTBanTaiQuay(Principal principal, UUID idghct, Integer soluong);

    // delete ghct theo idghct
    MessageHDCTBanTaiQuay DeleteGioHangCTBanTaiQuay(Principal principal, UUID idghct);
}
