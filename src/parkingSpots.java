import java.time.Duration;
import java.time.Instant;

public class parkingSpots {
    private int spots = 4;
    private Car[] garage = new Car[spots];
    private boolean[] parkingStatus = new boolean[spots];
    public semaphore empty = new semaphore(spots);
    public semaphore full = new semaphore(0);
    public semaphore mutex = new semaphore(1);

    public void produce(Car car) {
        Instant start = Instant.now();
        empty.P(car);
        int parkingSpot = findParkingSpot();
        garage[parkingSpot] = car;
        car.parkingNumber = parkingSpot;
        parkingStatus[parkingSpot] = true;
        Instant end = Instant.now();
        Duration waitingTime = Duration.between(start, end);
        car.waiting.V();
        full.V();
        if (waitingTime.toSeconds() != 0) {
            System.out
                    .println("Car " + car.carNumber + " from Gate " + car.gate.gateNumber + " parked after waiting for "
                            + waitingTime.toSeconds() + " units of time. " + "(Parking Status: " + full.getVal()
                            + " spots occupied)");
        } else {
            System.out
                    .println("Car " + car.carNumber + " from Gate " + car.gate.gateNumber + " parked "
                            + "(Parking Status: " + (full.getVal())
                            + " spots occupied)");
        }
    }

    public void consume(int parkingSpot) {
        full.P();
        parkingStatus[parkingSpot] = false;
        Car car = garage[parkingSpot];
        System.out.println(
                "Car " + car.carNumber + " left from gate " + car.gate.gateNumber + " after "
                        + car.parkingDurationInMilliseconds / 1000
                        + " units of time. (Parking status: " + car.parking.full.getVal() + " spots occupied)");
        empty.V();
    }

    public int findParkingSpot() {
        int parkingSpot = -1;
        for (int i = 0; i < spots; i++) {
            if (parkingStatus[i] == false) {
                parkingSpot = i;
                break;
            }
        }
        return parkingSpot;
    }
}
