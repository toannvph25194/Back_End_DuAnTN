package com.example.be_duantn.dto.respon.mua_hang_online_respon;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageGioHang {
    UUID Idgh;
    String message;
}
