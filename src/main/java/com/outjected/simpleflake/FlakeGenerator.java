package com.outjected.simpleflake;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class FlakeGenerator {

  private static final long EPOCH = 1288834974657L;
  private static final long MAX_SIGNED_LONG = 2199023255551L;
  private static final long MAX_RANDOM_BOUND = 4194305;

  public static SimpleFlake generate() {

    long current = System.currentTimeMillis();

    if (current < EPOCH || current > MAX_SIGNED_LONG) {
      // The current time cannot be less than the EPOCH
      throw new RuntimeException("Invalid System Clock was " + new Date(current));
    }

    final long timestamp = current - EPOCH;

    long shiftedTimeStamp = timestamp << 22;

    long random = ThreadLocalRandom.current().nextLong(MAX_RANDOM_BOUND);

    long shiftedResult = shiftedTimeStamp | random;

    return new SimpleFlake(shiftedResult);
  }
}
