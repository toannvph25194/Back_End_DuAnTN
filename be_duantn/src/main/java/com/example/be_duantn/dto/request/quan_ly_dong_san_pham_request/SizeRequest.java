package com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SizeRequest {
    private UUID id;

//    @NotBlank(message = "Tên danh mục không được để trống")

    private String tensize;

    //    @NotBlank(message = "Mô tả không được để trống")
    private String mota;

    //    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangthai;
}
