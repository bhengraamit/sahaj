package feemodels;

import models.ParkingTime;
import models.VehicleType;

public interface FeeModel {

    public Number calculateCost(VehicleType vehicleType, ParkingTime parkingTime);

}
