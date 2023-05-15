package feemodels;

import models.IntervalFee;
import models.ParkingTime;
import models.VehicleType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AirportFeeModel extends AbstractFeeModel {

    private Map<VehicleType, List<IntervalFee>> vehicleTypeIntervalFeeMap;

    public AirportFeeModel(Map<VehicleType,List<IntervalFee>> vehicleTypeIntervalFeeMap){
        this.vehicleTypeIntervalFeeMap = vehicleTypeIntervalFeeMap;
    }

    protected Number calculateCostImpl(VehicleType vehicleType, ParkingTime parkingTime){
        List<IntervalFee> intervalFeeList = getCostModel(vehicleType);
        TimeUnit timeUnit = parkingTime.getDays() > 0 ? TimeUnit.DAYS : TimeUnit.HOURS;
        boolean minutesGreaterThanZero = parkingTime.getMinutes() > 0;
        long totalTime = 0;
        if(timeUnit == TimeUnit.DAYS){
            totalTime = parkingTime.getDays() + (parkingTime.getHours() > 0 ? 1 : 0);
        }else{
            totalTime = parkingTime.getHours() + (parkingTime.getMinutes() > 0 ? 1 : 0);;
        }
        long totalCost = 0;
        for (IntervalFee intervalFee : intervalFeeList){
            if(intervalFee.getTimeUnit() == timeUnit && intervalFee.getEndTimeExclusive() == totalTime){
                if(intervalFee.getTimeUnit() ==  TimeUnit.HOURS && minutesGreaterThanZero){
                    totalCost = intervalFee.getFee().longValue();
                }else{
                    totalCost = totalTime * intervalFee.getFee().longValue();
                }
                break;
            }
            if(intervalFee.getTimeUnit() == timeUnit && intervalFee.getStartTimeInclusive() <= totalTime && totalTime < intervalFee.getEndTimeExclusive()){
                totalCost = timeUnit == TimeUnit.DAYS ? totalTime * intervalFee.getFee().longValue() : intervalFee.getFee().longValue();
                break;
            }
        }
        return totalCost;
    }

    public List<IntervalFee> getCostModel(VehicleType vehicleType){
        return this.vehicleTypeIntervalFeeMap.get(vehicleType);
    }

}
