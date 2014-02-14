package com.outjected.simpleflake;

import java.nio.ByteBuffer;


public class SimpleFlake {

  private final long data;

  public SimpleFlake(long data) {
    this.data = data;
  }

  public String toHexString() {
    return Long.toHexString(data);
  }

  public long toLong() {
    return data;
  }

  public byte[] toByteArray() {
    return ByteBuffer.allocate(8).putLong(data).array();
  }

  @Override
  public String toString() {
    return String.valueOf(data);
  }
}
