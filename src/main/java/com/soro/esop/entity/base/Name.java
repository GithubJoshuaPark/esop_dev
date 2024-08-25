package com.soro.esop.entity.base;

import java.io.Serializable;
import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable public class Name implements Serializable {

    @Column
    private String ko;

    @Column
    private String en;

    @Column
    private String zh;

    @Column
    private String ja;

    public String getLocalName(Locale locale) {
        if (Locale.KOREAN.equals(locale)) return this.ko;
        if (Locale.ENGLISH.equals(locale)) return this.en;
        if (Locale.CHINESE.equals(locale)) return this.zh;
        if (Locale.JAPANESE.equals(locale)) return this.ja;
        return this.ko;
    }
}