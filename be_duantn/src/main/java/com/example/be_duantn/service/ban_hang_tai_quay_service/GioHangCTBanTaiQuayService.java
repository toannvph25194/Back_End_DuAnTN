package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadGHCTTaiQuay;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageGHCTBanTaiQuay;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface GioHangCTBanTaiQuayService {
    // load giỏ hàng chi tiết tại quầy theo idgh của khác
    List<LoadGHCTTaiQuay> loadGHCTBanTaiQuay(UUID idgh);

    // Thêm sản phẩm vào giỏ hàng chi tiết bán tại quầy
    MessageGHCTBanTaiQuay ThemSPVaoGHCTBanTaiQuay(Principal principal, UUID idgh, UUID idspct, int soluong, Double dongiakhigiam);

    // update soluong ghct theo idghct
    MessageGHCTBanTaiQuay UpdateGioHangCTBanTaiQuay(Principal principal, UUID idghct, Integer soluong);

    // delete ghct theo idghct
    MessageGHCTBanTaiQuay DeleteGioHangCTBanTaiQuay(Principal principal, UUID idghct);
}
