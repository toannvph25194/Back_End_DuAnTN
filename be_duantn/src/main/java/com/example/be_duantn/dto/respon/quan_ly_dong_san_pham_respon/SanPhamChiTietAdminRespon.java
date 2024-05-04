package com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon;

import java.util.Date;
import java.util.UUID;

public interface SanPhamChiTietAdminRespon {

    UUID getid();

    Integer getsoluongton();

    String getmota();

    String getqrcode();

    Integer gettrangthai();

    Date getngaytao();

    String gettensize();

    String gettenmausac();
}
