package com.example.be_duantn.dto.respon.ban_tai_quay_respon;

import java.util.Date;
import java.util.UUID;

public interface HinhThucThanhToanBanTaiQuayRespon {

    UUID getid();

    String getmagiaodich();

    Date getngaythanhtoan();

    Double getsotientra();

    Date getngaytao();

    Date getngaycapnhat();

    String getghichu();

    String gettaikhoan();

    Integer gettrangthai();

    Integer gethinhthucthanhtoan();
}
