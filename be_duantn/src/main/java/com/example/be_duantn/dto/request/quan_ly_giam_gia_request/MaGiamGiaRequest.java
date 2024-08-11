package com.example.be_duantn.dto.request.quan_ly_giam_gia_request;

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
public class MaGiamGiaRequest {

    private UUID idgiamgia;

    private String magiamgia;

    private String tengiamgia;

    private Date ngaycapnhat;

    private Date ngaybatdau;

    private Date ngayketthuc;

    private Integer hinhthucgiam;

    private Integer giatrigiam;

    private String ghichu;

    private Integer trangthai;
}
