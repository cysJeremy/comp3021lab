package lab5;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class HeapTest {

    private Heap<Integer> integerHeap;

    @Before
    public void setup() {
        integerHeap = new Heap<>();
    }

    @Test
    public void peek() {
        integerHeap.addAll(Arrays.asList(30, 10, 20));
        assertEquals(Integer.valueOf(10), integerHeap.peek());
    }

    @Test
    public void poll() {
        List<Integer> values = Arrays.asList(2, 53, 213, 5, 1, 5, 4, 210, 14, 26, 44, 35, 31, 33, 19, 52, 27);
        integerHeap.addAll(values);

        Collections.sort(values);
        for (int x : values) {
            assertEquals(Integer.valueOf(x), integerHeap.poll());
        }
    }

    @Test
    public void size() {
    	List<Integer> values = Arrays.asList(10, 14, 26, 44, 35, 31, 33, 19, 52, 27);
        integerHeap.addAll(values);
        assertEquals(values.size(), integerHeap.size());
    }
}
