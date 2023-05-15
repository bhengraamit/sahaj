package feemodels;

import models.ParkingTime;
import models.VehicleType;

public abstract class AbstractFeeModel implements FeeModel {

    public Number calculateCost(VehicleType vehicleType, ParkingTime parkingTime){
        Object costModel = getCostModel(vehicleType);
        if(costModel == null){
            //TODO: throw exception
            return null;
        }
        return calculateCostImpl(vehicleType,parkingTime);
    }

    public abstract Object getCostModel(VehicleType vehicleType);

    protected abstract Number calculateCostImpl(VehicleType vehicleType, ParkingTime parkingTime);
}
