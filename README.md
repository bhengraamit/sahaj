# Sahaj Parking Lot Assignment

##Brief Overview of how classes are connected with each other


###Interfaces

- FeeModel
- ParkingLot


## Hierarchy of classes
- FeeModel -> AbstractFeeModel -> (MallFeeModel/StadiumFeeModel/AirportFeeModel)
- ParkingLot -> AbstractParkingLot -> GenericParkingLot


## In Detail
- GenericParkingLot accepts vehicle spot info and fee model, then based on these states park or unpark the vehicle.
- Every FeeModel has their own datastructure to store the parking cost and also business logic is customised based on that.
- Exceptions are thrown when we don't find a vehicle configuration.

## Tests
- Tests are written based on the test cases given in the problem statement
                                
