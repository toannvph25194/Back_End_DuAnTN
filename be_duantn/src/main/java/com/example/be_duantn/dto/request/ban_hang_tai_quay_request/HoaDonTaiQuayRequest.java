package com.example.be_duantn.dto.request.ban_hang_tai_quay_request;

import lombok.*;

import javax.swing.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HoaDonTaiQuayRequest {
    private UUID idhoadon;
    private String mahoadon;
    private UUID idkh;
    private String message;
    private String hovatenkh;
    private Integer trangthai;
}
