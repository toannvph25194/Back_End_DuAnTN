package com.example.be_duantn.dto.request.authentication_request.khachhang;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangRegisterRequest {
    @NotBlank(message = "Vui lòng điền username")
    @Size(min = 7, max = 13, message = "Độ dài username phải từ 7 đến 13 ký tự")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Chỉ cho phép chữ và số")
    private String taikhoan;

    @NotBlank(message = "Vui lòng điền password")
    private String matkhau;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Địa chỉ email không hợp lệ")
    private String email;

}
