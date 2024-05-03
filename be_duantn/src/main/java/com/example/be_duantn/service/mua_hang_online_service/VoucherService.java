package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.VouCherRespon;

import java.util.List;

public interface VoucherService {

    // load voucher lên trang checkout
    List<VouCherRespon> loadVouCher();
}
