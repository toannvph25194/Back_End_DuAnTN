package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.entity.ChatLieu;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.entity.SanPham;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatLieuService {
    // hiển thị all chất liệu
    public Page<ChatLieuRespon> getChatLieu(Integer page);
    // Thêm chất liệu
    ChatLieu addChatLieu(ChatLieuRequest chatlieu);
    Optional<ChatLieuRespon> getdanhmucById(UUID id);
    ChatLieu updateChatlieu(UUID id, ChatLieuRequest chatLieuRequest);
    void deleteChatlieu(UUID id);
    ChatLieu chuyenTrangThai(UUID id, Integer trangThaiMoi);

}
