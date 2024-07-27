package com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon;

import java.util.Date;
import java.util.UUID;

public interface KhachHangRespon {
    UUID getId();
    String getmakh();
    String getimage();
    String gethovatenkh();
    Boolean getgioitinh();
    String getdiachichitiet();
    String getphuongxa();
    String getquanhuyen();
    String gettinhthanh();
    String getemail();
    String getsodienthoai();
    Date getngaysinh();
    Integer gettrangthai();
}
