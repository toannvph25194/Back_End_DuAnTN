package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.GioHangChiTietRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHangCTRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.TongSoTienRespon;
import com.example.be_duantn.entity.*;
import com.example.be_duantn.repository.mua_hang_oneline_repository.GioHangChiTietRepository;
import com.example.be_duantn.repository.mua_hang_oneline_repository.GioHangRepository;
import com.example.be_duantn.repository.mua_hang_oneline_repository.SanPhamChiTietRepository;
import com.example.be_duantn.service.mua_hang_online_service.GioHangChiTietService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;
    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    GioHangRepository gioHangRepository;

    @Override
    public List<GioHangChiTietRespon> loadGioHangChiTiet(UUID idgh) {
        return gioHangChiTietRepository.loadGioHangChiTiet(idgh);
    }

    @Override
    public MessageGioHangCTRespon addSPGioHangCT(UUID idgh, UUID idspct, Integer soluong, Double dongiakhigiam) {
        GioHangChiTiet ghct = gioHangChiTietRepository.findByGiohang_IdghAndSanphamchitiet_Idspct(idgh, idspct);
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(idspct).orElse(null);
        if (ghct != null){

            // Sản phẩm đã có trong ghct, cập nhật số lượng
            ghct.setSoluong(ghct.getSoluong() + soluong);
            ghct.setDongiakhigiam(dongiakhigiam);
            ghct.setNgaycapnhat(new Date(System.currentTimeMillis()));
            gioHangChiTietRepository.save(ghct);
            // Giảm số lượng tổn của spct
            if (spct != null){
                spct.setSoluongton(spct.getSoluongton() - soluong);
                sanPhamChiTietRepository.save(spct);
            }else {
                return MessageGioHangCTRespon.builder().message("K tìm thấy idspct !").build();
            }
            return MessageGioHangCTRespon.builder().message("Cập nhật thành công ghct !").build();

        }else {

            // Sản phẩm chưa có trong ghct thì thêm mới
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
            ghctMoi.setTrangthai(1);

            // add ghct mới
            gioHangChiTietRepository.save(ghctMoi);

            // update số lượng tồn trong spct
            spct.setSoluongton(spct.getSoluongton() - soluong);
            sanPhamChiTietRepository.save(spct);
            return MessageGioHangCTRespon.builder().message("Thêm mới thành công ghct !").build();
        }
    }

    @Override
    public MessageGioHangCTRespon updateGioHangCT(UUID idghct, Integer soluong) {
        GioHangChiTiet ghct = gioHangChiTietRepository.findById(idghct).orElse(null);
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
            gioHangChiTietRepository.save(ghct);
        }else {
            return MessageGioHangCTRespon.builder().message("K tìm thấy idghct !").build();
        }
        return MessageGioHangCTRespon.builder().message("Cập nhật số lượng ghct thành công !").build();
    }

    @Override
    public MessageGioHangCTRespon deleteGioHangCT(UUID idghct) {
        GioHangChiTiet ghct = gioHangChiTietRepository.findById(idghct).orElse(null);
        if (ghct != null){
            gioHangChiTietRepository.delete(ghct);

            // Cập nhật lại soluongton trong spct
            SanPhamChiTiet spct = ghct.getSanphamchitiet();
            if (spct != null){
                spct.setSoluongton(ghct.getSoluong() + spct.getSoluongton());
                sanPhamChiTietRepository.save(spct);
            }else {
                return MessageGioHangCTRespon.builder().message("K tìm thấy idspct trong ghct !").build();
            }

            return MessageGioHangCTRespon.builder().message("Xóa ghct thành công !").build();
        }else {
            return MessageGioHangCTRespon.builder().message("K tìm thấy idghct !").build();
        }
    }

    @Override
    public MessageGioHangCTRespon deleteAllGioHangCT(UUID idgh) {
        Optional<GioHang> gioHangOptional = gioHangRepository.findById(idgh);
        if (gioHangOptional.isPresent()){
            GioHang gh = gioHangOptional.get();

            // Lấy danh sách chi tiết giỏ hàng
            List<GioHangChiTiet> ghctList = gh.getGiohangchitiet();

            // Xóa tất cả chi tiết giỏ hàng
            for (GioHangChiTiet ghct : ghctList) {
                gioHangChiTietRepository.delete(ghct);

                SanPhamChiTiet spct = ghct.getSanphamchitiet();
                if (spct != null){
                    spct.setSoluongton(spct.getSoluongton() + ghct.getSoluong());
                    sanPhamChiTietRepository.save(spct);
                }else {
                    return MessageGioHangCTRespon.builder().message("K tìm thấy idspct trong ghct !").build();
                }
            }
            // Cập nhật lại giỏ hàng sau khi xóa
            // Đặt lại danh sách ghct thành rỗng
            gh.setGiohangchitiet(new ArrayList<>());
            gioHangRepository.save(gh);
            return MessageGioHangCTRespon.builder().message("Xóa tất cả ghct thành công !").build();
        }else {
            return MessageGioHangCTRespon.builder().message("K tìm thấy idgh !").build();
        }
    }

    @Override
    public TongSoTienRespon loadTongSoTienSP(UUID idgh) {
        return gioHangChiTietRepository.loadTongSoTienSP(idgh);
    }
}
