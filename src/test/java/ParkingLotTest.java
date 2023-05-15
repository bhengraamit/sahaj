import feemodels.AirportFeeModel;
import feemodels.FeeModel;
import feemodels.MallFeeModel;
import feemodels.StadiumFeeModel;
import models.*;
import org.junit.Assert;
import org.junit.Test;
import parkinglot.GenericParkingLot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ParkingLotTest {

    /**
     * Spots:
     * ● Motorcycles/scooters: 2 spots
     * ● Cars/SUVs/Buses/Trucks: NA
     */
    @Test
    public void testSmallParkingLot() throws Exception {
        System.out.println("Running Small Parking Lot Test Cases");
        Map<VehicleType, SpotInfo> vehicleTypeSpots = new HashMap<>();
        SpotInfo spotInfo = new SpotInfo(2);
        vehicleTypeSpots.put(VehicleType.MOTORCYCLE,spotInfo);

        Map<VehicleType, Number> vehicleTypeCost = new HashMap<>();
        vehicleTypeCost.put(VehicleType.MOTORCYCLE,new Integer(10));
        GenericParkingLot smallParkingLot = new GenericParkingLot(vehicleTypeSpots,new MallFeeModel(vehicleTypeCost));

        LocalDateTime startTime  = LocalDateTime.parse("2022-05-29T14:04:07");
        ParkingTicket ticket1 = smallParkingLot.parkVehicle(VehicleType.MOTORCYCLE,startTime);
        System.out.println(ticket1);
        startTime  = LocalDateTime.parse("2022-05-29T14:44:07");
        ParkingTicket ticket2 = smallParkingLot.parkVehicle(VehicleType.MOTORCYCLE,startTime);
        System.out.println(ticket2);
        LocalDateTime endTime  = LocalDateTime.parse("2022-05-29T15:40:07");
        System.out.println(smallParkingLot.unparkVehicle(VehicleType.MOTORCYCLE,ticket2,endTime));

        startTime  = LocalDateTime.parse("2022-05-29T15:59:07");
        System.out.println(smallParkingLot.parkVehicle(VehicleType.MOTORCYCLE,startTime));

        endTime  = LocalDateTime.parse("2022-05-29T17:44:07");
        System.out.println(smallParkingLot.unparkVehicle(VehicleType.MOTORCYCLE,ticket1,endTime));
    }

    /**
     *
     * ● Motorcycles/scooters: 100 spots
     * ● Cars/SUVs: 80 spots
     * ● Buses/Trucks: 10 spots
     */
    public Map<VehicleType,Integer> getMallParkingLotSpots(){
        Map<VehicleType,Integer> map = new HashMap<>();
        map.put(VehicleType.MOTORCYCLE,100);
        map.put(VehicleType.CAR,80);
        map.put(VehicleType.TRUCK,10);
        return map;
    }

    public Map<VehicleType,Number> getMallFeeModelData(){
        Map<VehicleType, Number> vehicleTypeCost = new HashMap<>();
        vehicleTypeCost.put(VehicleType.MOTORCYCLE,new Integer(10));
        vehicleTypeCost.put(VehicleType.CAR,new Integer(20));
        vehicleTypeCost.put(VehicleType.TRUCK,new Integer(50));
        return vehicleTypeCost;
    }

    /**
     * ● Motorcycle parked for 3 hours and 30 mins. Fees: 40
     * ● Car parked for 6 hours and 1 min. Fees: 140
     * ● Truck parked for 1 hour and 59 mins. Fees: 100
     */
    public List<TestData> getMallParkingLotTestCases(){
        List<TestData> testCases = new ArrayList<>();
        testCases.add(new TestData(VehicleType.MOTORCYCLE, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T04:30:00"),40)); //  3 hours 30 mins
        testCases.add(new TestData(VehicleType.CAR, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T07:01:00"),140)); //  6 hours 1 min
        testCases.add(new TestData(VehicleType.TRUCK, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T02:59:00"),100)); //  6 hours 1 min
        return testCases;
    }

    public void testParkingLot(Map<VehicleType,Integer> spots, FeeModel feeModel, List<TestData> testCases) throws Exception{
        Map<VehicleType, SpotInfo> vehicleTypeSpots = new HashMap<>();
        for (Map.Entry<VehicleType,Integer> entry: spots.entrySet()){
            SpotInfo spotInfo = new SpotInfo(entry.getValue());
            vehicleTypeSpots.put(entry.getKey(),spotInfo);
        }
        GenericParkingLot genericParkingLot = new GenericParkingLot(vehicleTypeSpots,feeModel);
        for (TestData testData: testCases){
            ParkingTicket ticket1 = genericParkingLot.parkVehicle(testData.getVehicleType(),testData.getStartTime());
            System.out.println(ticket1);
            ParkingReceipt receipt = genericParkingLot.unparkVehicle(testData.getVehicleType(),ticket1,testData.getEndTime());
            System.out.println(receipt);
            Assert.assertEquals(testData.getExpectedFees(),receipt.getFees());
        }
    }

    /**
     * Spots:
     * ● Motorcycles/scooters: 100 spots
     * ● Cars/SUVs: 80 spots
     * ● Buses/Trucks: 10 spots
     *
     * ● Motorcycle parked for 3 hours and 30 mins. Fees: 40
     * ● Car parked for 6 hours and 1 min. Fees: 140
     * ● Truck parked for 1 hour and 59 mins. Fees: 100
     */
    @Test
    public void testMallParkingLot() throws Exception{
        System.out.println("Running Mall Parking Lot Test Cases");
        testParkingLot(getMallParkingLotSpots(),new MallFeeModel(getMallFeeModelData()),getMallParkingLotTestCases());
    }

    /**
     *
     * ● Motorcycles/scooters: 1000 spots
     * ● Cars/SUVs: 1500 spots
     */
    public Map<VehicleType,Integer> getStadiumParkingLotSpots(){
        Map<VehicleType,Integer> map = new HashMap<>();
        map.put(VehicleType.MOTORCYCLE,1000);
        map.put(VehicleType.CAR,1500);
        return map;
    }

    public Map<VehicleType, List<IntervalFee>> getStadiumFeeModelData(){
        Map<VehicleType, List<IntervalFee>> vehicleTypeCost = new HashMap<>();
        List<IntervalFee> intervalFees = new ArrayList<>();
        intervalFees.add(new IntervalFee(0,4,30, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(4,12,60, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(12,Integer.MAX_VALUE,100, TimeUnit.HOURS));
        vehicleTypeCost.put(VehicleType.MOTORCYCLE,intervalFees);

        intervalFees = new ArrayList<>();
        intervalFees.add(new IntervalFee(0,4,60, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(4,12,120, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(12,Integer.MAX_VALUE,200, TimeUnit.HOURS));
        vehicleTypeCost.put(VehicleType.CAR,intervalFees);
        return vehicleTypeCost;
    }

    /**
     *
     *● Motorcycle parked for 3 hours and 40 mins. Fees: 30
     * ● Motorcycle parked for 14 hours and 59 mins. Fees: 390.
     *      ○ 30 for the first 4 hours. 60 for the next 8 hours. And then 300 for the
     *          remaining duration.
     *
     * ● Electric SUV parked for 11 hours and 30 mins. Fees: 180.
     *      ○ 60 for the first 4 hours and then 120 for the remaining duration.
     * ● SUV parked for 13 hours and 5 mins. Fees: 580.
     *      ○ 60 for the first 4 hours and then 120 for the next 8 hours. 400 for the
     *          remaining duration.
     */
    public List<TestData> getStadiumParkingLotTestCases(){
        List<TestData> testCases = new ArrayList<>();
        testCases.add(new TestData(VehicleType.MOTORCYCLE, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T04:40:00"),30)); //  3 hours 40 mins
        testCases.add(new TestData(VehicleType.MOTORCYCLE, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T15:59:00"),390)); //  14 hours 59 mins
        testCases.add(new TestData(VehicleType.CAR, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T12:30:00"),180)); //  11 hours 30 min
        testCases.add(new TestData(VehicleType.CAR, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T14:05:00"),580)); //  13 hours 05 min
        return testCases;
    }

    @Test
    public void testStadiumParkingLot() throws Exception{
        System.out.println("Running Stadium Parking Lot Test Cases");
        testParkingLot(getStadiumParkingLotSpots(),new StadiumFeeModel(getStadiumFeeModelData()),getStadiumParkingLotTestCases());
    }

    /**
     *
     * ● Motorcycles/scooters: 200 spots
     * ● Cars/SUVs: 500 spots
     * ● Buses/Trucks: 100 spots
     */
    public Map<VehicleType,Integer> getAirportParkingLotSpots(){
        Map<VehicleType,Integer> map = new HashMap<>();
        map.put(VehicleType.MOTORCYCLE,200);
        map.put(VehicleType.CAR,500);
        map.put(VehicleType.TRUCK,100);
        return map;
    }

    public Map<VehicleType, List<IntervalFee>> getAirportFeeModelData(){
        Map<VehicleType, List<IntervalFee>> vehicleTypeCost = new HashMap<>();
        List<IntervalFee> intervalFees = new ArrayList<>();
        intervalFees.add(new IntervalFee(0,1,0, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(1,8,40, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(8,24,60, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(1,Integer.MAX_VALUE,80, TimeUnit.DAYS));
        vehicleTypeCost.put(VehicleType.MOTORCYCLE,intervalFees);

        intervalFees = new ArrayList<>();
        intervalFees.add(new IntervalFee(0,12,60, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(12,24,80, TimeUnit.HOURS));
        intervalFees.add(new IntervalFee(1,Integer.MAX_VALUE,100, TimeUnit.DAYS));
        vehicleTypeCost.put(VehicleType.CAR,intervalFees);
        return vehicleTypeCost;
    }

    /**
     *
     * ● Motorcycle parked for 55 mins. Fees: 0
     * ● Motorcycle parked for 14 hours and 59 mins. Fees: 60
     * ● Motorcycle parked for 1 day and 12 hours. Fees: 160
     * ● Car parked for 50 mins. Fees: 60
     * ● SUV parked for 23 hours and 59 mins. Fees: 80
     * ● Car parked for 3 days and 1 hour. Fees: 400
     */
    public List<TestData> getAirportParkingLotTestCases(){
        List<TestData> testCases = new ArrayList<>();
        testCases.add(new TestData(VehicleType.MOTORCYCLE, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T01:55:00"),0)); //  1 hours 55 mins
        testCases.add(new TestData(VehicleType.MOTORCYCLE, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T15:59:00"),60)); //  14 hours 59 mins
        testCases.add(new TestData(VehicleType.MOTORCYCLE, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-30T13:00:00"),160)); //  1 day 12 hours
        testCases.add(new TestData(VehicleType.CAR, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-29T01:50:00"),60)); //  50 mins
        testCases.add(new TestData(VehicleType.CAR, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-05-30T00:59:00"),80)); //  23 hours 59 mins
        testCases.add(new TestData(VehicleType.CAR, LocalDateTime.parse("2022-05-29T01:00:00"),LocalDateTime.parse("2022-06-01T02:00:00"),400)); //  3 days 1 hour
        return testCases;
    }

    @Test
    public void testAirportParkingLot() throws Exception{
        System.out.println("Running Airport Parking Lot Test Cases");
        testParkingLot(getAirportParkingLotSpots(),new AirportFeeModel(getAirportFeeModelData()),getAirportParkingLotTestCases());
    }


    public class TestData{
        VehicleType vehicleType;
        LocalDateTime startTime;
        LocalDateTime endTime;
        long expectedFees;

        public TestData(VehicleType vehicleType, LocalDateTime startTime, LocalDateTime endTime, long expectedFees) {
            this.vehicleType = vehicleType;
            this.startTime = startTime;
            this.endTime = endTime;
            this.expectedFees = expectedFees;
        }

        public VehicleType getVehicleType() {
            return vehicleType;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public long getExpectedFees() {
            return expectedFees;
        }
    }
}
