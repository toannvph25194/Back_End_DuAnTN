package com.example.be_duantn.service.mua_hang_online_service.Impl;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.VouCherRespon;
import com.example.be_duantn.repository.mua_hang_oneline_repository.VouCherRepository;
import com.example.be_duantn.service.mua_hang_online_service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VouCherServiceImpl implements VoucherService {

    @Autowired
    VouCherRepository vouCherRepository;

    @Override
    public List<VouCherRespon> loadVouCher() {
        return vouCherRepository.loadVouCher();
    }
}
