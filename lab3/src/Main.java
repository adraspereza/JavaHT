import Structure.Room;

import static java.lang.Thread.sleep;

public class Main {
    public static synchronized void main(String[] args) throws InterruptedException {
        Room queue = new Room();

        sleep(10000);
        queue.stop();
    }
}