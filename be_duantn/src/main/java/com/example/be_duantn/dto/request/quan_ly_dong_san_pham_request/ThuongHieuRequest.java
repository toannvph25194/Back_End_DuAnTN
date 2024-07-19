package com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ThuongHieuRequest {


    private UUID id;

    @NotBlank(message = "Tên chất liệu không được để trống")
    private String tenthuonghieu;

    private String mota;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangthai;

}
