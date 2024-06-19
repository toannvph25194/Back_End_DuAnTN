package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.HinhThucThanhToanBanTaiQuayRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.NhanVienAdminRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;

import java.util.List;
import java.util.UUID;

public interface HinhThucThanhToanAdminBanTaiQuayService {

    // hiển thị hoá đơn theo id
    public List<HinhThucThanhToanBanTaiQuayRespon> finByIdHTTT(UUID IdHD);

    //tạo httt tiền mặt
    HinhThucThanhToan AddHTTTMoiKhiThanhToanTienMat(UUID idhd,  Double TienCuoiCung, Double TienKhachDua,String taikhoan);

    //tạo httt chuyển khoản
    HinhThucThanhToan AddHTTTMoiKhiThanhToanChuyenKhoan(UUID idhd,  Double TienCuoiCung,String taikhoan, String magiaodich);



}
