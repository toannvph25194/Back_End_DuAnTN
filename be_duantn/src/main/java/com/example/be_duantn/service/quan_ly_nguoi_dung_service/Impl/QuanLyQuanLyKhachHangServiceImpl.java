package com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.KhachHangRequest;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.KhachHangRespon;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.MessageKhachHangRespon;
import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.repository.quan_ly_nguoi_dung_repository.QuanLyDiaChiKhachHangRepository;
import com.example.be_duantn.repository.quan_ly_nguoi_dung_repository.QuanLyKhachHangRepository;
import com.example.be_duantn.service.quan_ly_nguoi_dung_service.QuanLyKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuanLyQuanLyKhachHangServiceImpl implements QuanLyKhachHangService {
    @Autowired
    QuanLyKhachHangRepository quanLyKhachHangRepository;

    @Autowired
    private QuanLyDiaChiKhachHangRepository diaChiRepository;

    @Override
    public Page<KhachHangRespon> LoadAllKhachHang(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return quanLyKhachHangRepository.LoadAllKhachHang(pageable);
    }

    @Override
    public MessageKhachHangRespon ThemMoiKhachHang(KhachHangRequest request) {
        KhachHang khachHang = new KhachHang();
        khachHang.setIdkh(UUID.randomUUID());
        khachHang.setMakh(request.getMakhachhang());
        khachHang.setHovatenkh(request.getHovatenkh());
        khachHang.setGioitinh(request.getGioitinh());
        khachHang.setNgaysinh(request.getNgaysinh());
        khachHang.setTaikhoan(request.getTaikhoan());
        khachHang.setMatkhau(request.getMatkhau());
        khachHang.setSodienthoai(request.getSodienthoai());
        khachHang.setEmail(request.getEmail());
        khachHang.setImage(request.getImage());
        khachHang.setMota(request.getMota());
        khachHang.setTrangthai(request.getTrangthai());
        khachHang = quanLyKhachHangRepository.save(khachHang);

        Optional<DiaChi> diachikh = diaChiRepository.findByKhachhangAndTrangthai(khachHang, 1);
        if(diachikh.isPresent()){
            DiaChi diaChi = new DiaChi();
            diaChi.setDiachichitiet(request.getDiachichitiet());
            diaChi.setPhuongxa(request.getPhuongxa());
            diaChi.setQuanhuyen(request.getQuanhuyen());
            diaChi.setTinhthanh(request.getTinhthanh());
            diaChi.setQuocgia("Việt Nam");
            diaChi.setGhichu("Tạo thêm địa chỉ cho khách hàng");
            diaChi.setTrangthai(2);
            diaChi.setKhachhang(khachHang);
            diaChiRepository.save(diaChi);
            return MessageKhachHangRespon.builder().message("Tạo thêm địa chỉ cho khách hàng thành công !").build();
        }else{
            // Tạo và lưu địa chỉ với trạng thái là 1
            DiaChi diaChi = new DiaChi();
            diaChi.setDiachichitiet(request.getDiachichitiet());
            diaChi.setPhuongxa(request.getPhuongxa());
            diaChi.setQuanhuyen(request.getQuanhuyen());
            diaChi.setTinhthanh(request.getTinhthanh());
            diaChi.setQuocgia("Việt Nam");
            diaChi.setGhichu("Tạo mới địa chỉ cho khách hàng");
            diaChi.setTrangthai(1);
            diaChi.setKhachhang(khachHang);
            diaChiRepository.save(diaChi);
            return MessageKhachHangRespon.builder().message("Tạo mới địa chỉ cho khách hàng thành công !").build();
        }
    }

    @Override
    public MessageKhachHangRespon UpdateKhachHang(KhachHangRequest updatedRequest) {
        Optional<KhachHang> khfin = quanLyKhachHangRepository.findById(updatedRequest.getId());
        if (khfin.isPresent()) {
            KhachHang khachHang = khfin.get();

            // Cập nhật thông tin khách hàng
            khachHang.setMakh(updatedRequest.getMakhachhang());
            khachHang.setImage(updatedRequest.getImage());
            khachHang.setHovatenkh(updatedRequest.getHovatenkh());
            khachHang.setGioitinh(updatedRequest.getGioitinh());
            khachHang.setEmail(updatedRequest.getEmail());
            khachHang.setSodienthoai(updatedRequest.getSodienthoai());
            khachHang.setNgaysinh(updatedRequest.getNgaysinh());
            khachHang.setMota(updatedRequest.getMota());
            khachHang.setTaikhoan(updatedRequest.getTaikhoan());
            khachHang.setMatkhau(updatedRequest.getMatkhau());
            khachHang.setTrangthai(updatedRequest.getTrangthai());
            quanLyKhachHangRepository.save(khachHang);

            // Tìm địa chỉ có trạng thái bằng 1
            Optional<DiaChi> diaChiOptional = diaChiRepository.findByKhachhangAndTrangthai(khachHang, 1);

            if (diaChiOptional.isPresent()) {
                DiaChi diaChi = diaChiOptional.get();

                // Cập nhật thông tin địa chỉ
                diaChi.setDiachichitiet(updatedRequest.getDiachichitiet());
                diaChi.setPhuongxa(updatedRequest.getPhuongxa());
                diaChi.setQuanhuyen(updatedRequest.getQuanhuyen());
                diaChi.setTinhthanh(updatedRequest.getTinhthanh());
                diaChi.setQuocgia("Việt Nam");
                diaChi.setGhichu("Cập nhật ghi chú khách hàng");
                diaChi.setNgaycapnhat(new Date(System.currentTimeMillis()));
                // Lưu lại thông tin đã cập nhật
                diaChiRepository.save(diaChi);
                return MessageKhachHangRespon.builder().message("Cập nhập địa chỉ cho khách hàng thành công !").build();
            } else {
                // Tạo và lưu địa chỉ với trạng thái là 1
                DiaChi diaChi = new DiaChi();
                diaChi.setKhachhang(khachHang);
                diaChi.setDiachichitiet(updatedRequest.getDiachichitiet());
                diaChi.setPhuongxa(updatedRequest.getPhuongxa());
                diaChi.setQuanhuyen(updatedRequest.getQuanhuyen());
                diaChi.setTinhthanh(updatedRequest.getTinhthanh());
                diaChi.setQuocgia("Việt Nam");
                diaChi.setGhichu("Cập nhật tạo mới địa chỉ cho khách hàng");
                diaChi.setTrangthai(1);
                diaChiRepository.save(diaChi);
                return MessageKhachHangRespon.builder().message("Cập nhật Tạo mới địa chỉ cho khách hàng thành công !").build();
            }
        } else {
            return MessageKhachHangRespon.builder().message("Không tìm thấy thông tin khách hàng !").build();
        }
    }

    @Override
    public MessageKhachHangRespon UpdateTrangThaiKhachHang(UUID id, Integer trangThai) {
        Optional<KhachHang> khfin = quanLyKhachHangRepository.findById(id);

        if (khfin.isPresent()) {
            KhachHang khachHang = khfin.get();
            khachHang.setTrangthai(trangThai);
            quanLyKhachHangRepository.save(khachHang);
            return MessageKhachHangRespon.builder().message("Cập nhật trạng thái khách hàng thành công !").build();
        } else {
            return MessageKhachHangRespon.builder().message("Không tìm thấy thông tin khách hàng !").build();
        }
    }

    @Override
    public Page<KhachHangRespon> LocKhachHangTheoNhieuTieuChi(Integer page, String hovatenkh, String sodienthoai, String email) {
        Pageable pageable = PageRequest.of(page, 10);
        return quanLyKhachHangRepository.LocKhachHangTheoNhieuTieuChi(pageable, hovatenkh, sodienthoai, email);
    }

    @Override
    public Page<KhachHangRespon> LocKhachHangTheoTrangThai(Integer page, Integer trangthai) {
        Pageable pageable = PageRequest.of(page, 10);
        return quanLyKhachHangRepository.LocKhachHangTheoTrangThai(pageable, trangthai);
    }

}
