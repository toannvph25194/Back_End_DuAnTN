package com.example.be_duantn.dto.request.ban_hang_tai_quay_request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KhachHangDiaChiRequest {

    private UUID idkh;
    private String hovatenkh;
    private String taikhoan;
    private UUID iddiachi;
    private String diachichitiet;
    private String phuongxa;
    private String quanhuyen;
    private String tinhthanh;
    private String quocgia;
    private Integer trangthai;
    private String sodienthoai;
    private String email;
    private String matkhau;
    private Integer trangthaiDiaChi;
}
