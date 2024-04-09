package com.example.be_duantn.dto.respon.authentication_respon.nhanvien;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NhanVienJwtTokenResponse {
    private String token;

}
