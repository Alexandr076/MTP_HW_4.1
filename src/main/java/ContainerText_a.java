import java.util.concurrent.ArrayBlockingQueue;

public class ContainerText_a extends ContainerText {
    public ContainerText_a(ArrayBlockingQueue<String> queueForLetter) {
        super(queueForLetter);
    }

    @Override
    public char getLetter() {
        return 'a';
    }
}
