package com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

public interface HinhThucThanhToanRespon {

    UUID getid();

    String getmagiaodich();

    Date getngaythanhtoan();

    Double getsotientra();

    Date getngaytao();

    Date getngaycapnhat();

    String getghichu();

    String gettaikhoan();

    Integer gethinhthucthanhtoan();

    Integer gettrangthai();
}
