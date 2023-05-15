package models;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingTime {
    private long days;
    private long hours;
    private long minutes;

    public ParkingTime(LocalDateTime startTime,LocalDateTime endTime){
        if(startTime != null && endTime !=null){

            // Calculate the duration between the two LocalDateTime objects
            Duration duration = Duration.between(startTime, endTime);

            // Extract the days, hours, and minutes from the duration
            days = duration.toDays();
            hours = duration.toHours() % 24;
            minutes = duration.toMinutes() % 60;

        }
    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }
}
