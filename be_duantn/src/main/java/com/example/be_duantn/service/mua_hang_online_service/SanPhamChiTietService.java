package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;

import java.util.List;
import java.util.UUID;

public interface SanPhamChiTietService {

    // load top 4 sp mới nhất
    List<SanPhamRespon> loadSPCTNew();

    // detail sản phẩm bằng id sp
    SanPhamChiTietRespon finByIdSP(UUID idsp);

    // load list img theo idsp
    List<ListImageSPRespon> loadListImageSP(UUID idsp);

    // load màu sắc theo idsp
    List<LoadMauSacByIdSP> loadMauSacByIdSP(UUID idsp);

    // load size theo idsp
    List<LoadSizeByIdSP> loadSizeByIdSP(UUID idsp);

    // finByMauSac theo idsp và idsize
    List<FinMauSacByIdSPAndIdsize> finMauSacByIdSPAndIdSize(UUID idsp, UUID idsize);

    // finSize theo idsp và idmausac
    List<FinSizeByIdSPAndIdMauSac> finSizeByIdSPAndIdMauSac(UUID idsp, UUID idmausac);

    // tính tổng tất cả SoLuongTon spct theo idsp
    TongSoLuongTonSPCTRespon tinhTongSoLuongTonSPCT(UUID idsp);

    // fin idspct and soluonton theo idsp, idmausac và idsize
    FinByIdSPCTAndSoLuongTon finByIdSPCTAndSoLuongTon(UUID idsp, UUID idms, UUID idsize);
}
