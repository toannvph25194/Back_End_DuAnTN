package com.example.be_duantn.service.authentication_service.nhan_vien_service_impl;

import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienLoginRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienMessageResponse;
import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienRegisterRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienTokenResponse;
import com.example.be_duantn.entity.ChucVu;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.entity.RefeshToken;
import com.example.be_duantn.jwt.nhan_vien_jwt.NhanVienJwtService;
import com.example.be_duantn.jwt.nhan_vien_jwt.NhanVienCustomDetails;
import com.example.be_duantn.repository.authentication_repository.NhanVienRepository;
import com.example.be_duantn.service.authentication_service.nhan_vien_service.NhanVienService;
import com.example.be_duantn.repository.authentication_repository.ChucVuRepository;
import com.example.be_duantn.repository.authentication_repository.RefreshTokenReponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienService {
    private final ChucVuRepository loaiTaiKhoanRepository;

    private final NhanVienRepository taiKhoanRepository;

    private final PasswordEncoder passwordEncoder;

    private final NhanVienJwtService nhanVienJwtService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenReponsitory refreshTokenRepository;

    @Override
    public NhanVienTokenResponse login(NhanVienLoginRequest nhanVienLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        nhanVienLoginRequest.getTaikhoan(),
                        nhanVienLoginRequest.getMatkhau()
                )
        );
        // Kiểm tra xem tài khoản đã tồn tại hay không
        Optional<NhanVien> optionalPhatTu = taiKhoanRepository.findByTaikhoan(nhanVienLoginRequest.getTaikhoan());
        if (optionalPhatTu.isPresent()) {
            // Tài khoản tồn tại, tạo mới hoặc cập nhật token
            RefeshToken refreshToken = createToken(nhanVienLoginRequest.getTaikhoan());
            String jwtToken = nhanVienJwtService.generateToken(new NhanVienCustomDetails(optionalPhatTu.get()));

            return NhanVienTokenResponse.builder()

                    .accessToken(jwtToken)
                    .token(refreshToken.getToken())
                    .role(optionalPhatTu.get().getChucvu().getTenchucvu().name())
                    .idtk(optionalPhatTu.get().getId())
                    .username(optionalPhatTu.get().getTaikhoan())
                    .message("Login thành công")
                    .build();
        } else {
            return NhanVienTokenResponse.builder()
                    .message("Sai username hoặc password")
                    .build();
        }
    }

    @Override
    public NhanVienMessageResponse register(NhanVienRegisterRequest nhanVienRegisterRequest) {
        Optional<NhanVien> optionalPhatTu = taiKhoanRepository.findByTaikhoan(nhanVienRegisterRequest.getTaikhoan());

        if (optionalPhatTu.isPresent()) {
            return NhanVienMessageResponse.builder().message("Tài khoản đã tồn tại").build();
        }

        Optional<ChucVu> quyenHan = loaiTaiKhoanRepository.findByTenchucvu(nhanVienRegisterRequest.getChucvu());

        if (quyenHan.isEmpty()) {
            return NhanVienMessageResponse.builder().message("Quyền hạn không hợp lệ").build();
        }

        NhanVien user = NhanVien.builder()
                .id(UUID.randomUUID())
                .taikhoan(nhanVienRegisterRequest.getTaikhoan())
                .matkhau(passwordEncoder.encode(nhanVienRegisterRequest.getMatkhau()))
                .email(nhanVienRegisterRequest.getEmail())
                .chucvu(quyenHan.get())
                .build();

        try {
            NhanVien savedUser = taiKhoanRepository.save(user);
            String jwtToken = nhanVienJwtService.generateToken(new NhanVienCustomDetails(savedUser));
            return NhanVienMessageResponse.builder().message("Registered Successfully").build();
        } catch (Exception e) {
            return NhanVienMessageResponse.builder().message("Lỗi trong quá trình đăng ký").build();
        }
    }

    @Override
    public RefeshToken createToken(String username) {
        RefeshToken refreshToken = RefeshToken
                .builder()
                .nhanvien(taiKhoanRepository.findByTaikhoan(username).get())
                .token(UUID.randomUUID().toString())
                .thoigianhethan(LocalDate.from(LocalDateTime.now().plusMinutes(10)))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override// TODO kiểm tra xem token hết hạn chưa
    public RefeshToken verifyExpiration(RefeshToken token) {
        // trả về thời gian hết hạn của token < Thời gian hiện tại tức token hết hạn
        if (token.getThoigianhethan().compareTo(ChronoLocalDate.from(Instant.now())) < 0) {
            refreshTokenRepository.delete(token); // Xóa token
            throw new RuntimeException(token.getToken() +
                    " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public Optional<RefeshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
