package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.KhachHangDiaChiRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadDiaChiTaiQuayRespon;
import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.DiaChiTaiQuayRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.KhachHangBanTaiQuayRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.KhachHangBanTaiQuayService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class KhachHangBanTaiQuayServiceImpl implements KhachHangBanTaiQuayService {

    @Autowired
    KhachHangBanTaiQuayRepository repository;

    @Autowired
    DiaChiTaiQuayRepository diaChiTaiQuayRepository;





    @Override
    @Transactional
    public Page<LoadDiaChiTaiQuayRespon> getKhachHangByTrangThai(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LoadDiaChiTaiQuayRespon> khachHangPage = repository.findAllWithTrangThai(pageable);

        return khachHangPage;
    }

    @Override
    public LoadDiaChiTaiQuayRespon finByIdKh(UUID Idkh) {
        return repository.finByidKh(Idkh);
    }


    @Override
    public Page<LoadDiaChiTaiQuayRespon> LocTenKHBanTaiQuay( String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.LocTenKHBanTaiQuay(keyword, pageable);
    }


    @Transactional
    public KhachHang addKhachHang(KhachHang khachHang, DiaChi diaChi) {
        // Lưu đối tượng DiaChi vào cơ sở dữ liệu và nhận lại đối tượng đã lưu (bao gồm ID)
        DiaChi savedDiaChi = diaChiTaiQuayRepository.save(diaChi);
        diaChi.setKhachhang(khachHang);
        // Đảm bảo rằng danh sách diachi của khách hàng đã được khởi tạo
        if (khachHang.getDiachi() == null) {
            khachHang.setDiachi(new ArrayList<>());
        }
        // Thiết lập địa chỉ cho khách hàng
        khachHang.getDiachi().add(savedDiaChi);
        // Lưu khách hàng vào cơ sở dữ liệu
        KhachHang savedKhachHang = repository.save(khachHang);
      //  sendConfirmationEmail(savedKhachHang.getEmail(), savedKhachHang.getTaikhoan(), savedKhachHang.getMatkhau());
        return savedKhachHang;
    }


    @Override
    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean phoneExists(String phone) {
        return repository.existsBySodienthoai(phone);
    }


}
