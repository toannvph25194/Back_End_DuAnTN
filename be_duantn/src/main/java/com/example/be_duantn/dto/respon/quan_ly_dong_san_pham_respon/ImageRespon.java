package com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public interface ImageRespon {

    UUID getid();

    String gettenimage();

    String getmota();

    Integer gettrangthai();
}
