package com.example.be_duantn.dto.request.ban_hang_tai_quay_request;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoaDonXacNhanRequest {

    private UUID idhoadon;

    private Double tiengiaohang;

    private String tennguoinhan;

    private String sdtnguoinhan;

    private String emailnguoinhan;

    private String diachinhanhang;

}
