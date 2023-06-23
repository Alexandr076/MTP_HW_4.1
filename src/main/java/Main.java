import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static int sizeForText_a = 0;
    private static int sizeForText_b = 0;
    private static int sizeForText_c = 0;

    private static String str_a = "";
    private static String str_b = "";
    private static String str_c = "";
    private static final String LETTERS = "abc";
    private static final int LENGTH_OF_TEXT = 10_000;
    private static final int COUNT_OF_TEXTS = 100;
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int countOfLetter(char letter, String str) {
        return StringUtils.countMatches(str, letter);
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> queueForLetter_a = new ArrayBlockingQueue<>(100);
        ArrayBlockingQueue<String> queueForLetter_b = new ArrayBlockingQueue<>(100);
        ArrayBlockingQueue<String> queueForLetter_c = new ArrayBlockingQueue<>(100);
        ExecutorService es = Executors.newFixedThreadPool(4);


        es.execute(() -> {
            for (int i = 0; i < COUNT_OF_TEXTS; i++) {
                String str = generateText(LETTERS, LENGTH_OF_TEXT);
                try {
                    queueForLetter_a.put(str);
                    queueForLetter_b.put(str);
                    queueForLetter_c.put(str);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        es.execute(() -> {
            int tempCount;
            for (int i = 0; i < COUNT_OF_TEXTS; i++) {
                String str = "";
                try {
                    str = queueForLetter_a.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                tempCount = countOfLetter('a', str);
                if (sizeForText_a < tempCount) {
                    str_a = str;
                    sizeForText_a = tempCount;
                }
            }
        });

        es.execute(() -> {
            int tempCount;
            for (int i = 0; i < COUNT_OF_TEXTS; i++) {
                String str = "";
                try {
                    str = queueForLetter_b.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                tempCount = countOfLetter('b', str);
                if (sizeForText_b < tempCount) {
                    str_b = str;
                    sizeForText_b = tempCount;
                }
            }
        });

        es.execute(() -> {
            int tempCount;
            for (int i = 0; i < COUNT_OF_TEXTS; i++) {
                String str = "";
                try {
                    str = queueForLetter_c.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                tempCount = countOfLetter('c', str);
                if (sizeForText_c < tempCount) {
                    str_c = str;
                    sizeForText_c = tempCount;
                }
            }
        });

        es.shutdown();

        es.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("str_a " + str_a);
        System.out.println("str_b " + str_b);
        System.out.println("str_c " + str_c);
    }
}
