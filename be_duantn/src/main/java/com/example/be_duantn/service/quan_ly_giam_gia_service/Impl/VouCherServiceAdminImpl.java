package com.example.be_duantn.service.quan_ly_giam_gia_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_giam_gia_request.VouCherAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.VouCherAdminRespon;
import com.example.be_duantn.entity.VouCher;
import com.example.be_duantn.repository.quan_ly_giam_gia_repository.VouCherAdminRepository;
import com.example.be_duantn.service.quan_ly_giam_gia_service.VouCherServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class VouCherServiceAdminImpl implements VouCherServiceAdmin {
    @Autowired
    VouCherAdminRepository vouCherAdminRepository;

    @Override
    public Page<VouCherAdminRespon> GetAllVoucher(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return vouCherAdminRepository.GetAllVoucher(pageable);
    }

    @Override
    public Page<VouCherAdminRespon> TimKiemTheoMa(Integer pageNumber, Integer pageSize, String mavoucher) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return vouCherAdminRepository.TimKiemTheoMa(pageable, mavoucher);
    }

    @Override
    public Page<VouCherAdminRespon> TimKiemTheoKhoangNgay(Integer pageNumber, Integer pageSize, Date startDate, Date endDate) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return vouCherAdminRepository.TimKiemTheoKhoangNgay(pageable, startDate, endDate);
    }

    @Override
    public Optional<VouCherAdminRespon> FindByVoucherID(UUID id) {
        return vouCherAdminRepository.GetAllVouCherTheoID(id);
    }

    @Override
    public VouCher AddVoucher(VouCherAdminRequest vouCherAdminRequest) {
        VouCher vouCher = new VouCher();
        vouCher.setMavoucher(vouCherAdminRequest.getMavoucher());
        vouCher.setTenvoucher(vouCherAdminRequest.getTenvoucher());
        vouCher.setNgaybatdau(vouCherAdminRequest.getNgaybatdau());
        vouCher.setNgayketthuc(vouCherAdminRequest.getNgayketthuc());
        vouCher.setHinhthucgiam(vouCherAdminRequest.getHinhthucgiam());
        vouCher.setGiatrigiam(vouCherAdminRequest.getGiatrigiam());
        vouCher.setDieukientoithieuhoadon(vouCherAdminRequest.getDieukientoithieuhoadon());
        vouCher.setLoaivoucher(1);
        vouCher.setSoluongma(vouCherAdminRequest.getSoluongma());
        vouCher.setGhichu(vouCherAdminRequest.getGhichu());
        vouCher.setTrangthai(1);
        return vouCherAdminRepository.save(vouCher);
    }

    @Override
    public VouCher UpdateTrangThai(UUID id) {
        VouCher vouCher = vouCherAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy voucher với Id : " + id));

        // Thay đổi trạng thái
        if (vouCher.getTrangthai() == 1) {
            vouCher.setTrangthai(2);
        } else if (vouCher.getTrangthai() == 2) {
            vouCher.setTrangthai(1);
        } else {
            throw new RuntimeException("Trạng thái voucher không hợp lệ.");
        }

        // Lưu voucher với trạng thái mới
        return vouCherAdminRepository.save(vouCher);
    }

    @Override
    public VouCher UpdateVoucher(VouCherAdminRequest vouCherAdminRequest, UUID id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Tìm giảm giá theo id
        VouCher vouCher = vouCherAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy voucher với Id : " + id));
        vouCher.setTenvoucher(vouCherAdminRequest.getTenvoucher());
        vouCher.setNgaybatdau(vouCherAdminRequest.getNgaybatdau());
        vouCher.setNgayketthuc(vouCherAdminRequest.getNgayketthuc());
        vouCher.setNgaycapnhat(timestamp);
        vouCher.setHinhthucgiam(vouCherAdminRequest.getHinhthucgiam());
        vouCher.setGiatrigiam(vouCherAdminRequest.getGiatrigiam());
        vouCher.setDieukientoithieuhoadon(vouCherAdminRequest.getDieukientoithieuhoadon());
        vouCher.setSoluongma(vouCherAdminRequest.getSoluongma());
        vouCher.setGhichu(vouCherAdminRequest.getGhichu());
        return vouCherAdminRepository.save(vouCher);
    }
}
