package models;

import models.VehicleType;

import java.util.LinkedList;

/**
 * SpotInfo basically handles the resource management of spots.
 * It maintains a freeSpots list which keeps all the unparked spots eventually.
 */
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
