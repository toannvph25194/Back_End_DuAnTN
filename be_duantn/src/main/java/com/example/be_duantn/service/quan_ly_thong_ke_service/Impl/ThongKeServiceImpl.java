package com.example.be_duantn.service.quan_ly_thong_ke_service.Impl;

import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.repository.quan_ly_thong_ke_repository.ThongKeRepository;
import com.example.be_duantn.dto.respon.thong_ke_respon.DoanhThuResponse;

import com.example.be_duantn.service.quan_ly_thong_ke_service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    ThongKeRepository thongKeRepository;


    @Override
    public List<DoanhThuResponse> doanhThu(Date ngayBd, Date ngayKt) {
        return thongKeRepository.doanhThu(ngayBd,ngayKt);
    }

    @Override
    public BigDecimal tongTienDonHomNay() {
        return thongKeRepository.tongTienDonHomNay();
    }

    @Override
    public BigDecimal tongTienDonTuanNay() {
        return thongKeRepository.tongTienDonTuanNay();
    }

    @Override
    public BigDecimal tongTienDonThangNay() {
        return thongKeRepository.tongTienDonThangNay();
    }

    @Override
    public BigDecimal tongTienDonNamNay() {
        return thongKeRepository.tongTienDonNamNay();
    }

    public List<HoaDon> getAllHoaDon() {
        return thongKeRepository.findAll();
    }

    @Override
    public Integer soDonHomNay() {
        return thongKeRepository.soDonHomNay();
    }

    @Override
    public Integer soDonTuanNay() {
        return thongKeRepository.soDonTuanNay();
    }

    @Override
    public Integer soDonThangNay() {
        return thongKeRepository.soDonThangNay();
    }

    @Override
    public Integer soDonNamNay() {
        return thongKeRepository.soDonNamNay();
    }

    @Override
    public Integer soDonHuyHomNay() {
        return thongKeRepository.soDonHuyHomNay();
    }

    @Override
    public Integer soDonHuyTuanNay() {
        return thongKeRepository.soDonHuyTuanNay();
    }

    @Override
    public Integer soDonHuyThangNay() {
        return thongKeRepository.soDonHuyThangNay();
    }

    @Override
    public Integer soDonHuyNamNay() {
        return thongKeRepository.soDonHuyNamNay();
    }

    @Override
    public Integer soSanPhamBanRaHomNay() {
        return thongKeRepository.soSanPhamBanRaHomNay();
    }

    @Override
    public Integer soSanPhamBanRaTuanNay() {
        return thongKeRepository.soSanPhamBanRaTuanNay();
    }

    @Override
    public Integer soSanPhamBanRaThangNay() {
        return thongKeRepository.soSanPhamBanRaThangNay();
    }

    @Override
    public Integer soSanPhamBanNamNay() {
        return thongKeRepository.soSanPhamBanRaNamNay();
    }

    @Override
    public Integer demSoHoaDonChoXacNhan() {
        return thongKeRepository.demSoHoaDonChoXacNhan();
    }

    @Override
    public Integer demSoHoaDonXacNhan() {
        return thongKeRepository.demSoHoaDonXacNhan();
    }

    @Override
    public Integer demSoHoaDonDangGiao() {
        return thongKeRepository.demSoHoaDonDangGiao();
    }

    @Override
    public Integer demSoHoaDonChoGiaoHang() {
        return thongKeRepository.demSoHoaDonChoGiaoHang();
    }

    @Override
    public Integer demSoHoaDonThanhCong() {
        return thongKeRepository.demSoHoaDonThanhCong();
    }

    @Override
    public Integer demSoHoaDonDaHuy() {
        return thongKeRepository.demSoHoaDonDaHuy();
    }

    @Override
    public Integer demSoHoaDonTraHang() {
        return thongKeRepository.demSoHoaDonTraHang();
    }

    @Override
    public List<Object[]> SanPhamBanChay() {
        return thongKeRepository.SanPhamBanChay();
    }

//    @Override
//    public List<TopSanPham> listSanPhamBanChay() {
//        List<TopSanPham> sanPhamBanChayResponseList = thongKeRepository.sanPhamBanChay();
//        List<TopSanPham> listSanPhamBanChay = new ArrayList<>();
//
//        for (TopSanPham sp : sanPhamBanChayResponseList) {
//            BigDecimal doanhSo;
//            if (sp.getDonGiaKhiGiam() != null && sp.getDonGiaKhiGiam() != 0) {
//                // Nếu có giá giảm giá, tính toán bằng giá giảm giá
//                doanhSo = new BigDecimal(sp.getDonGiaKhiGiam()).multiply(new BigDecimal(sp.getSoLuongDaBan()));
//            } else {
//                // Nếu không có giảm giá, tính toán bằng giá gốc
//                doanhSo = new BigDecimal(sp.getPrice()).multiply(new BigDecimal(sp.getSoLuongDaBan()));
//            }
//
//            TopSanPham sanPhamBanChayResponse = new TopSanPham(
//                    sp.getImage(),
//                    sp.getProductName(),
//                    sp.getPrice(),
//                    sp.getDonGiaKhiGiam(),
//                    sp.getSoLuongDaBan(),
//                    doanhSo);
//            listSanPhamBanChay.add(sanPhamBanChayResponse);
//        }
//        return listSanPhamBanChay;
//    }

}
