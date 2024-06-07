package com.example.be_duantn.dto.request.mua_hang_online_request;

import com.example.be_duantn.entity.KhachHang;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DiaChiTaiKhoanRequest {
    private UUID iddiachi;
    private UUID idkh;
    private String diachichitiet;
    private String phuongxa;
    private String quanhuyen;
    private String tinhthanh;
    private String quocgia;
    private String ghichu;
    private Integer trangthai;
}

