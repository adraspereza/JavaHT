package Structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import static java.lang.Thread.sleep;

public class Room {
    private final int teachersCount = 3;
    public final int maxStudents = 10;
    private final Instructor instructor;

    public Room() throws InterruptedException
    {
        Subject[] subjects = Subject.values();
        for (Subject subject : subjects) {
            students.put(subject, new ConcurrentLinkedQueue<>());
        }

        Teacher physRobot = new Teacher(Subject.PHYS, this, "Physic teacher");
        Teacher oopRobot = new Teacher(Subject.OOP, this, "OOP teacher");
        Teacher mathRobot = new Teacher(Subject.MATH, this, "Math teacher");
        teachers.put(Subject.PHYS, physRobot);
        teachers.put(Subject.OOP, oopRobot);
        teachers.put(Subject.MATH, mathRobot);

        instructor = new Instructor(this);
        instructor.start();
        sleep(2000);

        physRobot.start();
        oopRobot.start();
        mathRobot.start();
    }

    private Map<Subject, Queue<Student>> students = new HashMap<>(3);
    private Map<Subject, Teacher> teachers = new HashMap<>(teachersCount);
    private boolean closed = false;

    public void add(Student student, boolean notify) throws InterruptedException
    {
        students.get(student.getSubject()).add(student);
        if (notify) {
            synchronized (teachers.get(student.getSubject())) {
                teachers.get(student.getSubject()).notify();
            }
        }

        System.out.println(String.format("STUD - %s | Labs: %s", student.getSubject(), student.getLabs()));

        if (getSize() >= maxStudents) {
            synchronized (instructor) {
                instructor.wait();
            }
        }
    }

    public Student poll(Subject subject) throws InterruptedException {
        Student student;
        synchronized (teachers.get(subject)) {
            if (students.get(subject).size() > 0) {
                student = students.get(subject).poll();
            } else {
                student = null;

                if (closed) {
                    teachers.get(subject).setClosed(true);
                } else {
                    System.out.println("TEACH - " + subject + " | " + teachers.get(subject).getName() + " waiting");
                    teachers.get(subject).wait();
                }
            }
        }

        synchronized (instructor) {
            if (!closed) {
                instructor.notify();
            }
        }

        return student;
    }

    public Queue<Student> getQueue(Subject subject)
    {
        return students.get(subject);
    }

    public int getSize()
    {
        int acc = 0;
        Subject[] subjects = Subject.values();
        for (Subject subject : subjects) {
            acc += students.get(subject).size();
        }
        return acc;
    }

    public void stop()
    {
        System.out.println("This is the end...");
        closed = true;

        synchronized (instructor) {
            instructor.setClosed(true);
            instructor.notify();
        }

        Subject[] subjects = Subject.values();
        for (Subject subject : subjects) {
            synchronized (teachers.get(subject)) {
                teachers.get(subject).notify();
            }
        }
    }
}