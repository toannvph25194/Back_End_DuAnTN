package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHDCTTaiQuay;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageHDCTBanTaiQuay;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface HoaDonCTBanTaiQuayService {
    // load hóa đơn chi tiết tại quầy theo idhd của khách
    List<LoadHDCTTaiQuay> loadHDCTBanTaiQuay(UUID idhd);

    // Thêm sản phẩm vào hóa đơn chi tiết cho khách bán tại quầy
    MessageHDCTBanTaiQuay ThemSPVaoHDCTBanTaiQuay(Principal principal, UUID idhd, UUID idspct, int soluong, Double dongiakhigiam);

    // update soluong hdct theo idhdct
    MessageHDCTBanTaiQuay UpdateHDCTBanTaiQuay(Principal principal, UUID idhdct, Integer soluong);

    // delete hdct theo idhdct
    MessageHDCTBanTaiQuay DeleteHDCTBanTaiQuay(Principal principal, UUID idhdct);
}
