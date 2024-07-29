package com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon;

import java.util.Date;
import java.util.UUID;

public interface NhanVienRespon {
    UUID getId();
    String getmanv();
    String gethovatennv();
    Boolean getgioitinh();
    Date getngaysinh();
    String getsodienthoai();
    String getemail();
    String getimage();
    String getdiachi();
    String getmota();
    Integer gettrangthai();
}
