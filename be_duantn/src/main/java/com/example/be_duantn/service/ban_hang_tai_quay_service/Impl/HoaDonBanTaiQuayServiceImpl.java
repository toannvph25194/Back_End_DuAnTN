package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonTaiQuayRequest;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageHuyHoaDon;
import com.example.be_duantn.entity.*;
import com.example.be_duantn.enums.TrangThaiDonHangEnums;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.*;
import com.example.be_duantn.service.ban_hang_tai_quay_service.HoaDonBanTaiQuayService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class HoaDonBanTaiQuayServiceImpl implements HoaDonBanTaiQuayService {

    @Autowired
    HoaDonBanTaiQuayRepository hoaDonBanTaiQuayRepository;

    @Autowired
    HoaDonCTBanTaiQuayRepository hoaDonCTBanTaiQuayRepository;

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
        KhachHang khachle;
        if (taikhoan != null) {
            NhanVien finnv = nhanVienBanTaiQuayRepository.findByTaikhoan(taikhoan);
            // Kiểm tra xem đã có khách lẻ chưa
            KhachHang finkl = khachHangBanTaiQuayRepository.findByTaikhoan("khachle");
            if (finkl != null) {
                // Nếu có dùng luôn khách lẻ đó
                khachle = finkl;
            } else {
                // Nếu chưa có thì tạo khách lẻ
                khachle = new KhachHang();
                khachle.setIdkh(UUID.randomUUID());
                khachle.setHovatenkh("Khách Lẻ");
                khachle.setTaikhoan("khachle");
                khachle.setTrangthai(2);
                khachHangBanTaiQuayRepository.save(khachle);
            }
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
            return HoaDonTaiQuayRequest.builder().idhoadon(hd.getIdhoadon()).idkh(khachle.getIdkh()).message("Tạo hóa đơn bán tại quầy thành công !").build();
        } else {
            return HoaDonTaiQuayRequest.builder().message("Tạo hóa đơn bán tại quầy thất bại. K tìm thấy nhân viên !").build();
        }
    }

    @Override
    public MessageHuyHoaDon HuyHoaDonTaiQuay(UUID idhoadon, Principal principal) {
        // Lấy thông tin hóa đơn
        HoaDon finhoadon = hoaDonBanTaiQuayRepository.findById(idhoadon).orElse(null);
        if (finhoadon != null) {
            // Nếu ghct k null thì sẽ cập nhật
            List<HoaDonChiTiet> listhdct = hoaDonCTBanTaiQuayRepository.findByHoadon_Idhoadon(finhoadon.getIdhoadon());
            for (HoaDonChiTiet hdct : listhdct) {
                if (hdct != null) {
                    // Cộng lại số lượng tồn spct
                    SanPhamChiTiet spct = sanPhamCTBanTaiQuayRepository.findById(hdct.getSanphamchitiet().getIdspct()).get();
                    spct.setSoluongton(spct.getSoluongton() + hdct.getSoluong());
                    sanPhamCTBanTaiQuayRepository.save(spct);

                    // Cập nhật lại ghct
                    hdct.setSoluong(0);
                    hdct.setTrangthai(2);
                    hdct.setNgaycapnhat(new Date(System.currentTimeMillis()));
                    hdct.setGhichu("Hóa đơn chi tiết tại quầy đã bị hủy");
                    hoaDonCTBanTaiQuayRepository.save(hdct);
                }
            }
        } else {
            return MessageHuyHoaDon.builder().message("Không tìm thấy hóa đơn của khách !").build();
        }
        // Cập nhật lại trạng thái hóa đơn thành hủy hóa đơn
        finhoadon.setNgaycapnhat(new Date(System.currentTimeMillis()));
        finhoadon.setGhichu("Hóa đơn tại quầy đã bị hủy");
        finhoadon.setTrangthai(TrangThaiDonHangEnums.DA_HUY.getValue());
        hoaDonBanTaiQuayRepository.save(finhoadon);
        return MessageHuyHoaDon.builder().message("Hủy hóa đơn thành công").build();
    }

    @Override
    public List<LoadHoaDonRespon> TimKiemHoaDonTaiQuay(String mahoadon) {
        return hoaDonBanTaiQuayRepository.TimKiemHoaDonTaiQuay(mahoadon);
    }



    ////Hùng làm update khách hàng ở hóa đơn
    @Transactional
    public int updateKhachHang(UUID idhoadon, UUID idkh, String hovatenkh) {
        return hoaDonBanTaiQuayRepository.updateKhachHangByIdHoaDon(idkh, idhoadon, hovatenkh);
    }
}
