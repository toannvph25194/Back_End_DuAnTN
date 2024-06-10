package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.FinGioHangBanTaiQuay;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.GioHangBanTaiQuayRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.GioHangBanTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GioHangBanTaiQuayServiceImpl implements GioHangBanTaiQuayService {
    @Autowired
    GioHangBanTaiQuayRepository gioHangBanTaiQuayRepository;

    @Override
    public FinGioHangBanTaiQuay TimKiemGioHangTaiQuay(UUID idkh) {
        return gioHangBanTaiQuayRepository.TimKiemGioHangBanTaiQuay(idkh);
    }
}
