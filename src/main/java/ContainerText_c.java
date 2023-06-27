import java.util.concurrent.ArrayBlockingQueue;

public class ContainerText_c extends ContainerText{

    public ContainerText_c(ArrayBlockingQueue<String> queueForLetter) {
        super(queueForLetter);
    }

    @Override
    public char getLetter() {
        return 'c';
    }
}
