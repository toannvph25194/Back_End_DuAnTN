package com.example.be_duantn.enums;

public enum QuyenEnum {
    ADMIN(0),

    NHANVIEN(1);

    private final int value;

    QuyenEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
