package com.example.be_duantn.service.authentication_service.khach_hang_service_impl;

import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangLoginRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangMessageResponse;
import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangRegisterRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangResponse;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.repository.authentication_repository.KhachHangRepository;
import com.example.be_duantn.service.authentication_service.khach_hang_service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khachhangRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public KhachHangResponse login(KhachHangLoginRequest khachhangLoginRequest) {
        Optional<KhachHang> otkh = khachhangRepository.findByTaikhoan(khachhangLoginRequest.getTaikhoan());
        if (otkh.isPresent()) {

            return KhachHangResponse.builder()

                    .idtk(otkh.get().getIdkh())
                    .username(otkh.get().getTaikhoan())
                    .message("Login thành công")
                    .build();
        } else {
            return KhachHangResponse.builder()
                    .message("Sai username hoặc password")
                    .build();
        }
    }

    @Override
    public KhachHangMessageResponse register(KhachHangRegisterRequest khachhangRegisterRequest) {
        Optional<KhachHang> otkh = khachhangRepository.findByTaikhoan(khachhangRegisterRequest.getTaikhoan());

        if (otkh.isPresent()) {
            return KhachHangMessageResponse.builder().message("Tài khoản đã tồn tại").build();
        }
        KhachHang kh = KhachHang.builder()
                .idkh(UUID.randomUUID())
                .taikhoan(khachhangRegisterRequest.getTaikhoan())
                .matkhau(passwordEncoder.encode(khachhangRegisterRequest.getMatkhau()))
                .email(khachhangRegisterRequest.getEmail())
                .build();

        try {
            KhachHang saveKH = khachhangRepository.save(kh);
            return KhachHangMessageResponse.builder().message("Registered thành công").build();
        } catch (Exception e) {
            return KhachHangMessageResponse.builder().message("Lỗi trong quá trình đăng ký").build();
        }
    }
}
