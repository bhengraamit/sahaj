package feemodels;

import feemodels.AbstractFeeModel;
import models.ParkingTime;
import models.VehicleType;

import java.util.Map;

public class MallFeeModel extends AbstractFeeModel {
    // per hour rate
    Map<VehicleType,Number> vehicleTypeCostMap;

    public MallFeeModel(Map<VehicleType,Number> vehicleTypeCostMap){
        this.vehicleTypeCostMap = vehicleTypeCostMap;
    }

    protected Number calculateCostImpl(VehicleType vehicleType, ParkingTime parkingTime){
        Number perHourCost = getCostModel(vehicleType);
        int perHourCostInt = perHourCost.intValue();
        return (perHourCostInt * parkingTime.getHours()) + (parkingTime.getMinutes() > 0 ? perHourCostInt : 0);
    }


    public Number getCostModel(VehicleType vehicleType){
        return vehicleTypeCostMap.get(vehicleType);
    }

}
