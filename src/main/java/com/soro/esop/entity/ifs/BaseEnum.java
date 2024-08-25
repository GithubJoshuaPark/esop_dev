package com.soro.esop.entity.ifs;

import com.fasterxml.jackson.annotation.JsonValue;
import com.soro.esop.entity.base.Name;
import com.soro.esop.entity.enums.Yn;

public interface BaseEnum {

  public default String getTitle() {
      return this.getClass().getSimpleName().replaceAll("([a-z])([A-Z]+)", "$1-$2").toUpperCase();
  }

  @JsonValue
  public String getValue();

  public Name getName();

  public int ordinal();

  public default String getDesc() {
      return null;
  }

  public default String getRemark1() {
      return null;
  }

  public default String getRemark2() {
      return null;
  }

  public default String getRemark3() {
      return null;
  }

  public default Yn getUseYn() {
      return Yn.Y;
  }

  public default Yn getEnumYn() {
      return Yn.Y;
  }
}