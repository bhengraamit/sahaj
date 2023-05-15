package feemodels;

import models.ParkingTime;
import models.VehicleType;

public abstract class AbstractFeeModel implements FeeModel {

    public Number calculateCost(VehicleType vehicleType, ParkingTime parkingTime) throws Exception {
        Object costModel = getCostModel(vehicleType);
        if(costModel == null){
            throw new Exception("Cost model doesn't exist for "+vehicleType);
        }
        return calculateCostImpl(vehicleType,parkingTime);
    }

    protected abstract Object getCostModel(VehicleType vehicleType);

    protected abstract Number calculateCostImpl(VehicleType vehicleType, ParkingTime parkingTime);
}
