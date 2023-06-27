import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;

public abstract class ContainerText {
    private ArrayBlockingQueue<String> queueForLetter;
    private int sizeForText;
    private String str;

    public static int countOfLetter(char letter, String str) {
        return StringUtils.countMatches(str, letter);
    }

    public ContainerText(ArrayBlockingQueue<String> queueForLetter) {
        this.queueForLetter = queueForLetter;
        this.sizeForText = 0;
        this.str = "";
    }

    public String getStr() {
        return str;
    }

    public char getLetter() {
        return 0;
    };

    public void updateContainerIfNeeded() {
        String take = null;
        try {
            take = queueForLetter.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int tempCount = countOfLetter(getLetter(), take);
        if (sizeForText < tempCount) {
            str = take;
            sizeForText = tempCount;
        }
    }

    public void putItem(String str) {
        try {
            queueForLetter.put(str);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
