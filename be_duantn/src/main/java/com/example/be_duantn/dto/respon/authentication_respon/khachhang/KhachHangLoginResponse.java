package com.example.be_duantn.dto.respon.authentication_respon.khachhang;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KhachHangLoginResponse {

    private String message;

    private UUID idtk;

    private String username;
}
