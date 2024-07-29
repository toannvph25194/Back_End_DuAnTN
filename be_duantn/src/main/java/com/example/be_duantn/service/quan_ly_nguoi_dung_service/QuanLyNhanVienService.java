package com.example.be_duantn.service.quan_ly_nguoi_dung_service;

import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.NhanVienRequest;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.MessageNhanVienRespon;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.NhanVienRespon;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface QuanLyNhanVienService {

    boolean emailExists(String email);

    boolean phoneExists(String phone);

    // Load all nhân viên phân trang
    Page<NhanVienRespon> LoadAllNhanVien(Integer page);

    // Find by nhân viên theo id
    NhanVienRespon FindNhanVienById(UUID id);

    // Lọc nhân viên theo tiêu chí
    Page<NhanVienRespon> LocNhanVienTieuChi(Integer page, String search);

    // Lọc nhân viên theo trạng thái
    Page<NhanVienRespon> LocNhanVienTrangThai(Integer page, Integer trangthai);

    // Cập nhật nhân viên
    MessageNhanVienRespon UpdateNhanVien(NhanVienRequest nhanVienRequest);

    // Cập nhật trạng thái nhân viên
    MessageNhanVienRespon UpdateTrangThai(UUID id, Integer trangthai);

    // Thêm mới nhân viên
    MessageNhanVienRespon ThemNhanVien(NhanVienRequest nhanVienRequest);
}
