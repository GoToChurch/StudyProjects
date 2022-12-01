package restaraunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets = new ArrayList<>();
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        Random random = new Random();
        Tablet tablet = tablets.get(random.nextInt(tablets.size()));
        int count = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                tablet.createTestOrder();
                count++;
                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
