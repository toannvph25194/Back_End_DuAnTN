package com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

public interface Hoadonrespon {

    UUID getid();

    String getmahoadon();

    Date getngaytao();

    Date getngaynhanhang();

    String gettennguoinhan();

    String getsdtnguoinhan();

    Double getthanhtien();

    Integer getloaihoadon();

    Date getngaythanhtoan();

    Date getngaycapnhat();

    Date getngaygiaohang();

    String getdonvigiaohang();

    String gettennguoigiao();

    String getsdtnguoigiao();

    Double gettiengiaohang();

    String getemailnguoinhan();

    String getdiachinhanhang();

    Double getgiatrigiam();

    Double gettienkhachtra();

    Double gettienthua();

    String getghichu();

    Date getngayxacnhan();

    Date getngaychogiaohang();

    Date getngayhuy();


    Integer gettrangthai();

    Double getgiaTrigiamvouCher();

    Integer gethinhthucgiam();

    String getmavoucher();
}
