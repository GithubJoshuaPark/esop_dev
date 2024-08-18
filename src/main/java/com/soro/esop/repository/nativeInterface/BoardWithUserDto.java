package com.soro.esop.repository.nativeInterface;

public interface BoardWithUserDto {
    Long getId();
    String getTitle();
    String getContent();
    Long getWriter();
    String getUsername();
    Boolean getEnabled();
}
