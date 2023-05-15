package parkinglot;

import models.ParkingReceipt;
import models.ParkingTicket;
import models.VehicleType;

import java.time.LocalDateTime;

public interface ParkingLot {

    ParkingTicket parkVehicle(VehicleType vehicleType, LocalDateTime startTime) throws Exception;
    ParkingReceipt unparkVehicle(VehicleType vehicleType, ParkingTicket parkingTicket, LocalDateTime endTime) throws Exception;
}
