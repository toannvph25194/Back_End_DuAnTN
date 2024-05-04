package com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon;

import java.util.Date;
import java.util.UUID;

public interface SanPhamAdminRespon {

    UUID getId();

    String getmasp();

    String getimagedefaul();

    String gettensp();

    Long gettrangthai();

    Date getngaythem();

    Double getgiaban();

    Double getgianhap();

    Integer gettheloai();

    String getmotasp();

    String gettenchatlieu();

    String gettenxuatxu();

    String gettenthuonghieu();

    String gettendanhmuc();

    UUID getdanhmuc();

    UUID getchatlieu();

    UUID getthuonghieu();

    UUID getxuatxu();
}
