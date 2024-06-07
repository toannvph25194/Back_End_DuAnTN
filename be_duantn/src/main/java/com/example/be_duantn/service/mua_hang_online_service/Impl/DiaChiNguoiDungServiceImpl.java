package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.request.mua_hang_online_request.DiaChiTaiKhoanRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.DiaChiNguoiDungRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.FinByDCNguoiDungRespon;
import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.repository.authentication_repository.KhachHangRepository;
import com.example.be_duantn.repository.mua_hang_oneline_repository.DiaChiNguoiDungRepository;
import com.example.be_duantn.service.mua_hang_online_service.DiaChiNguoiDungService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiaChiNguoiDungServiceImpl implements DiaChiNguoiDungService {

    @Autowired
    DiaChiNguoiDungRepository ttNguoiDungRepository;
    @Autowired
    KhachHangRepository khachHangRepository;

    @Override
    public List<DiaChiNguoiDungRespon> finByDiaChiKhachHang(UUID idkh) {
        return ttNguoiDungRepository.finByDiaChiKhachHang(idkh);
    }

    @Override
    public List<FinByDCNguoiDungRespon> finByDiaChiTaiKhoan(UUID idkh) {
        return ttNguoiDungRepository.finByDiaChiTaiKhoan(idkh);
    }

    @Override
    public DiaChi ThemDiaChiTaiKhoan(DiaChiTaiKhoanRequest dcrequest) {
        KhachHang kh = khachHangRepository.findById(dcrequest.getIdkh()).orElse(null);
        if(kh != null){
            DiaChi dcmoi = new DiaChi();
            dcmoi.setIddiachi(UUID.randomUUID());
            dcmoi.setKhachhang(kh);
            dcmoi.setDiachichitiet(dcrequest.getDiachichitiet());
            dcmoi.setPhuongxa(dcrequest.getPhuongxa());
            dcmoi.setQuanhuyen(dcrequest.getQuanhuyen());
            dcmoi.setTinhthanh(dcrequest.getTinhthanh());
            dcmoi.setQuocgia("Việt Nam");
            dcmoi.setNgaytao(new Date(System.currentTimeMillis()));
            dcmoi.setTrangthai(2);
            ttNguoiDungRepository.save(dcmoi);
            return dcmoi;
        }else {
            throw new EntityNotFoundException("K tìm thấy idkh !");
        }
    }

    @Override
    public DiaChi UpdateDiaChiTaiKhoan(DiaChiTaiKhoanRequest dcrequest) {
        DiaChi diachiFin = ttNguoiDungRepository.findById(dcrequest.getIddiachi()).orElse(null);
        if(diachiFin != null){
            diachiFin.setDiachichitiet(dcrequest.getDiachichitiet());
            diachiFin.setPhuongxa(dcrequest.getPhuongxa());
            diachiFin.setQuanhuyen(dcrequest.getQuanhuyen());
            diachiFin.setTinhthanh(dcrequest.getTinhthanh());
            diachiFin.setNgaycapnhat(new Date(System.currentTimeMillis()));
            diachiFin.setGhichu("Cập nhật thông tin địa chỉ");
            ttNguoiDungRepository.save(diachiFin);
            return diachiFin;
        }else {
            throw new EntityNotFoundException("K tìm thấy iddc !");
        }
    }

    @Override
    public DiaChi UpdateTrangThaiDC(UUID iddc, String taikhoan) {
        DiaChi iddiachichonmd = ttNguoiDungRepository.findById(iddc).orElse(null);
        if(iddiachichonmd != null){
            List<DiaChi> listdcbd = ttNguoiDungRepository.findByTrangthaiAndKhachhangTaikhoan(1, taikhoan);
            // Cập nhật trạng thái của tất cả các địa chỉ này thành 2
            for (DiaChi dc : listdcbd) {
                dc.setTrangthai(2);
                ttNguoiDungRepository.save(dc);
            }
            // Cập nhật trạng thái của địa chỉ chọn mặc định thành 1
            iddiachichonmd.setTrangthai(1);
            iddiachichonmd.setNgaycapnhat(new Date(System.currentTimeMillis()));
            iddiachichonmd.setGhichu("Cập nhật trạng thái địa chỉ");
            ttNguoiDungRepository.save(iddiachichonmd);
            return iddiachichonmd;
        }else {
            throw new EntityNotFoundException("K tìm thấy iddc !");
        }
    }
}
