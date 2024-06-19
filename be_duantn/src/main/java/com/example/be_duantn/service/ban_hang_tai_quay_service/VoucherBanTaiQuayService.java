package com.example.be_duantn.service.ban_hang_tai_quay_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.VouCherRespon;

import java.util.List;
import java.util.UUID;

public interface VoucherBanTaiQuayService {

    List<VouCherRespon> loadVouCher();
    VouCherRespon loadVouCherTheoID(UUID id);

}
