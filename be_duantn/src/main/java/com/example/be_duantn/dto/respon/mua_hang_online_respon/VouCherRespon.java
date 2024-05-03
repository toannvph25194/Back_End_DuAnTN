package com.example.be_duantn.dto.respon.mua_hang_online_respon;

import java.util.UUID;

public interface VouCherRespon {
    UUID getId();
    String getMavoucher();
    String getTenvoucher();
    String getNgayketthuc();
    String getSoluongma();
    String getSoluongdung();
    String getDieukientoithieuhoadon();
    String getGiatrigiam();
    Integer getHinhthucgiam();
}
