package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.request.mua_hang_online_request.ImageTKRequest;
import com.example.be_duantn.dto.request.mua_hang_online_request.TTTaiKhoanRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.TTNguoiDungRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinTTNguoiDungRespon;
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

    @Override
    public FinTTNguoiDungRespon finByTaiKhoanND(UUID idkh) {
        return thongTinNguoiDungRepository.finByTaiKhoanND(idkh);
    }

    @Override
    public TTTaiKhoanRequest updateTaiKhoanND(TTTaiKhoanRequest khrequest) {
        KhachHang khfin = thongTinNguoiDungRepository.findById(khrequest.getIdkh()).orElse(null);
        if(khfin != null){
            khfin.setHovatenkh(khrequest.getHovatenkh());
            khfin.setEmail(khrequest.getEmail());
            khfin.setSodienthoai(khrequest.getSodienthoai());
            khfin.setNgaysinh(khrequest.getNgaysinh());
            khfin.setGioitinh(khrequest.getGioitinh());
            thongTinNguoiDungRepository.save(khfin);
        }else {
            throw new EntityNotFoundException("K tìm thấy idkh !");
        }
        return khrequest;
    }

    @Override
    public ImageTKRequest uploadImageTK(ImageTKRequest upload) {
        KhachHang khfin = thongTinNguoiDungRepository.findById(upload.getIdkh()).orElse(null);
        if(khfin != null){
            khfin.setImage(upload.getImage());
            thongTinNguoiDungRepository.save(khfin);
        }else {
            throw new EntityNotFoundException("K tìm thấy idkh !");
        }
        return upload;
    }
}
