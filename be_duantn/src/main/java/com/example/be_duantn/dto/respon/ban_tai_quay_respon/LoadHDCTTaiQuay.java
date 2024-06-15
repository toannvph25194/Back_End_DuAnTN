package com.example.be_duantn.dto.respon.ban_tai_quay_respon;

import java.util.UUID;

public interface LoadHDCTTaiQuay {
    UUID getIdhdct();
    UUID getIdsp();
    String gettensp();
    String gettenmausac();
    String gettensize();
    String gettenchatlieu();
    String getimagedefaul();
    Integer getsoluongton();
    Integer getsoluong();
    Double getdongia();
    Double getdongiakhigiam();
}
