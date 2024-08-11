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
public class VouCherAdminRequest {

    private UUID idvoucher;

    private String mavoucher;

    private String tenvoucher;

    private Date ngaybatdau;

    private Date ngaycapnhat;

    private Date ngayketthuc;

    private Integer soluongma;

    private Integer soluongdung;

    private Double giatrigiam;

    private Double dieukientoithieuhoadon;

    private Integer hinhthucgiam;

    private Integer loaivoucher;

    private String ghichu;

    private Integer trangthai;
}
