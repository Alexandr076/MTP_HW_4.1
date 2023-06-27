import java.util.concurrent.ArrayBlockingQueue;

public class ContainerText_b extends ContainerText{
    public ContainerText_b(ArrayBlockingQueue<String> queueForLetter) {
        super(queueForLetter);
    }

    @Override
    public char getLetter() {
        return 'b';
    }
}
