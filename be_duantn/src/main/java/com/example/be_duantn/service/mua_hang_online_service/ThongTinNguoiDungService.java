package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.TTNguoiDungRespon;

import java.util.UUID;

public interface ThongTinNguoiDungService {

    // finby thông tin người dùng theo idkh
    TTNguoiDungRespon findByKhachhang(UUID idkh);
}
