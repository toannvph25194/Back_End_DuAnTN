package com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamRequest {

    private UUID idsp;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String masp;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 250, message = "Tên sản phẩm không được vượt quá 250 ký tự")
    private String tensp;

    @NotNull(message = "Loại sản phẩm không được để trống")
    private Integer theloai;

    private String motasp;

    @NotNull(message = "Trạng thái sản phẩm không được để trống")
    private Integer trangthai;

    private Date ngaythemsp = new Date();

    private Double gianhap;

    @NotNull(message = "Giá bán không được để trống")
    @Positive(message = "Giá nhập phải là số dương")
    private Double giaban;

    @NotNull(message = "Danh mục không được để trống")
    private UUID danhmuc;

    @NotNull(message = "Chất liệu không được để trống")
    private UUID chatlieu;

    @NotNull(message = "Thương hiệu không được để trống")
    private UUID thuonghieu;

    @NotNull(message = "Xuất xứ không được để trống")
    private UUID xuatxu;

    @NotNull(message = "imagedefaul không được để trống")
    private String imagedefaul;

}
