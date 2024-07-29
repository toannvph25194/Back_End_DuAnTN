package com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request;

import com.example.be_duantn.entity.ChucVu;
import com.example.be_duantn.enums.QuyenEnum;
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
public class NhanVienRequest {

    private UUID id;
    private String manv;
    private String email;
    private String hovatennv;
    private Boolean gioitinh;
    private String taikhoan;
    private String matkhau;
    private Date ngaysinh;
    private String sodienthoai;
    private String image;
    private String mota;
    private String diachi;
    private Integer trangthai;
    private QuyenEnum chucvu;
}
