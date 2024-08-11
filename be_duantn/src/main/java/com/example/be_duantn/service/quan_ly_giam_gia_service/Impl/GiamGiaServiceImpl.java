package com.example.be_duantn.service.quan_ly_giam_gia_service.Impl;


import com.example.be_duantn.dto.request.quan_ly_giam_gia_request.MaGiamGiaRequest;
import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.GiamGiaRespon;
import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.SanPhamGiamGiaRespon;
import com.example.be_duantn.entity.GiamGia;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.SanPhamRepository;
import com.example.be_duantn.repository.quan_ly_giam_gia_repository.GiamGiaRepository;
import com.example.be_duantn.service.quan_ly_giam_gia_service.GiamGiaService;
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
public class GiamGiaServiceImpl implements GiamGiaService {
    @Autowired
    GiamGiaRepository giamGiaRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Override
    public Page<GiamGiaRespon> GetAllGiamGia(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return giamGiaRepository.GetAllMaGiamGia(pageable);
    }

    @Override
    public Page<GiamGiaRespon> TimKiemTheoMa(Integer pageNumber, Integer pageSize, String magiamgia) {
        // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return giamGiaRepository.TimKiemTheoMa(pageable, magiamgia);
    }

    @Override
    public Page<GiamGiaRespon> TimKiemTheoKhoangNgay(Integer pageNumber, Integer pageSize, Date startDate, Date endDate) {
        // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return giamGiaRepository.TimKiemTheoKhoangNgay(pageable, startDate, endDate);
    }

    @Override
    public GiamGia AddGiamGia(MaGiamGiaRequest giamGiaRequest) {
        GiamGia gg = new GiamGia();
        gg.setMagiamgia(giamGiaRequest.getMagiamgia());
        gg.setTengiamgia(giamGiaRequest.getTengiamgia());
        gg.setNgaybatdau(giamGiaRequest.getNgaybatdau());
        gg.setNgayketthuc(giamGiaRequest.getNgayketthuc());
        gg.setHinhthucgiam(giamGiaRequest.getHinhthucgiam());
        gg.setGiatrigiam(giamGiaRequest.getGiatrigiam());
        gg.setGhichu(giamGiaRequest.getGhichu());
        gg.setTrangthai(1);
        return giamGiaRepository.save(gg);
    }

    @Override
    public Optional<GiamGia> FindByGiamGiaID(UUID id) {
        return giamGiaRepository.findById(id);
    }

    @Override
    public Page<SanPhamGiamGiaRespon> GetAllSanPhmaTheoId(Integer page, UUID id) {
        Pageable pageable = PageRequest.of(page, 10);
        return giamGiaRepository.GetAllSanPhamTheoID(pageable, id);
    }

    @Override
    public Page<SanPhamGiamGiaRespon> GetAllSanPhamGiamGiaThem(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return giamGiaRepository.GetAllSanPhamGiamGiaThem(pageable);
    }

    @Override
    public SanPham ThemSanPhamGiamGia(UUID idsp, UUID idgg) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Tìm sản phẩm và giảm giá
        SanPham sanPham1 = sanPhamRepository.findById(idsp)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với Id : " + idsp));

        GiamGia giamGia = giamGiaRepository.findById(idgg)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảm giá với Id : " + idgg));

        // Tạo mới đối tượng giảm giá và thiết lập
        GiamGia giamGiaNew = new GiamGia();
        giamGiaNew.setIdgiamgia(idgg);
        sanPham1.setGiamgia(giamGiaNew);

        // Tính giá sau khi giảm
        if (giamGia.getHinhthucgiam() == 1) {
            // Giảm giá theo phần trăm
            double giaBan = sanPham1.getGiaban();
            double giaTru = giamGia.getGiatrigiam() / 100.0;
            sanPham1.setDongiakhigiam(giaBan - (giaBan * giaTru));
        } else {
            // Giảm giá cố định
            sanPham1.setDongiakhigiam(sanPham1.getGiaban() - giamGia.getGiatrigiam());
        }

        // Thiết lập ngày thêm giảm giá và lưu sản phẩm
        sanPham1.setNgaythemgiamgia(timestamp);
        return sanPhamRepository.save(sanPham1);
    }

    @Override
    public SanPham XoaSanPhamGiamGia(UUID idsp) {


        // Tìm sản phẩm theo ID
        SanPham sanPham1 = sanPhamRepository.findById(idsp)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với Id : " + idsp));

        // Xóa thông tin giảm giá khỏi sản phẩm
        sanPham1.setGiamgia(null);

        // Đặt giá sau khi giảm về giá gốc
        sanPham1.setDongiakhigiam(null);

        // Cập nhật ngày thêm giảm giá và lưu sản phẩm
        sanPham1.setNgaythemgiamgia(null);
        return sanPhamRepository.save(sanPham1);
    }

    @Override
    public GiamGia UpdateTrangThaiGiamGia(UUID idgg) {
        // Tìm giảm giá theo id
        GiamGia giamGia = giamGiaRepository.findById(idgg)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảm giá với Id : " + idgg));

        // Thay đổi trạng thái
        if (giamGia.getTrangthai() == 1) {
            giamGia.setTrangthai(2);
        } else if (giamGia.getTrangthai() == 2) {
            giamGia.setTrangthai(1);
        } else {
            throw new RuntimeException("Trạng thái giảm giá không hợp lệ.");
        }

        // Lưu giảm giá với trạng thái mới
        return giamGiaRepository.save(giamGia);
    }

    @Override
    public GiamGia UpdateGiamGia(MaGiamGiaRequest giamGiaRequest, UUID idgg) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Tìm giảm giá theo id
        GiamGia giamGia = giamGiaRepository.findById(idgg)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảm giá với Id : " + idgg));
        giamGia.setTengiamgia(giamGiaRequest.getTengiamgia());
        giamGia.setHinhthucgiam(giamGiaRequest.getHinhthucgiam());
        giamGia.setNgaycapnhat(timestamp);
        giamGia.setGiatrigiam(giamGiaRequest.getGiatrigiam());
        giamGia.setNgaybatdau(giamGiaRequest.getNgaybatdau());
        giamGia.setNgayketthuc(giamGiaRequest.getNgayketthuc());
        giamGia.setGhichu(giamGiaRequest.getGhichu());
        return giamGiaRepository.save(giamGia);

    }

    @Override
    public Page<SanPhamGiamGiaRespon> GetAllSanPhamGiamGia(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return giamGiaRepository.GetAllSanPhamGiamGia(pageable);
    }


}
