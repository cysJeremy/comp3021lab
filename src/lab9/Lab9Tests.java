package lab9;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;



public class Lab9Tests {
    @Test(timeout=1000)
    public void testTwoThreads() throws InterruptedException {
        String[] segments = new String[]{"hlowrd", "el ol!"};
        StringBuilder result = new StringBuilder();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        ReentrantLock lock = new ReentrantLock();
        ParallelMerger.merge(segments, ch -> {
            lock.lock();
            int threadIdx = result.length() % segments.length;
            result.append(ch);
            Thread thread = Thread.currentThread();
            if (!threads.contains(thread)) {
                threads.add(thread);
            }
            // check whether threads take turns to write
            assertEquals(threads.get(threadIdx), thread);
            lock.unlock();
        });
        for (Thread thread :
                threads) {
            // check whether all threads have exited after merge() returns
            assertEquals(Thread.State.TERMINATED, thread.getState());
        }
        // check whether number of threads is the same as number of segments to merge.
        assertEquals(2, threads.size());
        // check merge result
        assertEquals("hello world!", result.toString());
    }

    @Test(timeout=1000)
    public void testThreeThreads() throws InterruptedException {
        String[] segments = new String[]{"hlwl", "eood", "l r!"};
        StringBuilder result = new StringBuilder();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        ReentrantLock lock = new ReentrantLock();
        ParallelMerger.merge(segments, ch -> {
            lock.lock();
            int threadIdx = result.length() % segments.length;
            result.append(ch);
            Thread thread = Thread.currentThread();
            if (!threads.contains(thread)) {
                threads.add(thread);
            }
            // check whether threads take turns to write
            assertEquals(threads.get(threadIdx), thread);
            lock.unlock();
        });
        for (Thread thread :
                threads) {
            // check whether all threads have exited after merge() returns
            assertEquals(Thread.State.TERMINATED, thread.getState());
        }
        // check whether number of threads is the same as number of segments to merge.
        assertEquals(3, threads.size());
        // check merge result
        assertEquals("hello world!", result.toString());
    }
}
