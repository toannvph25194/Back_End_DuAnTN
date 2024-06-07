package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonTaiQuayRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;
import com.example.be_duantn.entity.GioHang;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.enums.TrangThaiDonHangEnums;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.GioHangBanTaiQuayRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.HoaDonBanTaiQuayRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.KhachHangBanTaiQuayRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.NhanVienBanTaiQuayRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.HoaDonBanTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class HoaDonBanTaiQuayServiceImpl implements HoaDonBanTaiQuayService {

    @Autowired
    HoaDonBanTaiQuayRepository hoaDonBanTaiQuayRepository;

    @Autowired
    NhanVienBanTaiQuayRepository nhanVienBanTaiQuayRepository;

    @Autowired
    KhachHangBanTaiQuayRepository khachHangBanTaiQuayRepository;

    @Autowired
    GioHangBanTaiQuayRepository gioHangBanTaiQuayRepository;

    @Override
    public List<LoadHoaDonRespon> LoadHoaDonTaiQuay() {
        return hoaDonBanTaiQuayRepository.LoadHoaDonTaiQuay();
    }

    @Override
    public HoaDonTaiQuayRequest TaoHoaDonTaiQuay(Principal principal) {
        // Lấy idnv từ principal
        String taikhoan = principal.getName();
        if(taikhoan != null){
            NhanVien finnv = nhanVienBanTaiQuayRepository.findByTaikhoan(taikhoan);

            // Tạo khách lẻ
            KhachHang khachle = new KhachHang();
            khachle.setIdkh(UUID.randomUUID());
            khachle.setTaikhoan("khachle");
            khachle.setTrangthai(2);
            khachHangBanTaiQuayRepository.save(khachle);

            // Tạo hóa đơn
            Random random = new Random();
            int randomNumber = random.nextInt(100000);
            String maHĐ = String.format("HDTQ%03d", randomNumber);
            HoaDon hd = new HoaDon();
            hd.setIdhoadon(UUID.randomUUID());
            hd.setNhanvien(finnv);
            hd.setKhachhang(khachle);
            hd.setMahoadon(maHĐ);
            hd.setNgaytao(new Date(System.currentTimeMillis()));
            hd.setTennguoinhan("Khách Lẻ");
            hd.setGhichu("Nhân viên tạo hóa đơn cho khác");
            hd.setLoaihoadon(2);
            hd.setTrangthai(TrangThaiDonHangEnums.CHO_XAC_NHAN.getValue());
            hoaDonBanTaiQuayRepository.save(hd);

            // Tạo giỏ hàng
            GioHang gh = new GioHang();
            gh.setIdgh(UUID.randomUUID());
            gh.setNhanvien(finnv);
            gh.setKhachhang(khachle);
            gh.setNgaytao(new Date(System.currentTimeMillis()));
            gh.setGhichu("Nhân viên tạo giỏ hàng trống cho khách");
            gh.setTrangthai(1);
            gioHangBanTaiQuayRepository.save(gh);

            return HoaDonTaiQuayRequest.builder().idhoadon(hd.getIdhoadon()).idkh(khachle.getIdkh()).message("Tạo hóa đơn bán tại quầy thành công !").build();
        }else {
            return HoaDonTaiQuayRequest.builder().message("Tạo hóa đơn bán tại quầy thất bại. K tìm thấy nhân viên !").build();
        }


    }
}
