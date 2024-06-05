package com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon;

import com.example.be_duantn.entity.HoaDon;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

public interface HoaDonChiTietRespon {

    UUID getid();

    Integer getsoluong();

    Double getdongia();

    Double getdongiakhigiam();

    Date getngaytao();

    String getghichu();

    String gettensp();

    Integer gettrangthai();

}
