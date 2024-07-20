package com.example.be_duantn.controller.quan_ly_dong_san_pham_controller;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ChatLieuRequest;
import com.example.be_duantn.entity.ChatLieu;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl.ChatLieuServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/chatlieu")
public class ChatLieuController {
    @Autowired
    ChatLieuServiceImpl chatLieuService;

    // ToDo hiển thị danh sách chất liệu
    @GetMapping("/hien-thi")
    public ResponseEntity<?> getAllChatLieu(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(chatLieuService.GetAllChatlieu(page));
    }

    // ToDo findby chất liệu theo id
    @GetMapping("/find-by")
    public ResponseEntity<?> FindByIdChatLieu(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(chatLieuService.FindByChatLieuID(id));
    }

    // ToDo thêm mới chất liệu
    @PostMapping("/add-chatlieu")
    public ResponseEntity<?> AddChatLieu(@Valid @RequestBody ChatLieuRequest chatLieuRequest) {
        return ResponseEntity.ok(chatLieuService.AddChatLieu(chatLieuRequest));
    }

    // ToDo update chất liệu theo id
    @PutMapping("/update-chatlieu")
    public ResponseEntity<ChatLieu> CapNhatchatlieu(@RequestBody ChatLieuRequest chatLieuRequest) {
        return ResponseEntity.ok(chatLieuService.UpdateChatlieu(chatLieuRequest));
    }

    // ToDo chuyển trạng thái chất liệu theo id
    @PutMapping("/update-trang-thai")
    public ResponseEntity<ChatLieu> ChuyenTrangThai(@RequestParam("id") UUID id, @RequestParam Integer trangthai) {
        try {
            ChatLieu chatLieu = chatLieuService.ChuyenTrangThai(id, trangthai);
            if (chatLieu != null) {
                return ResponseEntity.ok(chatLieu);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ToDo hiển thị danh sách chất liệu load combobox
    @GetMapping("/hien-thi-combobox")
    public ResponseEntity<?> getAllThuongHieuLoadcombobox() {
        return ResponseEntity.ok(chatLieuService.GetAllChatlieuLoadCombobox());
    }
}
