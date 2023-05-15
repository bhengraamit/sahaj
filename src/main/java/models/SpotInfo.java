package models;

import models.VehicleType;

import java.util.LinkedList;

public class SpotInfo {
    private int maxNumberOfSpots;
    private int currentSpotNumber;
    private LinkedList<Integer> freeSpots;

    public SpotInfo(int maxNumberOfSpots){
        this.maxNumberOfSpots = maxNumberOfSpots;
        this.currentSpotNumber = 0;
        this.freeSpots = new LinkedList<>();
    }

    public Integer getAvailableSpot(){
        if(currentSpotNumber < maxNumberOfSpots){
            return ++currentSpotNumber;
        }else{
            if(!freeSpots.isEmpty()){
                return freeSpots.poll();
            }
        }
        return null;
    }

    public void freeSpot(int spotNumber){
        freeSpots.add(spotNumber);
    }

}
