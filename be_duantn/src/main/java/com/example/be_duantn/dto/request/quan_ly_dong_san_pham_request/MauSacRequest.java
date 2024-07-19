package com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MauSacRequest {
    private UUID id;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String tenmausac;

    private String mota;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangthai;
}
