package com.example.be_duantn.service.authentication_service.nhan_vien_service;

import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienLoginRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienMessageResponse;
import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienRegisterRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienTokenResponse;
import com.example.be_duantn.entity.RefeshToken;

import java.util.Optional;

public interface NhanVienService {

    NhanVienTokenResponse login(NhanVienLoginRequest nhanVienLoginRequest);

    NhanVienMessageResponse register(NhanVienRegisterRequest nhanVienRegisterRequest);

    RefeshToken createToken(String username);

    RefeshToken verifyExpiration(RefeshToken token);

    Optional<RefeshToken> findByToken(String token);
}
