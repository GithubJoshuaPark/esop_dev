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
public enum BuyStatus implements BaseEnum {

    $ ("@", Name.builder().ko("WICF").en("").zh("").ja("").build()),                 // ordinal: 0
    W ("W", Name.builder().ko("대기").en("Wait").zh("대기").ja("대기").build()),     // ordinal: 1
    I ("I", Name.builder().ko("입력").en("Input").zh("입력").ja("입력").build()),    // ordinal: 2
    C ("C", Name.builder().ko("승인").en("Confirm").zh("승인").ja("승인").build()),  // ordinal: 3
    F ("F", Name.builder().ko("완료").en("Finished").zh("완료").ja("완료").build()); // ordinal: 4

    private String value;
    private Name name;

    public static BuyStatus of(String value) {
        for (BuyStatus buyStatus : BuyStatus.values()) {
            if (buyStatus.getValue().equals(value)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static BuyStatus ofOrdinal(int ordinal) {
        for (BuyStatus buyStatus : BuyStatus.values()) {
            if (buyStatus.ordinal() == ordinal) {
                return buyStatus;
            }
        }
        return null;
    }

    public static BuyStatus ofValue(String value) {
        for (BuyStatus buyStatus : BuyStatus.values()) {
            if (buyStatus.getValue().equals(value)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static BuyStatus ofName(String name) {
        for (BuyStatus buyStatus : BuyStatus.values()) {
            if (buyStatus.getName().getKo().equals(name)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static BuyStatus ofName(Name name) {
        for (BuyStatus buyStatus : BuyStatus.values()) {
            if (buyStatus.getName().equals(name)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static BuyStatus ofName(String ko, String en, String zh, String ja) {
        for (BuyStatus buyStatus : BuyStatus.values()) {
            if (buyStatus.getName().getKo().equals(ko) && buyStatus.getName().getEn().equals(en) && buyStatus.getName().getZh().equals(zh) && buyStatus.getName().getJa().equals(ja)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static BuyStatus ofName(String ko, String en, String zh) {
        for (BuyStatus buyStatus : BuyStatus.values()) {
            if (buyStatus.getName().getKo().equals(ko) && buyStatus.getName().getEn().equals(en) && buyStatus.getName().getZh().equals(zh)) {
                return buyStatus;
            }
        }
        return null;
    }

    public static BuyStatus ofName(String ko, String en) {
        for (BuyStatus buyStatus : BuyStatus.values()) {
            if (buyStatus.getName().getKo().equals(ko) && buyStatus.getName().getEn().equals(en)) {
                return buyStatus;
            }
        }
        return null;
    }
}