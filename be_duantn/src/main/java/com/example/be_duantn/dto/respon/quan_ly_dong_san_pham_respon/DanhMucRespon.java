package com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon;

import jakarta.persistence.Column;

import java.util.UUID;

public interface DanhMucRespon {

    UUID getid();

    String gettendanhmuc();

    String getmota();

    Integer gettrangthai();

}
