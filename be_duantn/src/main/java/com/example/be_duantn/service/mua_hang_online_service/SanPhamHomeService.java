package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamGiamGiaRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamRespon;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SanPhamHomeService {

    // getALl sp home phân trang
    Page<SanPhamRespon> getAllSPHome(Integer page);

    // getALl sp nam nữ home phân trang
    Page<SanPhamRespon> getAllSPNamNuHome(Integer page, Integer theloai);

    // getALl sp new home
    List<SanPhamRespon> getAllSPNewHome();

    // getALl 6 sp giảm giá home
    List<SanPhamGiamGiaRespon> getAllSPGGHome();
}
