package com.example.be_duantn.dto.respon.ban_tai_quay_respon;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

public interface LoadDiaChiTaiQuayRespon {

    UUID getIdkh();
    String getHovatenkh();
    String getTaikhoan();
    UUID getIddiachi();
    String getDiachichitiet();
    String getPhuongxa();
    String getQuanhuyen();
    String getTinhthanh();
    String getQuocgia();
    Integer getTrangthai();
    String getSodienthoai();
    String getEmail();

}
