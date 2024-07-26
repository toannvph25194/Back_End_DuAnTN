package com.example.be_duantn.dto.request.authentication_request.nhanvien;


//import fpt.edu.duantn_th.dto.enums.TypeAccountEnum;
import com.example.be_duantn.enums.QuyenEnum;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienRegisterRequest {
    @NotBlank(message = "Vui lòng điền username")
    @Size(min = 7, max = 13, message = "Độ dài username phải từ 7 đến 13 ký tự")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Chỉ cho phép chữ và số")
    private String taikhoan;

    @NotBlank(message = "Vui lòng điền password")
    private String matkhau;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Địa chỉ email không hợp lệ")
    private String email;

    @NotNull(message = "Không được để trống quyền hạn")
    private QuyenEnum chucvu;
//    private TypeAccountEnum role;

    private String manv;
    private String hovatennv;
    private Boolean gioitinh;
    @Temporal(TemporalType.DATE)
    private Date ngaysinh;
    private String sodienthoai;
    private String image;
    private String mota;
    private String diachi;
    private Integer trangthai;
    // Getter và Setter
    public Integer getTrangthai() {
        return trangthai;
    }
    public void setTrangthai(Integer trangthai) {
        this.trangthai = trangthai;
    }
}
