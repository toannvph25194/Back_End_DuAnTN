package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinByGioHangRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHang;
import com.example.be_duantn.entity.GioHang;

import java.util.UUID;

public interface GioHangService {

    // Tạo giỏ hàng
    MessageGioHang TaoGioHang(GioHang gh, UUID idkh);

    // Tìm kiếm IdGH theo Idkh và trạng thái gh
    FinByGioHangRespon finByIdGioHang(UUID idkh);
}
