package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;

import java.util.List;
import java.util.UUID;

public interface ThongTinHoaDonSevice {

    // Load thông tin hóa đơn của khách hàng
    List<TTHoaDonRespon> LoadTTHoaDonKhachHang(UUID idkh, Integer trangthai);

    // Load thông tin sản phẩm trong hóa đơn của khách hàng
    List<TTSPHoaDonRespon> LoadTTSPHoaDonKhachHang(UUID idhoadon);

    // Tìm kiếm thông tin hóa đơn của khách theo mahoadon, tennguoinhan, tensanpham
    List<TTHoaDonRespon> TimKiemHoaDonKhachHang(UUID idkh, String keyword, Integer trangthai);

    // Find thông tin hóa đơn chi tiết
    TTHoaDonCTRespon FinTTHoaDonCTKhachHang(UUID idhoadon);

    // Find hình thức thanh toán hóa đơn của khách hàng
    List<TTHinhThucTTRespon> FinTTHinhThucTT(UUID idhoadon);

    // Find thông tin sản phẩm trong hóa đơn chi tiết
    List<TTSPHoaDonRespon> FinTTSPHoaDonCTKhachHang(UUID idhoadon);

    // Find thông tin lịch sử các ngày của hóa đơn
    TTLichSuHDRespon FindLichSuNgayHD(UUID idhoadon);

    // Xử lý khách hàng hủy đơn hàng
    MessageHuyDonHangRespon HuyDonHangKhachHang(UUID idhoadon);
}
