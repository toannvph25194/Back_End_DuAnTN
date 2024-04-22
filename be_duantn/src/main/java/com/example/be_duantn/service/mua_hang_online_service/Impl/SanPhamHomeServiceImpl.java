package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamGiamGiaRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamRespon;
import com.example.be_duantn.repository.mua_hang_oneline_repository.SanPhamHomeRepository;
import com.example.be_duantn.service.mua_hang_online_service.SanPhamHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamHomeServiceImpl implements SanPhamHomeService {

    @Autowired
    SanPhamHomeRepository sanPhamHomeRepository;

    @Override
    public Page<SanPhamRespon> getAllSPHome(Integer page) {
        Pageable pageable = PageRequest.of(page , 12);
        return sanPhamHomeRepository.getALlSPHome(pageable);
    }

    @Override
    public Page<SanPhamRespon> getAllSPNamHome(Integer page) {
        Pageable pageable = PageRequest.of(page , 12);
        return sanPhamHomeRepository.getALlSPNamHome(pageable);
    }

    @Override
    public Page<SanPhamRespon> getAllSPNuHome(Integer page) {
        Pageable pageable = PageRequest.of(page , 12);
        return sanPhamHomeRepository.getALlSPNuHome(pageable);
    }

    @Override
    public List<SanPhamRespon> getAllSPNewHome() {
        return sanPhamHomeRepository.getALlSPNewHome();
    }

    @Override
    public List<SanPhamGiamGiaRespon> getAllSPGGHome() {
        return sanPhamHomeRepository.getAllSPGGHome();
    }
}
