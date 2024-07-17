package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.HoaDonChiTiet;
import com.example.be_duantn.entity.SanPhamChiTiet;
import com.example.be_duantn.enums.TrangThaiDonHangEnums;
import com.example.be_duantn.repository.mua_hang_oneline_repository.SanPhamChiTietRepository;
import com.example.be_duantn.repository.mua_hang_oneline_repository.ThongTinHoaDonRepository;
import com.example.be_duantn.repository.mua_hang_oneline_repository.hoa_don_repository.HoaDonCTThanhToanRepository;
import com.example.be_duantn.service.mua_hang_online_service.ThongTinHoaDonSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ThongTinHoaDonSeviceImpl implements ThongTinHoaDonSevice {

    @Autowired
    ThongTinHoaDonRepository thongTinHoaDonRepository ;

    @Autowired
    HoaDonCTThanhToanRepository hoaDonCTThanhToanRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Override
    public List<TTHoaDonRespon> LoadTTHoaDonKhachHang(UUID idkh, Integer trangthai) {
        return thongTinHoaDonRepository.LoadTTHoaDonKhachHang(idkh, trangthai);
    }

    @Override
    public List<TTSPHoaDonRespon> LoadTTSPHoaDonKhachHang(UUID idhoadon) {
        return thongTinHoaDonRepository.LoadTTSPHoaDonKhachHang(idhoadon);
    }

    @Override
    public List<TTHoaDonRespon> TimKiemHoaDonKhachHang(UUID idkh, String keyword, Integer trangthai) {
        return thongTinHoaDonRepository.TimKiemHoaDonKhachHang(idkh, keyword, trangthai);
    }

    @Override
    public TTHoaDonCTRespon FinTTHoaDonCTKhachHang(UUID idhoadon) {
        return thongTinHoaDonRepository.FinTTHoaDonCTKhachHang(idhoadon);
    }

    @Override
    public List<TTHinhThucTTRespon> FinTTHinhThucTT(UUID idhoadon) {
        return thongTinHoaDonRepository.FinTTHinhThucTT(idhoadon);
    }

    @Override
    public List<TTSPHoaDonRespon> FinTTSPHoaDonCTKhachHang(UUID idhoadon) {
        return thongTinHoaDonRepository.FinTTSPHoaDonCTKhachHang(idhoadon);
    }

    @Override
    public TTLichSuHDRespon FindLichSuNgayHD(UUID idhoadon) {
        return thongTinHoaDonRepository.FindLichSuNgayHD(idhoadon);
    }

    @Override
    public MessageHuyDonHangRespon HuyDonHangKhachHang(UUID idhoadon) {

        HoaDon hoadon = thongTinHoaDonRepository.findById(idhoadon).orElse(null);
        if(hoadon != null){
            List<HoaDonChiTiet> listhdct = hoaDonCTThanhToanRepository.findByHoadon_Idhoadon(idhoadon);
            for (HoaDonChiTiet hdct : listhdct){
                if(hdct != null){
                    SanPhamChiTiet spct = sanPhamChiTietRepository.findById(hdct.getSanphamchitiet().getIdspct()).get();
                    spct.setSoluongton(spct.getSoluongton() + hdct.getSoluong());
                    sanPhamChiTietRepository.save(spct);

                    hdct.setTrangthai(2);
                    hdct.setNgaycapnhat(new Date(System.currentTimeMillis()));
                    hdct.setGhichu("Khách hàng đã hủy hóa đơn online");
                    hoaDonCTThanhToanRepository.save(hdct);
                }else {
                    return  MessageHuyDonHangRespon.builder().message("Không tìm thấy IdHDCT muốn hủy của khách hàng !").build();
                }
            }

            hoadon.setNgaycapnhat(new Date(System.currentTimeMillis()));
            hoadon.setNgayhuy(new Date(System.currentTimeMillis()));
            hoadon.setGhichu("Khách hàng đã hủy hóa đơn online");
            hoadon.setTrangthai(TrangThaiDonHangEnums.DA_HUY.getValue());
            thongTinHoaDonRepository.save(hoadon);
            return MessageHuyDonHangRespon.builder().message("Không tìm thấy Idhoadon muốn hủy của khách hàng !").build();
        }else{
            return MessageHuyDonHangRespon.builder().message("Khách hàng hủy đơn hàng online thành công ! !").build();
        }
    }
}
