package com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon;

import java.util.Date;
import java.util.UUID;


public interface VouCherAdminRespon {
    UUID getid();

    String getmavoucher();

    String gettenvoucher();

    Date getngaybatdau();

    Date getngaycapnhat();

    Date getngaytao();

    Date getngayketthuc();

    Integer getsoluongma();

    Integer getsoluongdung();

    Double getgiatrigiam();

    Double getdieukientoithieuhoadon();

    Integer gethinhthucgiam();

    Integer getloaivoucher();

    String getghichu();

    Integer gettrangthai();
}
