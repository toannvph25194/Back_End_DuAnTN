package com.example.be_duantn.dto.request.mua_hang_online_request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageTKRequest {
    private UUID idkh;
    private String image;
}
