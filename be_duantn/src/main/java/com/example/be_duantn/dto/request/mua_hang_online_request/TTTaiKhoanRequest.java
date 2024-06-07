package com.example.be_duantn.dto.request.mua_hang_online_request;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TTTaiKhoanRequest {
    private UUID idkh;
    private String hovatenkh;
    private String email;
    private String sodienthoai;
    private Date ngaysinh;
    private Boolean gioitinh;
    private String image;
}
