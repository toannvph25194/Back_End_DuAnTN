package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MessageRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamShopRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamAdminRespon;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface SanPhamService {
    // Hiển thị sản phẩm
    Page<SanPhamAdminRespon> HienThiSanPhamPhanTrang(Integer page);

    // Thêm sản phẩm
    SanPham addSanPham(SanPhamRequest sanpham);

    // tìm theo id
    public List<SanPhamAdminRespon> getSanPhamTheoID(UUID IdSP);

    // Lọctheo tensp
    Page<SanPhamAdminRespon> locTenSPShop(Integer pageNumber, Integer pageSize, String tensp);

    // Lọc theo nhiều tiêu chí. tendanhmuc, tenmausac, tensize
    Page<SanPhamAdminRespon> locSPShopNTC(Integer pageNumber, Integer pageSize, String tendanhmuc, String tenmausac, String tensize, String tenchatlieu, String tenxuatxu, String tenthuonghieu);

    MessageRequest udatesanpham(SanPhamRequest sanpham, UUID IdSp);
    //updatetrangthai
    SanPham updatesp(UUID idsp, Integer trangthai);
}
