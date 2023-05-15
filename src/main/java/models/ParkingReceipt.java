package models;

import java.time.LocalDateTime;

public class ParkingReceipt {
    private int receiptNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Number fees;

    private static int currentReceiptNumber = 0;

    public ParkingReceipt(LocalDateTime startTime, LocalDateTime endTime, Number fees){
        this.startTime = startTime;
        this.endTime = endTime;
        this.fees = fees;
        this.receiptNumber = getIncrementedReceiptNumber();
    }

    private int getIncrementedReceiptNumber(){
        currentReceiptNumber =  NumberGeneratorUtil.getIncrementedNumber(currentReceiptNumber);
        return currentReceiptNumber;
    }

    @Override
    public String toString() {
        return "ParkingReceipt{" +
                "receiptNumber=" + receiptNumber +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", fees=" + fees +
                '}';
    }

    public Number getFees() {
        return fees;
    }
}
