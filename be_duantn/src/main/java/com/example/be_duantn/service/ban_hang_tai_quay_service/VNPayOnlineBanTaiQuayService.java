package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.hoa_don_respon.VNPayOnlineRespon;
import jakarta.servlet.http.HttpServletRequest;

public interface VNPayOnlineBanTaiQuayService {

    // Thanh Toán VNPay Online
    VNPayOnlineRespon callPaymentApiOnline(HttpServletRequest req, Long amountParam);

}
