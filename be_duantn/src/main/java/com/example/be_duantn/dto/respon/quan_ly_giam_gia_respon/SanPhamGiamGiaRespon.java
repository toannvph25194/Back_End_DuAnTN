package com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon;

import java.util.Date;
import java.util.UUID;

public interface SanPhamGiamGiaRespon {

    UUID getid();

    String getmasp();

    String gettensp();

    String getimagedefaul();

    Double getdongiakhigiam();

    Double getgiaban();

    Date getngaythemgiamgia();

    String getmagiamgia();

    String gettengiamgia();


}
