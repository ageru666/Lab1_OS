package src;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedResourceWithLock {
    private Double valueF = null;
    private Double valueG = null;
    private double sum = 0.0;
    private final Lock lock = new ReentrantLock();

    public void addF(double valueF) {
        lock.lock();
        try {
            this.valueF = valueF;
            updateSum();
        } finally {
            lock.unlock();
        }
    }

    public void addG(double valueG) {
        lock.lock();
        try {
            this.valueG = valueG;
            updateSum();
        } finally {
            lock.unlock();
        }
    }

    private void updateSum() {
        if (valueF != null && valueG != null) {
            sum = valueF + valueG;
        }
    }

    public double getSum() {
        lock.lock();
        try {
            return sum;
        } finally {
            lock.unlock();
        }
    }
}

