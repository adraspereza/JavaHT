package Structure;

public class Instructor extends Thread implements Robot {
    final private String prefix = "[ROBO] INST | Инструктор: ";

    public Instructor(Room queue)
    {
        this.queue = queue;
        System.out.println(prefix + "готов к работе.");
    }

    private boolean closed = false;
    private Room queue;

    @Override
    public void run() {
        try {
            System.out.println(prefix + "начат запуск студентов.");
            while (!closed) {
                queue.add(Student.getRandomStudent(), true);
            }
            System.out.println(prefix + "запуск студентов окончен.");
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
