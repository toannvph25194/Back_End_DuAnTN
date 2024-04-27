package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinByGioHangRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHang;
import com.example.be_duantn.entity.GioHang;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.repository.authentication_repository.KhachHangRepository;
import com.example.be_duantn.repository.mua_hang_oneline_repository.GioHangRepository;
import com.example.be_duantn.service.mua_hang_online_service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Override
    public MessageGioHang TaoGioHang(GioHang gh, UUID idkh) {
        List<FinByGioHangRespon> giohangcheck = gioHangRepository.finByKhAndTrangThai(idkh, 1);
        if (giohangcheck.isEmpty()){

            KhachHang kh = khachHangRepository.findById(idkh).orElse(null);
            GioHang ghmoi = new GioHang();
            ghmoi.setIdgh(UUID.randomUUID());
            ghmoi.setKhachhang(kh);
            ghmoi.setNgaytao(gh.getNgaytao());
            ghmoi.setGhichu(gh.getGhichu());
            ghmoi.setTrangthai(1);
            gioHangRepository.save(ghmoi);
            return MessageGioHang.builder().message("Tạo giỏ hàng thành công !").Idgh(ghmoi.getIdgh()).build();
        }else {
            return MessageGioHang.builder().message("KH đã có gh và trạng thái gh là 1 !").build();
        }
    }

    @Override
    public FinByGioHangRespon finByIdGioHang(UUID idkh) {
        return gioHangRepository.finByIdGioHang(idkh);
    }
}
