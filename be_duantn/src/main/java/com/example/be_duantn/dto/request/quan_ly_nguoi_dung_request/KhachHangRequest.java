package com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request;

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
public class KhachHangRequest {
    private UUID id;
    private String image;
    private String hovatenkh;
    private Boolean gioitinh;
    private String email;
    private String sodienthoai;
    private Date ngaysinh;
    private String mota;
    private String taikhoan;
    private String matkhau;
    private Integer trangthai;
    private String diachichitiet;
    private String phuongxa;
    private String quanhuyen;
    private String tinhthanh;
}
