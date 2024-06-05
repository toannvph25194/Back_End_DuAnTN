package com.example.be_duantn.dto.request.quan_ly_hoa_don_request;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HinhThucThanhToanAdminRequest {


    private UUID id;


    private String magiaodich;


    private Date ngaythanhtoan;

    private Double sotientra;

    private Date ngaytao;

    private Date ngaycapnhat;

    private String ghichu;

    private Integer trangthai;

    private Integer hinhthucthanhtoan;
}
