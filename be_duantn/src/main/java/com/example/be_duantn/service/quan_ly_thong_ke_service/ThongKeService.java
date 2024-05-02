package com.example.be_duantn.service.quan_ly_thong_ke_service;

import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.dto.respon.thong_ke_respon.DoanhThuResponse;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ThongKeService {
    List<DoanhThuResponse> doanhThu(Date ngayBd, Date ngayKt);

    BigDecimal tongTienDonHomNay();

    BigDecimal tongTienDonTuanNay();

    BigDecimal tongTienDonThangNay();

    BigDecimal tongTienDonNamNay();

    List<HoaDon> getAllHoaDon();

    Integer soDonHomNay();

    Integer soDonTuanNay();

    Integer soDonThangNay();

    Integer soDonNamNay();

    Integer soDonHuyHomNay();

    Integer soDonHuyTuanNay();

    Integer soDonHuyThangNay();

    Integer soDonHuyNamNay();

    Integer soSanPhamBanRaHomNay();

    Integer soSanPhamBanRaTuanNay();

    Integer soSanPhamBanRaThangNay();

    Integer soSanPhamBanNamNay();

    Integer demSoHoaDonChoXacNhan();

    Integer demSoHoaDonXacNhan();

    Integer demSoHoaDonDangGiao();

    Integer demSoHoaDonChoGiaoHang();

    Integer demSoHoaDonThanhCong();

    Integer demSoHoaDonDaHuy();

    Integer demSoHoaDonTraHang();

   // List<TopSanPham> listSanPhamBanChay();

    List<Object[]> SanPhamBanChay();
}
