package lab9.task2;

public class ProductConsumer {
    public static void main(String[] args){
        CircularBuffer buffer1 = new CircularBuffer(10);
        CircularBuffer buffer2 = new CircularBuffer(10);

        for (int i = 1; i <= 5; i++) {
            int threadNum = i;
            Thread producer = new Thread(() -> {
                int msgCount = 0;
                try {
                    while (true) {
                        String msg = "Потік №" + threadNum + " згенерував повідомлення " + msgCount++;
                        buffer1.put(msg);
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            producer.setDaemon(true);
            producer.start();
        }
        
        for (int i = 1; i <= 2; i++) {
            int threadNum = i;
            Thread translator = new Thread(() -> {
                try {
                    while (true) {
                        String msg = buffer1.take();
                        String translated = "Потік №" + threadNum + " переклав повідомлення: " + msg;
                        buffer2.put(translated);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            translator.setDaemon(true);
            translator.start();
        }
        for (int i = 0; i < 100; i++) {
            try{
                String msg = buffer2.take();
                System.out.println(msg);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
