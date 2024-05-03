package com.example.be_duantn.dto.respon.mua_hang_online_respon;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TTNguoiDungRespon {
    private String taikhoan;
    private String hovatenkh;
    private String sodienthoai;
    private String email;
}
