package com.example.be_duantn.dto.request.authentication_request.nhanvien;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NhanVienLoginRequest {

    @NotBlank(message = "Không được để trống username")
    private String taikhoan;

    @NotBlank(message = "Không được để trống password")
    private String matkhau;
}
