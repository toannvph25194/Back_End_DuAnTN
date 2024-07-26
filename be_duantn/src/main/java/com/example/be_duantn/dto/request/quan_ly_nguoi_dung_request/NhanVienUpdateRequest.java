package com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request;

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
public class NhanVienUpdateRequest {

    private UUID id;
    private String manv;
    private String email;
    private String hovatennv;
    private Boolean gioitinh;
    private Date ngaysinh;
    private String sodienthoai;
    private String image;
    private String mota;
    private String diachi;
    private Integer trangthai;
}
