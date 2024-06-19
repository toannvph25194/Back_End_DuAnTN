package com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.UUID;

public interface NhanVienAdminRespon {

    UUID getidnv();


    String getmanv();


    String gethovatennv();


    Boolean getgioitinh();


    String getngaysinh();


    String gettaikhoan();


    String getmatkhau();

    String getsodienthoai();


    String getemail();


    String getimage();

    String mota();


    String getdiachi();


    Integer gettrangthai();
}
