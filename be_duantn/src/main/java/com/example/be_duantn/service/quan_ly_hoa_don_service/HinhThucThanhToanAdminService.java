package com.example.be_duantn.service.quan_ly_hoa_don_service;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HoaDonTrangThaiAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.NhanVienAdminRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.NhanVien;

import java.util.List;
import java.util.UUID;

public interface HinhThucThanhToanAdminService {

    // hiển thị hoá đơn theo id
  public List <HinhThucThanhToanRespon> finByIdHTTT(UUID IdHD);

    // update hình thức thanh toán
    HinhThucThanhToan updateHTTT(UUID IDHD, HinhThucThanhToanAdminRequest hinhThucThanhToanAdminRequest);

    // thêm mới hình thức thanh toán hoàn tiền khi huỷ
    HinhThucThanhToan AddHTTTMoi( UUID idhd,HinhThucThanhToanAdminRequest hinhThucThanhToanAdminRequest);

    // thêm mới hình thức thanh toán hoàn tiền hoặc thu thêm khi hoàn thành
    HinhThucThanhToan AddHTTTMoiKhiHoanThanh( UUID idhd,HinhThucThanhToanAdminRequest hinhThucThanhToanAdminRequest);

  // update hình thức thanh toán
  List<HinhThucThanhToan> updateNguoiXacNhan(UUID IDHD, UUID idnhanvien);

  public List <NhanVienAdminRespon> loadtatcanhanvienTT1();


}
