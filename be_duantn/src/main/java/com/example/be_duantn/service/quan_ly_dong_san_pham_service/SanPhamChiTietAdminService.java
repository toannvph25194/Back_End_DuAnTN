package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamChiTietAdminRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamChiTietRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamChiTietAdminRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamChiTietManThemHoaDonRespon;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.SanPhamChiTiet;

import java.util.List;
import java.util.UUID;

public interface SanPhamChiTietAdminService {
    public List<SanPhamChiTietAdminRespon> getSanPhamCT(UUID IdSP);
    // Thêm sản phẩm CT
    SanPhamChiTiet addSanPhamCT(SanPhamChiTietAdminRequest sanPhamChiTietAdminRequest);
    //updatetrangthai
    SanPhamChiTiet updateCTSP( UUID idspct, Integer trangthai);
    //updatesoluong
    SanPhamChiTiet updateCTSPsl( UUID idspct, Integer soluongton);
    //detail sản phẩm bằng id sp mà sửa hoá đơn
    SanPhamChiTietManThemHoaDonRespon finByIdSP(UUID IdSP);

}
