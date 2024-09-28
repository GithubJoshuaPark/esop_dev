package com.soro.esop.entity.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.soro.esop.entity.base.Name;
import com.soro.esop.entity.ifs.BaseEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(using = YnDesirializer.class)
public enum Yn implements BaseEnum {
    $ ("@", Name.builder().ko("YN").en("").zh("").ja("").build()),   // ordinal: 0
    N ("N", Name.builder().ko("N").en("N").zh("N").ja("N").build()), // ordinal: 1
    Y ("Y", Name.builder().ko("Y").en("Y").zh("Y").ja("Y").build()); // ordinal: 2

    private String value;
    private Name name;

    public static Yn of(String value) {
        for (Yn yn : Yn.values()) {
            if (yn.getValue().equals(value)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofOrdinal(int ordinal) {
        for (Yn yn : Yn.values()) {
            if (yn.ordinal() == ordinal) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofBoolean(boolean value) {
        return value ? Y : N;
    }

    public static Yn ofValue(String value) {
        for (Yn yn : Yn.values()) {
            if (yn.getValue().equals(value)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofName(String name) {
        for (Yn yn : Yn.values()) {
            if (yn.getName().getKo().equals(name)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofName(Name name) {
        for (Yn yn : Yn.values()) {
            if (yn.getName().equals(name)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofYn(boolean value) {
        return value ? Y : N;
    }

    public static Yn ofYn(String value) {
        for (Yn yn : Yn.values()) {
            if (yn.getValue().equals(value)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofYn(Name name) {
        for (Yn yn : Yn.values()) {
            if (yn.getName().equals(name)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofYn(String ko, String en, String zh, String ja) {
        for (Yn yn : Yn.values()) {
            if (yn.getName().getKo().equals(ko) && yn.getName().getEn().equals(en) && yn.getName().getZh().equals(zh) && yn.getName().getJa().equals(ja)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofYn(String ko, String en, String zh) {
        for (Yn yn : Yn.values()) {
            if (yn.getName().getKo().equals(ko) && yn.getName().getEn().equals(en) && yn.getName().getZh().equals(zh)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofYn(String ko, String en) {
        for (Yn yn : Yn.values()) {
            if (yn.getName().getKo().equals(ko) && yn.getName().getEn().equals(en)) {
                return yn;
            }
        }
        return null;
    }

    public static Yn ofYnOrdinal(int ordinal) {
        for (Yn yn : Yn.values()) {
            if (yn.ordinal() == ordinal) {
                return yn;
            }
        }
        return null;
    }


}