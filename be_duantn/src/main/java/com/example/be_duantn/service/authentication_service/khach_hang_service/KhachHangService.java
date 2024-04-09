package com.example.be_duantn.service.authentication_service.khach_hang_service;

import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangLoginRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangMessageResponse;
import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangRegisterRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangResponse;

public interface KhachHangService {

    KhachHangResponse login(KhachHangLoginRequest khachhangLoginRequest);

    KhachHangMessageResponse register(KhachHangRegisterRequest khachhangRegisterRequest);

}
