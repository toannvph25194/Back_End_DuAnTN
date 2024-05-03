package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHangCTRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.GioHangChiTietRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.TongSoTienRespon;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.UUID;

public interface GioHangChiTietService {

    // load ghct theo id giỏ hàng
    List<GioHangChiTietRespon> loadGioHangChiTiet(UUID idgh);

    // add sản phầm vào ghct
    MessageGioHangCTRespon addSPGioHangCT(UUID idgh, UUID idspct, Integer soluong, Double dongiakhigiam);

    // update soluong ghct theo idghct
    MessageGioHangCTRespon updateGioHangCT(UUID idghct, Integer soluong);

    // delete ghct theo idghct
    MessageGioHangCTRespon deleteGioHangCT(UUID idghct);

    // delete all ghct theo idgh
    MessageGioHangCTRespon deleteAllGioHangCT(UUID idgh);

    // load tổng tất cả số tiền của sp theo igh
    TongSoTienRespon loadTongSoTienSP(UUID idgh);
}
