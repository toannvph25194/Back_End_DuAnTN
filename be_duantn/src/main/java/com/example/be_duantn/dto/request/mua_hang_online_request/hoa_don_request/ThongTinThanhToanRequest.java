package com.example.be_duantn.dto.request.mua_hang_online_request.hoa_don_request;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThongTinThanhToanRequest {

    private String hovatenkh;

    private String sodienthoai;

    private String email;

    private String diachichitiet;

    private String phuongxa;

    private String quanhuyen;

    private String tinhthanh;

    private Double thanhtien;

    private Double tienkhachtra;

    private Integer phuongthucthanhtoan;

    private UUID idvoucher;

    private Double giatrigiam;

    private List<UUID> giohangchitietlist;
}
