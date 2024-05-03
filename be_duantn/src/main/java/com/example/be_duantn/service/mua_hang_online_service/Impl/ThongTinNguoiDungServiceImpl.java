package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.TTNguoiDungRespon;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.repository.mua_hang_oneline_repository.ThongTinNguoiDungRepository;
import com.example.be_duantn.service.mua_hang_online_service.ThongTinNguoiDungService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ThongTinNguoiDungServiceImpl implements ThongTinNguoiDungService {

    @Autowired
    ThongTinNguoiDungRepository thongTinNguoiDungRepository;

    @Override
    public TTNguoiDungRespon findByKhachhang(UUID idkh) {
        Optional<KhachHang> kh = thongTinNguoiDungRepository.findById(idkh);
        if (kh.isPresent()){
            TTNguoiDungRespon khDto = new TTNguoiDungRespon();
            khDto.setTaikhoan(kh.get().getTaikhoan());
            khDto.setHovatenkh(kh.get().getHovatenkh());
            khDto.setSodienthoai(kh.get().getSodienthoai());
            khDto.setEmail(kh.get().getEmail());
            return khDto;
        }else {
            throw new EntityNotFoundException("K tìm thấy idkh !");
        }
    }
}
