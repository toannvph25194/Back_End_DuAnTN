package com.example.be_duantn.service.authentication_service.khach_hang_service;

import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangLoginRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangMessageResponse;
import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangRegisterRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangLoginResponse;

import java.util.UUID;

public interface KhachHangService {

    // login khách hàng
    KhachHangLoginResponse login(KhachHangLoginRequest khachhangLoginRequest);

    // register khách hàng
    KhachHangMessageResponse register(KhachHangRegisterRequest khachhangRegisterRequest);

    // senMail khi đăng kí thành công tk kh
    KhachHangMessageResponse sendConfirmEmailForgotPassWord(String email, UUID id);

    // senMail khi quên mk
    String confirmationCodeForgotPassWord();

    // forgotPassword khách hàng
    KhachHangMessageResponse forgotPassword(String email);
}
