package com.outjected.simpleflake;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FlakeGenerator {

  private static final long EPOCH = 1288834974657L;
  private static final long MAX_SIGNED_LONG = 2199023255551L;

  private long lastTimeStamp = 0;
  private Set<Long> currentRandoms = new HashSet<>(5000);
  private SecureRandom secureRandom = new SecureRandom();

  public long generateLong() {

    long currentTimeStamp = System.currentTimeMillis();

    while(lastTimeStamp > currentTimeStamp){
      //Clock is running backwards so wait until it isn't
      currentTimeStamp = System.currentTimeMillis();
    }

    if (currentTimeStamp < EPOCH || currentTimeStamp > MAX_SIGNED_LONG) {
      // The current time cannot be less than the EPOCH
      throw new RuntimeException("Invalid System Clock was " + new Date(currentTimeStamp));
    }

    final long customTimeStamp = currentTimeStamp - EPOCH;

    final long shiftedTimeStamp = customTimeStamp << 22;

    long random = nextRandom();

    if(lastTimeStamp != currentTimeStamp) {
        // Timestamp has advanced so reset it and clear the previous cache
        lastTimeStamp = currentTimeStamp;
        currentRandoms.clear();
    } else {
        // Same timestamp as previous keep generating randoms till new is found
        while(currentRandoms.contains(random)) {
            random = nextRandom();
        }
    }
    currentRandoms.add(random);
    return shiftedTimeStamp | random;
  }

    public SimpleFlake generateSimpleFlake(){
        return new SimpleFlake(generateLong());
    }

    private long nextRandom(){
        return secureRandom.nextLong() >>> 42;
    }
}
