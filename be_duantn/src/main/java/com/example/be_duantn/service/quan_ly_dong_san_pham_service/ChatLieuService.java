package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.entity.ChatLieu;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatLieuService {
    // Hiển thị danh sách chất liệu
    Page<ChatLieuRespon> GetAllChatlieu(Integer page);

    // Findby chất liệu theo id
    Optional<ChatLieuRespon> FindByChatLieuID(UUID id);

    // Thêm chất liệu
    ChatLieu AddChatLieu(ChatLieuRequest chatlieu);

    // Update chất liệu theo id
    ChatLieu UpdateChatlieu(ChatLieuRequest chatLieuRequest);

    // Chuyển trạng thái chất liệu
    ChatLieu ChuyenTrangThai(UUID id, Integer trangThaiMoi);

}
