package com.example.be_duantn.service.quan_ly_nguoi_dung_service;

import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienRegisterRequest;
import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.NhanVienUpdateRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienMessageResponse;
import com.example.be_duantn.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface QuanLyNhanVienService {
    Page<NhanVien> getAllNhanVien(int page, int size);

    NhanVien updateStatus(UUID id, Integer newStatus);

    boolean emailExists(String email);

    boolean phoneExists(String phone);

    NhanVien updateNhanVien(NhanVienUpdateRequest nhanVienUpdate, UUID id);

    NhanVien getNhanVienById(UUID id);

    Page<NhanVien> searchNhanVien(String search, Integer trangthai, Pageable pageable);

    NhanVienMessageResponse themNhanVien(NhanVienRegisterRequest nhanVienRegisterRequest);

}
