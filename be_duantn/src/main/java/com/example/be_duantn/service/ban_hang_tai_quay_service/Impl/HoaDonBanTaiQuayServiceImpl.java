package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonTaiQuayRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.FinGioHangBanTaiQuay;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageHuyHoaDon;
import com.example.be_duantn.entity.*;
import com.example.be_duantn.enums.TrangThaiDonHangEnums;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.*;
import com.example.be_duantn.service.ban_hang_tai_quay_service.HoaDonBanTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

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

    @Autowired
    GiohangChiTietBanTaiQuayRepository giohangChiTietBanTaiQuayRepository;

    @Autowired
    SanPhamCTBanTaiQuayRepository sanPhamCTBanTaiQuayRepository;

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
            hd.setGhichu("Nhân viên tạo hóa đơn cho khách");
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

    @Override
    public MessageHuyHoaDon HuyHoaDonTaiQuay(UUID idhoadon, UUID idkh, Principal principal) {
        // Lấy thông tin hóa đơn
        HoaDon finhoadon = hoaDonBanTaiQuayRepository.findById(idhoadon).orElse(null);
        // Tìm id giỏ hàng của khách hàng
        FinGioHangBanTaiQuay fingiohang = gioHangBanTaiQuayRepository.TimKiemGioHangBanTaiQuay(idkh);
        if(fingiohang != null){
            // Nếu giỏ hàng k null thì sẽ cập nhật giỏ hàng
            GioHang gh = gioHangBanTaiQuayRepository.findById(fingiohang.getId()).orElse(null);
            gh.setTrangthai(3);
            gh.setNgaycapnhat(new Date(System.currentTimeMillis()));
            gh.setGhichu("Giỏ hàng của khách không còn sử dụng");
            gioHangBanTaiQuayRepository.save(gh);

            // Nếu ghct k null thì sẽ cập nhật
            List<GioHangChiTiet> listghct = giohangChiTietBanTaiQuayRepository.findByGiohang_Idgh(gh.getIdgh());
            for (GioHangChiTiet ghct : listghct){
                if (ghct != null) {
                    // Cộng lại số lượng tồn spct
                    SanPhamChiTiet spct = sanPhamCTBanTaiQuayRepository.findById(ghct.getSanphamchitiet().getIdspct()).get();
                    spct.setSoluongton(spct.getSoluongton() + ghct.getSoluong());
                    sanPhamCTBanTaiQuayRepository.save(spct);

                    // Cập nhật lại ghct
                    ghct.setSoluong(0);
                    ghct.setTrangthai(3);
                    ghct.setNgaycapnhat(new Date(System.currentTimeMillis()));
                    ghct.setGhichu("Giỏ hàng chi tiết không còn sử dụng");
                    giohangChiTietBanTaiQuayRepository.save(ghct);
                }
            }
        } else {
            return MessageHuyHoaDon.builder().message("Không tìm thấy giỏ hảng của khách !").build();
        }

        // Cập nhật lại trạng thái hóa đơn thành hủy hóa đơn
        finhoadon.setNgaycapnhat(new Date(System.currentTimeMillis()));
        finhoadon.setGhichu("Hóa đơn tại quầy đã bị hủy");
        finhoadon.setTrangthai(TrangThaiDonHangEnums.DA_HUY.getValue());
        hoaDonBanTaiQuayRepository.save(finhoadon);
        return  MessageHuyHoaDon.builder().message("Hủy hóa đơn thành công").build();
    }

    @Override
    public List<LoadHoaDonRespon> TimKiemHoaDonTaiQuay(String mahoadon) {
        return hoaDonBanTaiQuayRepository.TimKiemHoaDonTaiQuay(mahoadon);
    }
}
