package com.soro.esop.entity.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.soro.esop.entity.base.Name;
import com.soro.esop.entity.ifs.BaseEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(using = BuyStatusDesirializer.class)
public enum C01Status implements BaseEnum {

    $ ("@", Name.builder().ko("WDAR").en("").zh("").ja("").build()),               // ordinal: 0
    W ("W", Name.builder().ko("대기").en("Wait").zh("대기").ja("대기").build()),     // ordinal: 1
    I ("D", Name.builder().ko("요청").en("Draft").zh("요청").ja("요청").build()),    // ordinal: 2
    C ("A", Name.builder().ko("승인").en("Approve").zh("승인").ja("승인").build()),  // ordinal: 3
    F ("R", Name.builder().ko("반려").en("Reject").zh("반려").ja("반려").build());   // ordinal: 4

    private String value;
    private Name name;

    public static C01Status of(String value) {
        for (C01Status buyStatus : C01Status.values()) {
            if (buyStatus.getValue().equals(value)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static C01Status ofOrdinal(int ordinal) {
        for (C01Status buyStatus : C01Status.values()) {
            if (buyStatus.ordinal() == ordinal) {
                return buyStatus;
            }
        }
        return null;
    }

    public static C01Status ofValue(String value) {
        for (C01Status buyStatus : C01Status.values()) {
            if (buyStatus.getValue().equals(value)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static C01Status ofName(String name) {
        for (C01Status buyStatus : C01Status.values()) {
            if (buyStatus.getName().getKo().equals(name)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static C01Status ofName(Name name) {
        for (C01Status buyStatus : C01Status.values()) {
            if (buyStatus.getName().equals(name)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static C01Status ofName(String ko, String en, String zh, String ja) {
        for (C01Status buyStatus : C01Status.values()) {
            if (buyStatus.getName().getKo().equals(ko) && buyStatus.getName().getEn().equals(en) && buyStatus.getName().getZh().equals(zh) && buyStatus.getName().getJa().equals(ja)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static C01Status ofName(String ko, String en, String zh) {
        for (C01Status buyStatus : C01Status.values()) {
            if (buyStatus.getName().getKo().equals(ko) && buyStatus.getName().getEn().equals(en) && buyStatus.getName().getZh().equals(zh)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static C01Status ofName(String ko, String en) {
        for (C01Status buyStatus : C01Status.values()) {
            if (buyStatus.getName().getKo().equals(ko) && buyStatus.getName().getEn().equals(en)) {
                return buyStatus;
            }
        }
        return null;
    }
}