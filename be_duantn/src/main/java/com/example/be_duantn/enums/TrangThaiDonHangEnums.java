package com.example.be_duantn.enums;

public enum TrangThaiDonHangEnums {
    CHO_XAC_NHAN(1),

    XAC_NHAN(2),

    CHO_GIAO_HANG(3),

    GIAO_HANG(4),

    HOAN_THANH(5),

    DA_HUY(6),

    TRAN_HANG(7);

    private final int value;

    TrangThaiDonHangEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
