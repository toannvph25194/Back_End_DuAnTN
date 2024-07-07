package com.example.be_duantn.service.quan_ly_hoa_don_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MessageRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HoaDonTrangThaiAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamAdminRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamChiTietManThemHoaDonRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.entity.HoaDon;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.UUID;

public interface HoaDonAdminService {
    Integer TongSoHoaDonChoXacNhan();

    Integer TongSoHoaDonXacNhan();

    Integer TongSoHoaDonChoGiao();

    Integer TongSoHoaDonDangGiao();

    Integer TongSoHoaDonHoanThanh();

    Integer TongSoHoaDonHuy();

    // Hiển thị hoá đơn
    Page<Hoadonrespon> HienThiHoaDonPhanTrang(Integer page);

    // Lọc theo trang thai
    Page<Hoadonrespon> LocHoaDonTheoTrangThai(Integer pageNumber, Integer pageSize, Integer trangthai);

    // Lọc theo ma
    Page<Hoadonrespon> TimKiemTheoMa(Integer pageNumber, Integer pageSize, String mahoadon);

    // Lọc theo loai hoa don
    Page<Hoadonrespon> LocHoaDonTheoLoaiHoaDon(Integer pageNumber, Integer pageSize, Integer loaihoadon);

    // hiển thị hoá đơn theo id
    Hoadonrespon finByIdHD(UUID IdHD);

    // update thông tin hoá đơn
    HoaDon updateThongTinHoaDon(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest,String taikhoan, Principal principal);

    // update Trạng thái hoá đơn
    HoaDon updateTrangThaiHoaDon(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest,String taikhoan, Principal principal);

    // update thông tin người giao
    HoaDon updateThongTinNguoiGiao(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest,String taikhoan, Principal principal);

    // update hoá đơn hoàn thành
    HoaDon updatehoanthanh(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest,String taikhoan, Principal principal);

    // update hoá đơn hoàn thành
    HoaDon updatehuyhoadon(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan, Principal principal);

    //cập nhật lại thành tiền và giá trị giảm
    HoaDon updateThanhTien(UUID IDHD);


}
