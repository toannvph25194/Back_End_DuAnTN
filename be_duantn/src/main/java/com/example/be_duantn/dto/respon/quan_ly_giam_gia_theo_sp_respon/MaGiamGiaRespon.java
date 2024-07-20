package com.example.be_duantn.dto.respon.quan_ly_giam_gia_theo_sp;

import java.util.Date;
import java.util.UUID;

public interface MaGiamGiaRespon {

    UUID getidgiamgia();


    String getmagiamgia();


    String gettengiamgia();


    Date getngaytao();


    Date getngaycapnhat();


    Date getngaybatdau();


    Date getngayketthuc();


    Integer gethinhthucgiam();


    String getghichu();


    Double gettiengiamgia();


    Integer gettrangthai();
}
