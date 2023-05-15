package feemodels;

import feemodels.AbstractFeeModel;
import models.IntervalFee;
import models.ParkingTime;
import models.VehicleType;

import java.util.List;
import java.util.Map;

public class StadiumFeeModel extends AbstractFeeModel {
    //    Flat rate up to a few hours and then per-hour rate.
    private Map<VehicleType, List<IntervalFee>> vehicleTypeIntervalFeeMap;

    public StadiumFeeModel(Map<VehicleType,List<IntervalFee>> vehicleTypeIntervalFeeMap){
        this.vehicleTypeIntervalFeeMap = vehicleTypeIntervalFeeMap;
    }

    protected Number calculateCostImpl(VehicleType vehicleType, ParkingTime parkingTime){
        List<IntervalFee> intervalFeeList = getCostModel(vehicleType);
        boolean minutesGreaterThanZero = parkingTime.getMinutes() > 0;
        long totalHours = parkingTime.getHours() + (minutesGreaterThanZero ? 1 : 0);
        long totalFee = 0;
        for(IntervalFee intervalFee : intervalFeeList){
            totalFee += intervalFee.getEndTimeExclusive() != Integer.MAX_VALUE ? intervalFee.getFee().longValue() : (totalHours - intervalFee.getStartTimeInclusive()) * intervalFee.getFee().longValue();
            if(totalHours == intervalFee.getEndTimeExclusive() && minutesGreaterThanZero){
                break;
            }
            if(totalHours < intervalFee.getEndTimeExclusive()){
                break;
            }
        }
        return totalFee;
//        return IntervalFeeUtil.calculateIntervalFee(intervalFeeList,parkingTime);
    }

    public List<IntervalFee> getCostModel(VehicleType vehicleType){
        return this.vehicleTypeIntervalFeeMap.get(vehicleType);
    }

}

