package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.TongTienBanTaiQuayRespon;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.TongTienBanTaiquayRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.TongTienBanTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TongTienBanTaiQuayServiceImpl implements TongTienBanTaiQuayService {

    @Autowired
    TongTienBanTaiquayRepository tongTienBanTaiquayRepository;

    @Override
    public TongTienBanTaiQuayRespon tongtien(UUID id) {
        return tongTienBanTaiquayRepository.TongTienBanTaiQuay(id);
    }
}
