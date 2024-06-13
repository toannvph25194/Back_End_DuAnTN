package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadGHCTTaiQuay;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageGHCTBanTaiQuay;
import com.example.be_duantn.entity.GioHang;
import com.example.be_duantn.entity.GioHangChiTiet;
import com.example.be_duantn.entity.SanPhamChiTiet;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.GioHangBanTaiQuayRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.GiohangChiTietBanTaiQuayRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.SanPhamCTBanTaiQuayRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.GioHangCTBanTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class GioHangCTBanTaiQuayServiceImpl implements GioHangCTBanTaiQuayService {
    @Autowired
    GiohangChiTietBanTaiQuayRepository giohangChiTietBanTaiQuayRepository;

    @Autowired
    GioHangBanTaiQuayRepository gioHangBanTaiQuayRepository;

    @Autowired
    SanPhamCTBanTaiQuayRepository sanPhamCTBanTaiQuayRepository;

    @Override
    public List<LoadGHCTTaiQuay> loadGHCTBanTaiQuay(UUID idgh) {
        return giohangChiTietBanTaiQuayRepository.loadGHCTBanTaiQuay(idgh);
    }

    @Override
    public MessageGHCTBanTaiQuay ThemSPVaoGHCTBanTaiQuay(Principal principal, UUID idgh, UUID idspct, int soluong, Double dongiakhigiam) {
        // Lấy taikhoan nhân viên khi đăng nhập
        String taikhoan = principal.getName();
        // Tìm thông tin gh và spct
        GioHang gh = gioHangBanTaiQuayRepository.findById(idgh).orElse(null);
        SanPhamChiTiet spct = sanPhamCTBanTaiQuayRepository.findById(idspct).orElse(null);

        // Kiểm tra có quyền truy cập không
        if(taikhoan != null){
            // Thông báo nếu không tìm thấy giỏ hàng
            if(gh == null){
                return MessageGHCTBanTaiQuay.builder().message("Không tìm thấy giỏ hàng !").build();
            }
            // Xử lý giỏ hàng chi tiết
            GioHangChiTiet ghct = giohangChiTietBanTaiQuayRepository.findByGiohang_IdghAndSanphamchitiet_Idspct(idgh, idspct);
            // Nếu ghct đã có thì cộng số lượng ghct
            if(ghct != null){
                ghct.setSoluong(ghct.getSoluong() + soluong);
                ghct.setDongiakhigiam(dongiakhigiam);
                ghct.setNgaycapnhat(new Date(System.currentTimeMillis()));
                giohangChiTietBanTaiQuayRepository.save(ghct);
                // Xử lý sản phẩm chi tiết, trừ số lượng tồn sản phẩm chi tiết
                if(spct != null){
                    spct.setSoluongton(spct.getSoluongton() - soluong);
                    sanPhamCTBanTaiQuayRepository.save(spct);
                }
                return MessageGHCTBanTaiQuay.builder().message("Cập nhật ghct thành công !").build();
            } else {
                // Nếu chưa có ghct thì tạo ghct
                GioHangChiTiet ghctMoi = new GioHangChiTiet();
                ghctMoi.setIdghct(UUID.randomUUID());
                // Tạo mới giỏ hàng để lấy idgh
                GioHang ghMoi = new GioHang();
                ghMoi.setIdgh(idgh);
                ghctMoi.setGiohang(ghMoi);
                // Tạo mới spct để lấy idspct
                SanPhamChiTiet spctMoi = new SanPhamChiTiet();
                spctMoi.setIdspct(idspct);
                ghctMoi.setSanphamchitiet(spctMoi);
                ghctMoi.setSoluong(soluong);
                ghctMoi.setDongia(spct.getSanpham().getGiaban());
                ghctMoi.setDongiakhigiam(dongiakhigiam);
                ghctMoi.setNgaytao(ghctMoi.getNgaytao());
                ghctMoi.setGhichu("Nhân viên thêm sản phẩm vào giỏ hàng chi tiết cho khách");
                ghctMoi.setTrangthai(1);
                giohangChiTietBanTaiQuayRepository.save(ghctMoi);

                // update số lượng tồn trong spct
                spct.setSoluongton(spct.getSoluongton() - soluong);
                sanPhamCTBanTaiQuayRepository.save(spct);
                return MessageGHCTBanTaiQuay.builder().message("Thêm sản phẩm vào ghct thành công !").build();
            }
        }else {
            return MessageGHCTBanTaiQuay.builder().message("Không tìm thấy thông tin nhân viên !").build();
        }
    }


    @Override
     public MessageGHCTBanTaiQuay UpdateGioHangCTBanTaiQuay(Principal principal, UUID idghct, Integer soluong){
        String taikhoan = principal.getName();
        if(taikhoan != null){
            GioHangChiTiet ghct = giohangChiTietBanTaiQuayRepository.findById(idghct).orElse(null);
            if (ghct != null){
                // Số lượng hiện tại trong giỏ hàng
                Integer soluongcu = ghct.getSoluong();
                // Số lượng tồn hiện tại của spct
                Integer soluongton = ghct.getSanphamchitiet().getSoluongton();

                // xử lý cập nhật số lượng trong giỏ. Lấy số lượng mới - số lượng cũ
                Integer soluongthaydoi = soluong - soluongcu;
                ghct.setSoluong(soluong);

                // xử lý cập nhật lại số lượng tồn trong spct. Lấy số lượng tồn - số lượng thay đổi
                Integer soluongtonmoi = soluongton - soluongthaydoi;
                ghct.getSanphamchitiet().setSoluongton((soluongtonmoi));
                ghct.setNgaycapnhat(new Date(System.currentTimeMillis()));
                ghct.setGhichu("Nhân viên cập nhật sản phẩm cho khách hàng");
                giohangChiTietBanTaiQuayRepository.save(ghct);
            }else {
                return MessageGHCTBanTaiQuay.builder().message("K tìm thấy idghct bán hàng tại quầy!").build();
            }
            return MessageGHCTBanTaiQuay.builder().message("Cập nhật số lượng ghct thành công bán hàng tại quầy!").build();
        } else {
            return MessageGHCTBanTaiQuay.builder().message("Không tìm thấy thông tin nhân viên !").build();
        }
    }

    @Override
    public MessageGHCTBanTaiQuay DeleteGioHangCTBanTaiQuay(Principal principal, UUID idghct){
        String taikhoan = principal.getName();
        if (taikhoan != null){
            GioHangChiTiet ghct = giohangChiTietBanTaiQuayRepository.findById(idghct).orElse(null);
            if (ghct != null){
                giohangChiTietBanTaiQuayRepository.delete(ghct);
                //Cập nhật lại số lượng tồn trong spct
                SanPhamChiTiet spct = ghct.getSanphamchitiet();
                if (spct != null){
                    spct.setSoluongton(ghct.getSoluong() + spct.getSoluongton());
                    sanPhamCTBanTaiQuayRepository.save(spct);
                }else {
                    return MessageGHCTBanTaiQuay.builder().message("Không tìm thất idspct trong ghct bán hàng tại quầy").build();
                }
                return MessageGHCTBanTaiQuay.builder().message("Xóa ghct bán hàng tại quầy thành công !").build();
            }else {
                return MessageGHCTBanTaiQuay.builder().message("Không tìm thấy idghct bán hàng tại quầy !").build();
            }
        }else {
            return MessageGHCTBanTaiQuay.builder().message("Không tìm thấy thông tin nhân viên !").build();
        }
    }
}
