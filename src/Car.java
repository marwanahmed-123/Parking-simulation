public class Car implements Runnable {
    public int arriveTimeInMilliseconds;
    public Gate gate;
    public int carNumber;
    public int parkingDurationInMilliseconds;
    public int parkingNumber;
    semaphore waiting = new semaphore();
    parkingSpots parking;

    public Car(int carNumber, int arriveTime, Gate gate, int parkingDuration, parkingSpots parking) {
        this.carNumber = carNumber;
        this.arriveTimeInMilliseconds = arriveTime * 1000;
        this.gate = gate;
        this.parkingDurationInMilliseconds = parkingDuration * 1000;
        this.parking = parking;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arriveTimeInMilliseconds);
            System.out.println("Car " + carNumber + " from Gate " + gate.gateNumber + " arrived at time "
                    + arriveTimeInMilliseconds / 1000);
        } catch (InterruptedException e) {
            System.out.println("an error occured");
        }
        getToGate();
        try {
            Thread.sleep(parkingDurationInMilliseconds);
        } catch (InterruptedException e) {
            System.out.println("an error occured");
        }
        parking.consume(parkingNumber);

    }

    public void getToGate() {
        gate.reset();
        gate.queueOfCars.add(this);
        waiting.P();
    }
}
