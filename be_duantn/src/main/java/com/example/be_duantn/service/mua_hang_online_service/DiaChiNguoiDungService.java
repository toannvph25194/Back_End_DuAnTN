package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.DiaChiNguoiDungRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.TTNguoiDungRespon;
import com.example.be_duantn.entity.KhachHang;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaChiNguoiDungService {

    // finby thông tin người dùng theo idkh
    List<DiaChiNguoiDungRespon> finByDiaChiNguoiDung(UUID idkh);
}
