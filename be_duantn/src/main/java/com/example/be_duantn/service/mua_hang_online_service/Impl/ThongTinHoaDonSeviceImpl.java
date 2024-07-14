package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;
import com.example.be_duantn.repository.mua_hang_oneline_repository.ThongTinHoaDonRepository;
import com.example.be_duantn.service.mua_hang_online_service.ThongTinHoaDonSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ThongTinHoaDonSeviceImpl implements ThongTinHoaDonSevice {

    @Autowired
    ThongTinHoaDonRepository thongTinHoaDonRepository ;

    @Override
    public List<TTHoaDonRespon> LoadTTHoaDonKhachHang(UUID idkh, Integer trangthai) {
        return thongTinHoaDonRepository.LoadTTHoaDonKhachHang(idkh, trangthai);
    }

    @Override
    public List<TTSPHoaDonRespon> LoadTTSPHoaDonKhachHang(UUID idhoadon) {
        return thongTinHoaDonRepository.LoadTTSPHoaDonKhachHang(idhoadon);
    }

    @Override
    public List<TTHoaDonRespon> TimKiemHoaDonKhachHang(UUID idkh, String keyword, Integer trangthai) {
        return thongTinHoaDonRepository.TimKiemHoaDonKhachHang(idkh, keyword, trangthai);
    }

    @Override
    public TTHoaDonCTRespon FinTTHoaDonCTKhachHang(UUID idhoadon) {
        return thongTinHoaDonRepository.FinTTHoaDonCTKhachHang(idhoadon);
    }

    @Override
    public List<TTHinhThucTTRespon> FinTTHinhThucTT(UUID idhoadon) {
        return thongTinHoaDonRepository.FinTTHinhThucTT(idhoadon);
    }

    @Override
    public List<TTSPHoaDonRespon> FinTTSPHoaDonCTKhachHang(UUID idhoadon) {
        return thongTinHoaDonRepository.FinTTSPHoaDonCTKhachHang(idhoadon);
    }

    @Override
    public TTLichSuHDRespon FindLichSuNgayHD(UUID idhoadon) {
        return thongTinHoaDonRepository.FindLichSuNgayHD(idhoadon);
    }
}
