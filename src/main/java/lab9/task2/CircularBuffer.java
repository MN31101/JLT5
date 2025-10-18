package lab9.task2;

public class CircularBuffer {
    private final String[] buffer;
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    public CircularBuffer(int size){
        buffer = new String[size];
    }

    public synchronized void put(String message) throws InterruptedException {
        while (count == buffer.length) wait();
        buffer[tail] = message;
        tail = (tail + 1) % buffer.length;
        count++;
        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (count == 0) wait();
        String message = buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        notifyAll();
        return message;
    }
}
