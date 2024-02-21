package com.jdktomcat.demo.snowflake.generate.util;

public class SnowflakeIdWorker {

    private final long twepoch = 1272643200000L;

    /**
     * Number of digits occupied by machine id
     */
    private final long workerIdBits = 5L;

    /**
     * Number of digits occupied by data id
     */
    private final long datacenterIdBits = 5L;

    /**
     * The maximum machine id supported, the result is 31 (this shift algorithm can quickly calculate the maximum decimal number that several binary numbers can represent)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * Maximum data id supported, result is 31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * Number of digits of sequence in id
     */
    private final long sequenceBits = 12L;

    /**
     * Machine ID moved 12 bits to the left
     */
    private final long workerIdShift = sequenceBits;

    /**
     * Move data id 17 bits to the left (12 + 5)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * Time cut 22 bits left (5 + 5 + 12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * Mask for generating sequence, here is 4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * Working machine ID (0-31)
     */
    private long workerId;

    /**
     * Data center ID (0-31)
     */
    private long datacenterId;

    /**
     * Sequence in milliseconds (0-4095)
     */
    private long sequence = 0L;

    /**
     * Last ID generated by
     */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================

    /**
     * Constructor
     *
     * @param workerId     Work ID (0-31)
     * @param datacenterId Data center ID (0-31)
     */
    private SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized static long createTableId() {
        SnowflakeIdWorker snowflakeIdFactory = new SnowflakeIdWorker(0, 1);
        return snowflakeIdFactory.nextId();
    }
    // ==============================Methods==========================================

    /**
     * Get the next ID (this method is thread safe)
     *
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        long timestamp = timeGen();
        //If the current time is less than the time stamp generated by the last ID, an exception should be thrown when the system clock goes back
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //If generated at the same time, sequence in milliseconds
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //Sequence overflow in MS
            if (sequence == 0) {
                //Block to next MS, get new timestamp
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        //Last ID generated by
        lastTimestamp = timestamp;
        //Shift and combine by or operation to form a 64 bit ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * Blocks to the next millisecond until a new timestamp is obtained
     *
     * @param lastTimestamp Last ID generated by
     * @return Current timestamp
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Returns the current time in milliseconds
     *
     * @return Current time (MS)
     */
    protected synchronized long timeGen() {
        return System.nanoTime();
    }
}
