package com.example.be_duantn.dto.respon.ban_tai_quay_respon;

import java.util.Date;
import java.util.UUID;

public interface LoadHoaDonRespon {
    UUID getId();
    UUID getIdkh();
    String getmahoadon();
    String gethovatennv();
    String gettennguoinhan();
    Date getngaytao();
    Integer getloaihoadon();
    Integer gettrangthai();
}
