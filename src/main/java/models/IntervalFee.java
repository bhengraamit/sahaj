package models;

import java.util.concurrent.TimeUnit;

public class IntervalFee{
    int startTimeInclusive;
    int endTimeExclusive;
    Number fee;
    TimeUnit timeUnit;

    public IntervalFee(int startTimeInclusive,int endTimeExclusive, Number fee,TimeUnit timeUnit){
        this.startTimeInclusive = startTimeInclusive;
        this.endTimeExclusive = endTimeExclusive;
        this.fee = fee;
        this.timeUnit = timeUnit;
    }

    public int getStartTimeInclusive() {
        return startTimeInclusive;
    }

    public int getEndTimeExclusive() {
        return endTimeExclusive;
    }

    public Number getFee() {
        return fee;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}