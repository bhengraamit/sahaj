package parkinglot;

import feemodels.FeeModel;
import models.*;
import parkinglot.AbstractParkingLot;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;

public class GenericParkingLot extends AbstractParkingLot {
    private Map<VehicleType, SpotInfo> vehicleTypeSpots;
    private FeeModel feeModel;

    public GenericParkingLot(Map<VehicleType,SpotInfo> vehicleTypeSpots, FeeModel feeModel){
        this.vehicleTypeSpots = vehicleTypeSpots;
        this.feeModel = feeModel;
    }


    @Override
    public ParkingTicket parkVehicle(VehicleType vehicleType, LocalDateTime startTime) throws Exception {
        SpotInfo spotInfo =  vehicleTypeSpots.get(vehicleType);
        if(spotInfo == null){
            throw new Exception("This vehicle type is not supported");
        }
        Integer availableSpot  = spotInfo.getAvailableSpot();
        if(availableSpot != null){
            //generate ticket
            return new ParkingTicket(availableSpot, startTime);
        }
        //No space available
        return null;
    }



    @Override
    public ParkingReceipt unparkVehicle(VehicleType vehicleType, ParkingTicket parkingTicket, LocalDateTime endTime) throws Exception {
        SpotInfo spotInfo =  vehicleTypeSpots.get(vehicleType);
        if(spotInfo == null){
            throw new Exception("This vehicle type is not supported");
        }
        // calculate fees
        Number cost = feeModel.calculateCost(vehicleType,new ParkingTime(parkingTicket.getStartTime(),endTime));

        spotInfo.freeSpot(parkingTicket.getSpotNumber());

        //generate receipt
        return new ParkingReceipt(parkingTicket.getStartTime(), endTime,cost);
    }
}
