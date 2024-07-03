package com.example.be_duantn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "lichsuthaotac")
public class LichSuTaoTac {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID idhdct;

    @Column(name = "nguoithaotac")
    private String nguoithaotac;

    @Column(name = "ghichu")
    private String ghichu;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao = new Date();

    @Column(name = "trangthai")
    private Integer trangthai;

    @ManyToOne
    @JoinColumn(name = "idhd")
    @JsonBackReference
    HoaDon hoadon;
}
