package com.soro.esop.entity.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable public class Modif implements Serializable, Comparable<Modif> {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime dt;

    @Column
    private String id;

    @Transient
    private String nm;

    @Transient
    @JsonIgnore
    private Name name;

    @Transient
    @JsonIgnore
    @Builder.Default
    private Locale locale = Locale.KOREAN;

    public String getNm() {
        if (ObjectUtils.isNotEmpty(this.name) && ObjectUtils.isNotEmpty(this.name.getLocalName(this.locale))) {
            return this.name.getLocalName(this.locale);
        }
        return this.nm;
    }

    public Modif setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public Modif update(Modif modif) {
        if (ObjectUtils.isNotEmpty(modif) && modif.isNotEmpty()) {
            return modif;
        }
        return this;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return ObjectUtils.isEmpty(this.dt) && ObjectUtils.isEmpty(this.id);
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    @Override
    public int compareTo(Modif o) {
        return ObjectUtils.compare(this.getDt(), o.getDt());
    }
}
