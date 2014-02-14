package com.outjected.simpleflake;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class FlakeGenerator {

  private static final long EPOCH = 1288834974657L;
  private static final long MAX_SIGNED_LONG = 2199023255551L;

  private static final int TIME_SHIFT = 22;
  private static final int RANDOM_SHIFT = 42;

  private long lastTimestamp = 0;
  private Set<Long> recentRandoms = new HashSet<>(5000);

  /**
   * Generates a Twitter Snowflake compatible id utilizing randomness for the right most 22 bits and
   * the Twitter standard EPOCH
   * 
   * @return long
   */
  public long generate() {

    long currentTimestamp = System.currentTimeMillis();

    while (lastTimestamp > currentTimestamp) {
      // Clock is running backwards so wait until it isn't
      currentTimestamp = System.currentTimeMillis();
    }

    if (currentTimestamp < EPOCH || currentTimestamp > MAX_SIGNED_LONG) {
      // The current time cannot be less than the EPOCH
      throw new RuntimeException("Invalid System Clock was " + new Date(currentTimestamp));
    }

    final long customTimestamp = currentTimestamp - EPOCH;

    final long shiftedTimestamp = customTimestamp << TIME_SHIFT;

    long random = nextRandomPart();

    if (lastTimestamp != currentTimestamp) {
      // timestamp has advanced so reset it and clear the previous cache
      lastTimestamp = currentTimestamp;
      recentRandoms.clear();
    } else {
      // Same timestamp as previous keep generating randoms till new is found
      while (recentRandoms.contains(random)) {
        random = nextRandomPart();
      }
    }
    recentRandoms.add(random);
    return shiftedTimestamp | random;
  }

  /**
   * Generates a Twitter Snowflake compatible id utilizing randomness for the right most 22 bits and
   * the Twitter standard EPOCH
   * 
   * @return byte[]
   */
  public byte[] generateBytes() {
    return ByteBuffer.allocate(8).putLong(generate()).array();
  }

  private long nextRandomPart() {
    return ThreadLocalRandom.current().nextLong() >>> RANDOM_SHIFT;
  }
}
