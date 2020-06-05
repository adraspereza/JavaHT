package Structure;

public class Teacher extends Thread implements Robot {
    final protected int maxStudents = 10;
    final protected int labsPerTick = 5;
    final String prefix;


    public Teacher(Subject subject, Room queue, String name)
    {
        super();
        this.subject = subject;
        this.queue = queue;
        this.setName(name);
        this.prefix = String.format("[ROBO] %s | %s: ", subject, name);
        System.out.println(prefix + "Готов");
    }


    private Subject subject;
    private boolean closed = false;
    private Room queue;

    @Override
    public void run()
    {
        try {
            System.out.println(prefix + "Предмет " + subject);
            while (!closed) {
                Student student = null;
                try {
                    while ((student == null) && !closed) {
                        student = queue.poll(subject);
                    }
                    if (closed) {
                        break;
                    }

                    int labsToDo = student.getLabs();
                    while (labsToDo > 0) {
                        labsToDo -= labsPerTick;
                        sleep(10);
                        System.out.println(prefix + "Остаток по работам: " + labsToDo);
                    }
                    System.out.println(prefix + "Работ сдано: " + student.getLabs());
                } catch (NullPointerException e) {
                    System.out.println(prefix + " ");
                }
            }
            System.out.println(prefix + "Конец ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setClosed(boolean closed)
    {
        this.closed = closed;
    }
}
