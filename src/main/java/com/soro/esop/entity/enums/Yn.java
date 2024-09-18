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

    public static Yn ofBoolean(boolean value) {
        if (value) return Yn.Y;
        return Yn.N;
    }
}