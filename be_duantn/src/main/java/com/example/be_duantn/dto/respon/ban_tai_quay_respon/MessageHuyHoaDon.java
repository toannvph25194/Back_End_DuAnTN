package com.example.be_duantn.dto.respon.ban_tai_quay_respon;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageHuyHoaDon {
    private String message;
}
