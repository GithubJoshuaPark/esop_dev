package com.soro.esop.entity.base;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.soro.esop.entity.enums.Yn;

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
@Embeddable public class Use implements Serializable {

    @Column
    @Builder.Default
    private Yn yn = Yn.N;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate frDt;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate toDt;

    public boolean isUse(Use condition) {
        if (this.yn.equals(condition.getYn())) {
            if (this.yn.equals(Yn.Y)) {
                if (ObjectUtils.isNotEmpty(this.frDt)) {
                    LocalDate toDt = Optional.ofNullable(condition.getToDt()).orElse(LocalDate.MAX);
                    if(!toDt.isEqual(this.frDt) && !toDt.isAfter(this.frDt)) return false;
                }
                if (ObjectUtils.isNotEmpty(this.toDt)) {
                    LocalDate frDt = Optional.ofNullable(condition.getFrDt()).orElse(LocalDate.MIN);
                    if(!frDt.isEqual(this.toDt) && !frDt.isBefore(this.toDt)) return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isNotUse(Use condition) {
        return !this.isUse(condition);
    }

    @JsonIgnore
    public Yn getUseYn() {
        return Yn.ofBoolean(isUse(Use.builder().yn(Yn.Y).frDt(LocalDate.now()).toDt(LocalDate.now()).build()));
    }
}