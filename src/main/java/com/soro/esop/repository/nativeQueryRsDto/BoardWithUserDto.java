package com.soro.esop.repository.nativeQueryRsDto;

public interface BoardWithUserDto {
    Long getId();
    String getTitle();
    String getContent();
    Long getWriter();
    String getUsername();
    Boolean getEnabled();
}
