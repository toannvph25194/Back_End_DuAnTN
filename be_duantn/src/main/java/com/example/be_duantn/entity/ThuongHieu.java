package com.example.be_duantn.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "thuonghieu")
public class ThuongHieu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID idthuonghieu;

    @Column(name = "tenthuonghieu")
    private String tenthuonghieu;

    @Column(name = "mota")
    private String mota;

    @Column(name = "trangthai")
    private Integer trangthai;

    @OneToMany(mappedBy = "thuonghieu", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<SanPham> sanpham;
}
