package Structure;

public class Instructor extends Thread implements Robot {
    final private String prefix = "Instructor: ";

    public Instructor(Room queue)
    {
        this.queue = queue;
        System.out.println(prefix + "Ready to work");
    }

    private boolean closed = false;
    private Room queue;

    @Override
    public void run() {
        try {
            System.out.println(prefix + "Launching students");
            while (!closed) {
                queue.add(Student.getRandomStudent(), true);
            }
            System.out.println(prefix + "Launching end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void setClosed(boolean closed)
    {
        this.closed = closed;
    }
}
