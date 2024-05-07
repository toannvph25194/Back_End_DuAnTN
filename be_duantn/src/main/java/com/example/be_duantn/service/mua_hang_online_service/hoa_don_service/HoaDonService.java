package com.example.be_duantn.service.mua_hang_online_service.hoa_don_service;

import com.example.be_duantn.dto.request.mua_hang_online_request.hoa_don_request.ThongTinThanhToanRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.hoa_don_respon.MessageTTHoaDonLoginRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.hoa_don_respon.MessageTTHoaDonNotLoginRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;

import java.util.UUID;

public interface HoaDonService {

    // Tạo hóa đơn thanh toán login
    MessageTTHoaDonLoginRespon ThanhToanLogin(ThongTinThanhToanRequest ttttrequest, UUID idkh);

    //Tạo hóa đon thanh toán not login
    MessageTTHoaDonNotLoginRespon ThanhToanNotLogin(ThongTinThanhToanRequest thongTinThanhToanRequest);

    // Tạo mới hình thức thanh toán
    HinhThucThanhToan hinhThucTT(UUID idhoadon, Double sotientra, String magiaodinh);
}
