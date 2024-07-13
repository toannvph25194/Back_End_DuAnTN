package com.example.be_duantn.dto.respon.mua_hang_online_respon;

import java.util.Date;
import java.util.UUID;

public interface TTHoaDonRespon {
    UUID getId();
    String getmahoadon();
    String gettennguoinhan();
    Date getngaytao();
    Double getthanhtien();
    Integer gettrangthai();
}
