package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;
import com.example.be_duantn.repository.mua_hang_oneline_repository.SanPhamShopRepository;
import com.example.be_duantn.service.mua_hang_online_service.SanPhamShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamShopServiceImpl implements SanPhamShopService {

    @Autowired
    SanPhamShopRepository sanPhamShopRepository;

    @Override
    public List<DanhMucSPShopRespon> loadDanhMucSPShop() {
        return sanPhamShopRepository.loadDanhMucSPShop();
    }

    @Override
    public List<MauSacSPShopRespon> loadMauSacSPShop() {
        return sanPhamShopRepository.loadMauSacSPShop();
    }

    @Override
    public List<SizeSPShopRespon> loadSizeSPShop() {
        return sanPhamShopRepository.loadSizeSPShop();
    }

    @Override
    public Page<SanPhamShopRespon> loadSPShop(Integer page) {
        Pageable pageable = PageRequest.of(page, 9);
        return sanPhamShopRepository.loadSPShop(pageable);
    }

    @Override
    public Page<SanPhamShopRespon> locKhongGiaSPShop(Integer pageNumber, Integer pageSize, Double key1, Double key2) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return sanPhamShopRepository.locKhoangGiaSPShop(pageable, key1, key2);
    }

    @Override
    public Page<SanPhamShopRespon> locSPShop(Integer pageNumber, Integer pageSize, String tensp, String tendanhmuc, String tenmausac, String tensize) {
        Pageable pageable = PageRequest.of(pageNumber , pageSize);
        return sanPhamShopRepository.locSPShop(pageable, tensp, tendanhmuc, tenmausac, tensize);
    }
}
