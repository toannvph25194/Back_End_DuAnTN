package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonTaiQuayRequest;
import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonXacNhanRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LayTienKhachTraRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageHuyHoaDon;
import com.example.be_duantn.entity.HoaDon;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface HoaDonBanTaiQuayService {

    // load thông tin hóa đơn bán tại quầy
    List<LoadHoaDonRespon> LoadHoaDonTaiQuay();

    // Tạo hóa đơn bán hàng tại quầy
    HoaDonTaiQuayRequest TaoHoaDonTaiQuay(Principal principal);

    // Hủy hóa đơn bán hàng tại quầy
    MessageHuyHoaDon HuyHoaDonTaiQuay(UUID idhoadon, Principal principal);

    // Tìm kiếm hóa đơn bán tại quầy
    List<LoadHoaDonRespon> TimKiemHoaDonTaiQuay(String mahoadon);

    //Hùng làm Update khách hàng ở hóa đơn
    int updateKhachHang(UUID idhoadon, UUID idkh, String hovatenkh);

    //lấy tiền khách đã trả
    LayTienKhachTraRespon laytienkhachtra(UUID id);

    // update hoá đơn hoàn thành
    HoaDon updatehoanthanh(UUID IDHD, UUID Idgg, Double TienCuoiCung, Double TienDuocGiam);

    // update hoá đơn xác nhận
    HoaDon updateXacNhan(UUID IDHD, UUID Idgg, Double TienCuoiCung, Double TienDuocGiam, HoaDonXacNhanRequest hoaDonXacNhanRequest);

    //lấy tiền khách đã trả
    HoaDon layhoadontheoid(UUID id);
}