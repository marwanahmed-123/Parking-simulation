import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Gate implements Runnable {
    public Queue<Car> queueOfCars = new PriorityQueue<>(Comparator.comparingInt(car -> car.arriveTimeInMilliseconds));
    public int gateNumber;
    public parkingSpots parking;
    public Thread currenThread = null;

    public Gate(int gateNumber, parkingSpots parking) {
        this.gateNumber = gateNumber;
        this.parking = parking;
    }

    @Override
    public void run() {
        while (!queueOfCars.isEmpty()) {
            Car car = queueOfCars.remove();
            parking.produce(car);
        }
    }

    public void reset() {
        synchronized (this) {
            if ((currenThread != null && !currenThread.isAlive()) || currenThread == null) {
                currenThread = new Thread(this);
                currenThread.start();
            }
        }
    }
}
