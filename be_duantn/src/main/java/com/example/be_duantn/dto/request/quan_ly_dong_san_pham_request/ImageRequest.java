package com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request;

import com.example.be_duantn.entity.SanPham;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ImageRequest {

    private UUID idimage;

    private String tenimage;

    private String mota;

    private Integer trangthai;

    private UUID sanpham;

}
