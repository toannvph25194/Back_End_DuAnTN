package com.example.be_duantn.dto.respon.authentication_respon.nhanvien;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NhanVienTokenResponse {

    private String accessToken;

    private String token;

    private String message;

    private String role;

    private UUID idtk;

    private String username;
}
