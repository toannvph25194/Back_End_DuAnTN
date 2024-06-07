package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.request.mua_hang_online_request.DiaChiTaiKhoanRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.DiaChiNguoiDungRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinByDCNguoiDungRespon;
import com.example.be_duantn.entity.DiaChi;

import java.util.List;
import java.util.UUID;

public interface DiaChiNguoiDungService {

    // finby địa chỉ người dùng lên trang checkout
    List<DiaChiNguoiDungRespon> finByDiaChiKhachHang(UUID idkh);

    // finby địa chỉ người dùng lên trang tài khoản
    List<FinByDCNguoiDungRespon> finByDiaChiTaiKhoan(UUID idkh);

    // add địa chỉ người dùng trang tài khoản
    DiaChi ThemDiaChiTaiKhoan(DiaChiTaiKhoanRequest dcrequest);

    // update địa chỉ người dùng trang tài khoản
    DiaChi UpdateDiaChiTaiKhoan(DiaChiTaiKhoanRequest dcrequest);

    // update trạng thái địa chỉ người dùng trang tài khoản
    DiaChi UpdateTrangThaiDC(UUID iddc, String taikhoan);
}
