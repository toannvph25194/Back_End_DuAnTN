package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonTaiQuayRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;

import java.security.Principal;
import java.util.List;

public interface HoaDonBanTaiQuayService {

    // load thông tin hóa đơn bán tại quầy
    List<LoadHoaDonRespon> LoadHoaDonTaiQuay();

    // Tạo hóa đơn bán hàng tại quầy
    HoaDonTaiQuayRequest TaoHoaDonTaiQuay(Principal principal);
}