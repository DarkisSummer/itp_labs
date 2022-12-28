import java.net.URL;
import java.util.LinkedList;
public class URLPool {
    private LinkedList<URLDepthPair> found, unique;
    private int waiters = 0, maxDepth;

    public URLPool(int maxDepth) {
        this.maxDepth = maxDepth;
        found = new LinkedList<>();
        unique = new LinkedList<>();
        waiters = 0;
    }
    public synchronized URLDepthPair get() {
        while (found.isEmpty()) {
            waiters++;
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
            waiters--;
        }
        return found.removeFirst();
    }

    public synchronized void add(URLDepthPair pair) {
        if (!found.contains(pair) && !unique.contains(pair)) {
            unique.add(pair);
            if (pair.getDepth() < maxDepth) {
                found.add(pair);
                notify();
            }
        }
    }

    public LinkedList<URLDepthPair> getUnique() {
        return unique;
    }

    public int getWaiters() {
        return waiters;
    }
}