package com.example.be_duantn.controller.authentication_controller;

import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienLoginRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienMessageResponse;
import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienRegisterRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienTokenResponse;
import com.example.be_duantn.entity.RefeshToken;
import com.example.be_duantn.jwt.nhan_vien_jwt.NhanVienJwtService;
import com.example.be_duantn.jwt.nhan_vien_jwt.NhanVienCustomDetails;
import com.example.be_duantn.service.authentication_service.nhan_vien_service_impl.NhanVienServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/auth/nhanvien/")
public class NhanVienControler {

    @Autowired
    private NhanVienServiceImpl userService;

    @Autowired
    private NhanVienJwtService nhanVienJwtService;

    @PostMapping("dangnhapnhanvien")
    public ResponseEntity<NhanVienTokenResponse> login(@Valid @RequestBody NhanVienLoginRequest nhanVienLoginRequest) {
        return new ResponseEntity<>(userService.login(nhanVienLoginRequest), HttpStatus.OK);
    }

    @PostMapping("dangkynhanvien")
    public ResponseEntity<NhanVienMessageResponse> register(@Valid @RequestBody NhanVienRegisterRequest nhanVienRegisterRequest) {
        return new ResponseEntity<>(userService.register(nhanVienRegisterRequest), HttpStatus.CREATED);
    }

    // TODO: phương thức có nhiệm vụ cập nhật token.
    @PostMapping("refresh-token")
    public ResponseEntity<NhanVienTokenResponse> refreshToken(@RequestBody RefeshToken refreshToken) {
        try {
            // tìm user thông qua token
            NhanVienTokenResponse response = userService.findByToken(refreshToken.getToken())
                    .map(userService::verifyExpiration) // nếu tìm thấy user sẽ gọi hàm very...
                    .map(RefeshToken::getNhanvien) // lấy ra thông tin phật tử
                    .map(phatTu -> {
                        String accessToken = nhanVienJwtService.generateToken(new NhanVienCustomDetails(phatTu));
                        return NhanVienTokenResponse.builder()
                                .accessToken(accessToken)
                                .token(refreshToken.getToken())
                                .build();
                    }).orElseThrow(() -> new RuntimeException(
                            "Refresh token is not in database!"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
