package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHDCTTaiQuay;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageHDCTBanTaiQuay;
import com.example.be_duantn.entity.*;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.HoaDonBanTaiQuayRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.HoaDonCTBanTaiQuayRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.SanPhamCTBanTaiQuayRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.HoaDonCTBanTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HoaDonCTBanTaiQuayServiceImpl implements HoaDonCTBanTaiQuayService {

    @Autowired
    HoaDonCTBanTaiQuayRepository hoaDonCTBanTaiQuayRepository;

    @Autowired
    SanPhamCTBanTaiQuayRepository sanPhamCTBanTaiQuayRepository;

    @Autowired
    HoaDonBanTaiQuayRepository hoaDonBanTaiQuayRepository;

    @Override
    public List<LoadHDCTTaiQuay> loadHDCTBanTaiQuay(UUID idhd) {
        return hoaDonCTBanTaiQuayRepository.loadHDCTBanTaiQuay(idhd);
    }

    @Override
    public MessageHDCTBanTaiQuay ThemSPVaoHDCTBanTaiQuay(Principal principal, UUID idhd, UUID idspct, int soluong, Double dongiakhigiam) {
        // Lấy taikhoan nhân viên khi đăng nhập
        String taikhoan = principal.getName();
        // Tìm thông tin hd và spct
        HoaDon hd = hoaDonBanTaiQuayRepository.findById(idhd).orElse(null);
        SanPhamChiTiet spct = sanPhamCTBanTaiQuayRepository.findById(idspct).orElse(null);

        // Kiểm tra có quyền truy cập không
        if(taikhoan != null){
            // Thông báo nếu không tìm thấy đơn hàng của khách hàng
            if(hd == null){
                return MessageHDCTBanTaiQuay.builder().message("Không tìm thấy hóa đơn của khách hàng !").build();
            }
            // Xử lý hóa đơn chi tiết
            HoaDonChiTiet hdct = hoaDonCTBanTaiQuayRepository.findByHoadon_IdhoadonAndSanphamchitiet_Idspct(idhd, idspct);
            // Nếu hdct đã có thì cộng số lượng hdct
            if(hdct != null){
                hdct.setSoluong(hdct.getSoluong() + soluong);
                hdct.setDongiakhigiam(dongiakhigiam);
                hdct.setNgaycapnhat(new Date(System.currentTimeMillis()));
                hoaDonCTBanTaiQuayRepository.save(hdct);
                // Xử lý sản phẩm chi tiết, trừ số lượng tồn sản phẩm chi tiết
                if(spct != null){
                    spct.setSoluongton(spct.getSoluongton() - soluong);
                    sanPhamCTBanTaiQuayRepository.save(spct);
                }
                return MessageHDCTBanTaiQuay.builder().message("Cập nhật hdct thành công !").build();
            } else {
                // Nếu chưa có hdct thì tạo hdct
                HoaDonChiTiet hdctMoi = new HoaDonChiTiet();
                hdctMoi.setIdhdct(UUID.randomUUID());
                hdctMoi.setHoadon(hd);
                hdctMoi.setSanphamchitiet(spct);
                hdctMoi.setSoluong(soluong);
                hdctMoi.setDongia(spct.getSanpham().getGiaban());
                hdctMoi.setDongiakhigiam(dongiakhigiam);
                hdctMoi.setNgaytao(hdctMoi.getNgaytao());
                hdctMoi.setGhichu("Nhân viên thêm sản phẩm vào hoá đơn chi tiết cho khách");
                hdctMoi.setTrangthai(2);
                hdctMoi.setLoaihoadonchitiet(2);
                hoaDonCTBanTaiQuayRepository.save(hdctMoi);

                // update số lượng tồn trong spct
                spct.setSoluongton(spct.getSoluongton() - soluong);
                sanPhamCTBanTaiQuayRepository.save(spct);
                return MessageHDCTBanTaiQuay.builder().message("Thêm sản phẩm vào hdct thành công !").build();
            }
        }else {
            return MessageHDCTBanTaiQuay.builder().message("Không tìm thấy thông tin nhân viên !").build();
        }
    }

    @Override
    public MessageHDCTBanTaiQuay UpdateHDCTBanTaiQuay(Principal principal, UUID idhdct, Integer soluong) {
        String taikhoan = principal.getName();
        if(taikhoan != null){
            HoaDonChiTiet hdct = hoaDonCTBanTaiQuayRepository.findById(idhdct).orElse(null);
            if (hdct != null){
                // Số lượng hiện tại trong hóa đơn chi tiết
                Integer soluongcu = hdct.getSoluong();
                // Số lượng tồn hiện tại của spct
                Integer soluongton = hdct.getSanphamchitiet().getSoluongton();

                // xử lý cập nhật số lượng trong hdct. Lấy số lượng mới - số lượng cũ
                Integer soluongthaydoi = soluong - soluongcu;
                hdct.setSoluong(soluong);

                // xử lý cập nhật lại số lượng tồn trong spct. Lấy số lượng tồn - số lượng thay đổi
                Integer soluongtonmoi = soluongton - soluongthaydoi;
                hdct.getSanphamchitiet().setSoluongton((soluongtonmoi));
                hdct.setNgaycapnhat(new Date(System.currentTimeMillis()));
                hdct.setGhichu("Nhân viên cập nhật sản phẩm cho khách hàng");
                hoaDonCTBanTaiQuayRepository.save(hdct);
            }else {
                return MessageHDCTBanTaiQuay.builder().message("K tìm thấy idhdct bán hàng tại quầy!").build();
            }
            return MessageHDCTBanTaiQuay.builder().message("Cập nhật số lượng hdct thành công bán hàng tại quầy!").build();
        } else {
            return MessageHDCTBanTaiQuay.builder().message("Không tìm thấy thông tin nhân viên !").build();
        }
    }

    @Override
    public MessageHDCTBanTaiQuay DeleteHDCTBanTaiQuay(Principal principal, UUID idhdct) {
        String taikhoan = principal.getName();
        if (taikhoan != null){
            HoaDonChiTiet hdct = hoaDonCTBanTaiQuayRepository.findById(idhdct).orElse(null);
            if (hdct != null){
                hoaDonCTBanTaiQuayRepository.delete(hdct);
                //Cập nhật lại số lượng tồn trong spct
                SanPhamChiTiet spct = hdct.getSanphamchitiet();
                if (spct != null){
                    spct.setSoluongton(hdct.getSoluong() + spct.getSoluongton());
                    sanPhamCTBanTaiQuayRepository.save(spct);
                }else {
                    return MessageHDCTBanTaiQuay.builder().message("Không tìm thấy idspct trong hdct bán hàng tại quầy").build();
                }
                return MessageHDCTBanTaiQuay.builder().message("Xóa hdct bán hàng tại quầy thành công !").build();
            }else {
                return MessageHDCTBanTaiQuay.builder().message("Không tìm thấy idhdct bán hàng tại quầy !").build();
            }
        }else {
            return MessageHDCTBanTaiQuay.builder().message("Không tìm thấy thông tin nhân viên !").build();
        }
    }
}
