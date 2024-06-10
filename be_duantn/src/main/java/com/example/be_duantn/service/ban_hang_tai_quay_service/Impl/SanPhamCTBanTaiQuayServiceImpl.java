package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.*;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.SanPhamCTBanTaiQuayRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.SanPhamCTBanTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamCTBanTaiQuayServiceImpl implements SanPhamCTBanTaiQuayService {
    @Autowired
    SanPhamCTBanTaiQuayRepository sanPhamCTBanTaiQuayRepository;

    @Override
    public Page<LoadSPTaiQuayRespon> LoadSPBanTaiQuay(Integer page) {
        Pageable pageable = PageRequest.of(page,20);
        return sanPhamCTBanTaiQuayRepository.LoadSPBanTaiQuay(pageable);
    }

    @Override
    public List<LoadMSTaiQuayRespon> LoadMauSacBanTaiQuay() {
        return sanPhamCTBanTaiQuayRepository.LoadMauSacBanTaiQuay();
    }

    @Override
    public List<LoadSizeTaiQuayRespon> LoadSizeBanTaiQuay() {
        return sanPhamCTBanTaiQuayRepository.LoadSizeBanTaiQuay();
    }

    @Override
    public List<LoadChatLieuTaiQuayRespon> LoadChatLieuBanTaiQuay() {
        return sanPhamCTBanTaiQuayRepository.LoadChatLieuBanTaiQuay();
    }

    @Override
    public List<LoadDanhMucTaiQuayRespon> LoadDanhMucBanTaiQuay() {
        return sanPhamCTBanTaiQuayRepository.LoadDanhMucBanTaiQuay();
    }

    @Override
    public List<LoadThuongHieuTaiQuayRespon> LoadThuongHieuBanTaiQuay() {
        return sanPhamCTBanTaiQuayRepository.LoadThuongHieuBanTaiQuay();
    }

    @Override
    public List<LoadXuatXuTaiQuayRespon> LoadXuatXuBanTaiQuay() {
        return sanPhamCTBanTaiQuayRepository.LoadXuatXuBanTaiQuay();
    }

    @Override
    public Page<LoadSPTaiQuayRespon> LocTenSPBanTaiQuay(Integer page, String tensp) {
        Pageable pageable = PageRequest.of(page, 20);
        return sanPhamCTBanTaiQuayRepository.LocTenSPBanTaiQuay(pageable, tensp);
    }

    @Override
    public Page<LoadSPTaiQuayRespon> LocSPNhieuTieuChiBanTaiQuay(Integer page, String tenmausac, String tensize, String tenchatlieu, String tendanhmuc, String tenthuonghieu, String tenxuatxu) {
        Pageable pageable = PageRequest.of(page, 20);
        return sanPhamCTBanTaiQuayRepository.LocSPNhieuTieuChiBanTaiQuay(pageable, tenmausac, tensize, tenchatlieu, tendanhmuc, tenthuonghieu, tenxuatxu);
    }
}
