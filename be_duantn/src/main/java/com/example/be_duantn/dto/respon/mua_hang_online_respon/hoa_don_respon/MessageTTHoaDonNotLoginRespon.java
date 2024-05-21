package com.example.be_duantn.dto.respon.mua_hang_online_respon.hoa_don_respon;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageTTHoaDonNotLoginRespon {
    String message;
    UUID idhoadon;
    UUID idkhdataoandkhmoi;
}
