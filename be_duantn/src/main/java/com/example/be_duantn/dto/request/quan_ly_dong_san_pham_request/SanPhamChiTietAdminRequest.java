package com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request;

import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
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
public class SanPhamChiTietAdminRequest {

    private UUID idspct;

    @NotNull(message = "Loại sản phẩm không được để trống")
    private Integer soluongton;

    private String mota;

    private String qrcode;

    @NotNull(message = "Trạng thái sản phẩm không được để trống")
    private Integer trangthai;

    private Date ngaytao = new Date();

    @NotNull(message = "Xuất xứ không được để trống")
    private UUID sanpham;

    @NotNull(message = "Xuất xứ không được để trống")
    private UUID mausac;

    @NotNull(message = "Xuất xứ không được để trống")
    private UUID size;
}
