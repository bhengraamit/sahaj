package models;

import java.time.LocalDateTime;

public class ParkingTicket {
    private int ticketNumber;
    private int spotNumber;
    private LocalDateTime startTime;
    private static int currentTicketNumber;

    public ParkingTicket(int spotNumber, LocalDateTime startTime){
        this.spotNumber = spotNumber;
        this.startTime = startTime;
        this.ticketNumber = getIncrementedTicketNumber();
    }

    private int getIncrementedTicketNumber(){
        currentTicketNumber =  NumberGeneratorUtil.getIncrementedNumber(currentTicketNumber);
        return currentTicketNumber;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "ticketNumber=" + ticketNumber +
                ", spotNumber=" + spotNumber +
                ", startTime=" + startTime +
                '}';
    }
}
