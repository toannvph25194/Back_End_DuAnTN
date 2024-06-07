package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.request.mua_hang_online_request.ImageTKRequest;
import com.example.be_duantn.dto.request.mua_hang_online_request.TTTaiKhoanRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.TTNguoiDungRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinTTNguoiDungRespon;

import java.util.UUID;

public interface ThongTinNguoiDungService {

    // finby thông tin người dùng lên trang checkout
    TTNguoiDungRespon findByKhachhang(UUID idkh);

    // finby thông tin người dùng lên trang tài khoản
    FinTTNguoiDungRespon finByTaiKhoanND(UUID idkh);

    // update thông tin người dùng trang tài khoản
    TTTaiKhoanRequest updateTaiKhoanND(TTTaiKhoanRequest khrequest);

    // upload hình ảnh  dùng
    ImageTKRequest uploadImageTK(ImageTKRequest upload);
}
