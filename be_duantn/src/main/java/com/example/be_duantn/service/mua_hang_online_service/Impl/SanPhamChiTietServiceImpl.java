package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;
import com.example.be_duantn.repository.mua_hang_oneline_repository.SanPhamChiTietRepository;
import com.example.be_duantn.service.mua_hang_online_service.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Override
    public List<SanPhamRespon> loadSPCTNew() {
        return sanPhamChiTietRepository.loadSPCTNew();
    }

    @Override
    public SanPhamChiTietRespon finByIdSP(UUID idsp) {
        return sanPhamChiTietRepository.finByidSP(idsp);
    }

    @Override
    public List<ListImageSPRespon> loadListImageSP(UUID id) {
        return sanPhamChiTietRepository.loadListImageSP(id);
    }

    @Override
    public List<LoadMauSacByIdSP> loadMauSacByIdSP(UUID idsp) {
        return sanPhamChiTietRepository.loadMauSacByIdSP(idsp);
    }

    @Override
    public List<LoadSizeByIdSP> loadSizeByIdSP(UUID idsp) {
        return sanPhamChiTietRepository.loadSizeByIdSP(idsp);
    }

    @Override
    public List<FinMauSacByIdSPAndIdsize> finMauSacByIdSPAndIdSize(UUID idsp, UUID idsize) {
        return sanPhamChiTietRepository.finMauSacByIdSanPhamAndIdSize(idsp, idsize);
    }

    @Override
    public List<FinSizeByIdSPAndIdMauSac> finSizeByIdSPAndIdMauSac(UUID idsp, UUID idmausac) {
        return sanPhamChiTietRepository.finSizeIdSanPhamAndIdMauSac(idsp, idmausac);
    }

    @Override
    public TongSoLuongTonSPCTRespon tinhTongSoLuongTonSPCT(UUID idsp) {
        return sanPhamChiTietRepository.tinhTongSoLuongTonSPCT(idsp);
    }

    @Override
    public FinByIdSPCTAndSoLuongTon finByIdSPCTAndSoLuongTon(UUID idsp, UUID idms, UUID idsize) {
        return sanPhamChiTietRepository.finByIdSPCTAndSoLuongTon(idsp, idms, idsize);
    }
}
