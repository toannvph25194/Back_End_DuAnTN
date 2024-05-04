package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.entity.ChatLieu;
import com.example.be_duantn.entity.SanPham;

import java.util.List;

public interface ChatLieuService {
    // hiển thị all chất liệu
    public List<ChatLieuRespon> getChatLieu();
    // Thêm chất liệu
    ChatLieu addChatLieu(ChatLieuRequest chatlieu);


}
