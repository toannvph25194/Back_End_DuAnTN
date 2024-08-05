package com.example.be_duantn.service.quan_ly_hoa_don_service;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadSPTaiQuayRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHangCTRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.LoadSPHoaDonChiTietRespon;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietAdminService {
    // hiển thị hoá đơn theo id
    public List<HoaDonChiTietRespon> finByIdHDCT(UUID IdHD);

    // Load sản phẩm phân trang lên bán hàng tại quầy
    Page<LoadSPHoaDonChiTietRespon> LoadSPSuaHoaDon(Integer page);

    // Lọc sản phẩm phân trang theo tên sản phẩm sửa hoá đơn
    Page<LoadSPHoaDonChiTietRespon> LocTenSPBanTaiQuay(Integer page, String tensp);

    // Lọc sản phẩm phân trang nhiều tiêu chí sửa háo đơn
    Page<LoadSPHoaDonChiTietRespon> LocSPNhieuTieuChiBanTaiQuay(Integer page, String tenmausac, String tensize, String tenchatlieu, String tendanhmuc, String tenthuonghieu, String tenxuatxu);

    // add sản phầm vào ghct
    MessageGioHangCTRespon addSPHDCT(UUID idhdct, UUID idspct, Integer soluong, Double dongiakhigiam, Principal principal);

    // update soluong HDCT theo idghct
    MessageGioHangCTRespon updateHDCT(UUID idHDCT, Integer soluong,UUID idhd ,Principal principal);

    // delete hdct theo idghct
    MessageGioHangCTRespon deleteHoaDonCT(UUID idghct,UUID idhd,Principal principal);
}
