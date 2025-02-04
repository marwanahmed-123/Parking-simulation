public class semaphore {
    protected int value;

    public int getVal() {
        return value;
    }

    protected semaphore() {
        value = 0;
    }

    protected semaphore(int start) {
        value = start;
    }

    public synchronized void P() {
        value--;
        if (value < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("an error occured");
            }
        }
    }

    public synchronized void P(Car car) {
        value--;
        if (value < 0) {
            try {
                System.out.println(
                        "Car " + car.carNumber + " from Gate " + car.gate.gateNumber + " is waiting for a spot");
                wait();
            } catch (InterruptedException e) {
                System.out.println("an error occured");
            }
        }
    }

    public synchronized void V() {
        value++;
        if (value <= 0) {
            notify();
        }
    }
}
