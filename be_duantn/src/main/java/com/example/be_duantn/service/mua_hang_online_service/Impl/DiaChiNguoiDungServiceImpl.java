package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.DiaChiNguoiDungRespon;
import com.example.be_duantn.repository.mua_hang_oneline_repository.DiaChiNguoiDungRepository;
import com.example.be_duantn.service.mua_hang_online_service.DiaChiNguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DiaChiNguoiDungServiceImpl implements DiaChiNguoiDungService {

    @Autowired
    DiaChiNguoiDungRepository ttNguoiDungRepository;

    @Override
    public List<DiaChiNguoiDungRespon> finByDiaChiNguoiDung(UUID idkh) {
        return ttNguoiDungRepository.finByDiaChiNguoiDung(idkh);
    }
}
