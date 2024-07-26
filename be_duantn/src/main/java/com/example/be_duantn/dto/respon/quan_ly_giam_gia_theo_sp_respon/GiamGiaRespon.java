package com.example.be_duantn.dto.respon.quan_ly_giam_gia_theo_sp_respon;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

public interface GiamGiaRespon {

    UUID getid();

    String getmagiamgia();

    String gettengiamgia();

    Date getngaytao();

    Date getngaycapnhat();

    Date getngaybatdau();

    Date getngayketthuc();

    Integer gethinhthucgiam();

    Integer getgiatrigiam();

    String getghichu();

    Integer gettrangthai();
}
