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
        this.prefix = String.format("TEACH - %s | %s - ", subject, name);
        System.out.println(prefix + "Ready to work");
    }

    private Subject subject;
    private boolean closed = false;
    private Room queue;

    @Override
    public void run()
    {
        try {
            System.out.println(prefix + "Subject: " + subject);
            while (!closed) {
                Student student = null;
                try {
                    while (student == null && !closed) {
                        student = queue.poll(subject);
                    }
                    if (closed) {
                        break;
                    }

                    int labsToDo = student.getLabs();
                    while (labsToDo > 0) {
                        labsToDo -= labsPerTick;
                        sleep(10);
                        System.out.println(prefix + "Work left: " + labsToDo);
                    }
                    System.out.println(prefix + "Submitted works: " + student.getLabs());
                } catch (NullPointerException e) {
                    System.out.println(prefix + "Nullpo! Зашел на @channel. ");
                }
            }
            System.out.println(prefix + "End");
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
