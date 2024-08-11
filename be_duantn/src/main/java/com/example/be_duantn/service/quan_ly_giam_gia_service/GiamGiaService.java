package com.example.be_duantn.service.quan_ly_giam_gia_service;

import com.example.be_duantn.dto.request.quan_ly_giam_gia_request.MaGiamGiaRequest;
import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.GiamGiaRespon;
import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.SanPhamGiamGiaRespon;
import com.example.be_duantn.entity.GiamGia;
import com.example.be_duantn.entity.SanPham;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface GiamGiaService {
    // Hiển thị danh sách giảm giá
    Page<GiamGiaRespon> GetAllGiamGia(Integer page);

    // Lọc theo ma
    Page<GiamGiaRespon> TimKiemTheoMa(Integer pageNumber, Integer pageSize, String magiamgia);

    // lọc khoảng ngày
    Page<GiamGiaRespon> TimKiemTheoKhoangNgay(Integer pageNumber, Integer pageSize, Date startDate, Date endDate);

    // Thêm mã giảm giá
    GiamGia AddGiamGia(MaGiamGiaRequest giamGiaRequest);

    // Findby giảm giá theo id
    Optional<GiamGia> FindByGiamGiaID(UUID id);

    // Hiển thị danh sách giảm giá
    Page<SanPhamGiamGiaRespon> GetAllSanPhmaTheoId(Integer page, UUID id);

    // Hiển thị danh sách giảm giá san phẩm
    Page<SanPhamGiamGiaRespon> GetAllSanPhamGiamGiaThem(Integer page);

    SanPham ThemSanPhamGiamGia(UUID idsp, UUID idgg);

    SanPham XoaSanPhamGiamGia(UUID idsp);

    GiamGia UpdateTrangThaiGiamGia(UUID idgg);

    GiamGia UpdateGiamGia(MaGiamGiaRequest giamGiaRequest, UUID idgg);

    // Hiển thị danh sách sản phẩm đã được giảm giá
    Page<SanPhamGiamGiaRespon> GetAllSanPhamGiamGia(Integer page);
}

