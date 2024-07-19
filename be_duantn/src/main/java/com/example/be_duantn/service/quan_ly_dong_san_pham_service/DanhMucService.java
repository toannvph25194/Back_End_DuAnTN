package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.XuatXu;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DanhMucService {
    public Page<DanhMucRespon> getDanhMuc(Integer page);
    // Thêm danh mục
    DanhMuc addDanhMuc(DanhMucRequest danhmuc);
    Optional<DanhMucRespon> getdanhmucById(UUID id);
    DanhMuc updateDanhmuc(UUID id, DanhMucRequest danhMucRequest);
    void deleteDanhmuc(UUID id);
    DanhMuc chuyenTrangThai(UUID id, Integer trangThaiMoi);
}
