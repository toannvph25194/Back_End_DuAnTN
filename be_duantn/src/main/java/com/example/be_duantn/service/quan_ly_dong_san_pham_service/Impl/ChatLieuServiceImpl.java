package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ChatLieuRespon;
import com.example.be_duantn.entity.ChatLieu;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.ChatLieuRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {
    @Autowired
    ChatLieuRepository chatLieuRepository;

    @Override
    public Page<ChatLieuRespon> GetAllChatlieu(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(page, 10);
            return chatLieuRepository.GetAllChatlieu(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Optional<ChatLieuRespon> FindByChatLieuID(UUID id) {
        return chatLieuRepository.FindByChatLieuID(id);
    }

    @Override
    public ChatLieu AddChatLieu(ChatLieuRequest chatlieu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {

            ChatLieu chatLieu = new ChatLieu();
            chatLieu.setTenchatlieu(chatlieu.getTenchatlieu());
            chatLieu.setMota(chatlieu.getMota());
            chatLieu.setTrangthai(chatlieu.getTrangthai());

            return chatLieuRepository.save(chatLieu);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public ChatLieu UpdateChatlieu(ChatLieuRequest chatLieuRequest) {
        ChatLieu chatLieu = chatLieuRepository.findById(chatLieuRequest.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chất liệu với Id : " + chatLieuRequest.getId()));

        chatLieu.setTenchatlieu(chatLieuRequest.getTenchatlieu());
        chatLieu.setMota(chatLieuRequest.getMota());
        chatLieu.setTrangthai(chatLieuRequest.getTrangthai());
        return chatLieuRepository.save(chatLieu);
    }

    @Override
    public ChatLieu ChuyenTrangThai(UUID id, Integer trangThaiMoi) {
        Optional<ChatLieu> chatLieu = chatLieuRepository.findById(id);
        if (chatLieu.isPresent()) {
            ChatLieu chatLieu1 = chatLieu.get();
            chatLieu1.setTrangthai(trangThaiMoi);
            return chatLieuRepository.save(chatLieu1);
        } else {
            throw new IllegalArgumentException("Không tìm thấy chất liệu với Id : " + id);
        }
    }

    private boolean hasPermission(Collection<? extends GrantedAuthority> authorities, String... requiredRoles) {
        // Kiểm tra xem người dùng có ít nhất một trong các quyền cần thiết hay không
        for (String requiredRole : requiredRoles) {
            if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals(requiredRole))) {
                return true;
            }
        }
        return false;
    }
}
