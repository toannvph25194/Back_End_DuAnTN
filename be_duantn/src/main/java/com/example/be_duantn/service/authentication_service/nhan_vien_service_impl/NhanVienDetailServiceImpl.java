package com.example.be_duantn.service.authentication_service.nhan_vien_service_impl;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.jwt.nhan_vien_jwt.NhanVienCustomDetails;
import com.example.be_duantn.repository.authentication_repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class NhanVienDetailServiceImpl implements UserDetailsService {

    @Autowired
    private NhanVienRepository phatTuRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<NhanVien> phatTu = phatTuRepository.findByTaikhoan(username);
        return new NhanVienCustomDetails(phatTu.get());
    }
}
