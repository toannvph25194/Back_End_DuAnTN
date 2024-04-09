package com.example.be_duantn.dto.request.authentication_request.khachhang;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class KhachHangLoginRequest {

    @NotBlank(message = "Không được để trống username")
    private String taikhoan;

    @NotBlank(message = "Không được để trống password")
    private String matkhau;
}
