package com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon;

import jakarta.persistence.Column;

import java.util.UUID;

public interface XuatXuRespon {

    UUID getid();

    String gettenxuatxu();

    String getmota();

    Integer gettrangthai();
}
