import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String LETTERS = "abc";
    private static final int LENGTH_OF_TEXT = 100;
    private static final int COUNT_OF_TEXTS = 5;

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


    public static void main(String[] args) throws InterruptedException {
        ContainerText_a containerText_a = new ContainerText_a(new ArrayBlockingQueue<>(100));
        ContainerText_b containerText_b = new ContainerText_b(new ArrayBlockingQueue<>(100));
        ContainerText_c containerText_c = new ContainerText_c(new ArrayBlockingQueue<>(100));

        ExecutorService es = Executors.newFixedThreadPool(4);


        es.execute(() -> {
            for (int i = 0; i < COUNT_OF_TEXTS; i++) {
                String str = generateText(LETTERS, LENGTH_OF_TEXT);
                    containerText_a.putItem(str);
                    containerText_b.putItem(str);
                    containerText_c.putItem(str);
            }
        });

        es.execute(() -> {
            for (int i = 0; i < COUNT_OF_TEXTS; i++) {
                containerText_a.updateContainerIfNeeded();
            }
        });

        es.execute(() -> {
            for (int i = 0; i < COUNT_OF_TEXTS; i++) {
                containerText_b.updateContainerIfNeeded();
            }
        });

        es.execute(() -> {
            for (int i = 0; i < COUNT_OF_TEXTS; i++) {
                containerText_c.updateContainerIfNeeded();
            }
        });

        es.shutdown();

        es.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("str_a " + containerText_a.getStr());
        System.out.println("str_b " + containerText_b.getStr());
        System.out.println("str_c " + containerText_c.getStr());
    }
}
