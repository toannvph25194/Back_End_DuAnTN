package com.example.be_duantn.service.quan_ly_nguoi_dung_service;

import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.KhachHangRequest;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.KhachHangRespon;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.MessageKhachHangRespon;
import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface QuanLyKhachHangService {

    // Load khách hàng phân trang
    Page<KhachHangRespon> LoadAllKhachHang(Integer page);

    // Thêm mới khách hàng
    MessageKhachHangRespon ThemMoiKhachHang(KhachHangRequest request);

    // Update khách hàng
    MessageKhachHangRespon UpdateKhachHang(KhachHangRequest updatedRequest);

    // Update trạng thái khách hàng
    MessageKhachHangRespon UpdateTrangThaiKhachHang(UUID id, Integer trangThai);

    // Lọc khách hàng theo tên, sdt, email
    Page<KhachHangRespon> LocKhachHangTheoNhieuTieuChi(Integer page, String hovatenkh, String sodienthoai, String email);

    // Lọc khách hàng theo trạng thái
    Page<KhachHangRespon> LocKhachHangTheoTrangThai(Integer page, Integer trangthai);
}
