package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ThuongHieuRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ThuongHieuRespon;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.ThuongHieu;
import com.example.be_duantn.entity.XuatXu;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThuongHieuService {
    public Page<ThuongHieuRespon> getThuongHIeu(Integer page);

    // Thêm thương hiệu
    ThuongHieu addThuongHieu(ThuongHieuRequest thuonghieu);

    Optional<ThuongHieuRespon> getthuonghieuById(UUID id);

    ThuongHieu updateThuonghieu(UUID id, ThuongHieuRequest thuongHieuRequest);

    void deleteThuonghieu(UUID id);

    ThuongHieu chuyenTrangThai(UUID id, Integer trangThaiMoi);

    // Hiển thị danh sách thương hiệu load combobox
    List<ThuongHieuRespon> getThuongHieuLoadComboBox();
}
