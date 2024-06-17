package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.KhachHangDiaChiRequest;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadDiaChiTaiQuayRespon;

import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.entity.KhachHang;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface KhachHangBanTaiQuayService {

    Page<LoadDiaChiTaiQuayRespon> getKhachHangByTrangThai(int page, int size);


    LoadDiaChiTaiQuayRespon finByIdKh(UUID Idkh);

    Page<LoadDiaChiTaiQuayRespon> LocTenKHBanTaiQuay( String keyword, int page, int size);

    KhachHang addKhachHang(KhachHang khachHang, DiaChi diaChi);

}
