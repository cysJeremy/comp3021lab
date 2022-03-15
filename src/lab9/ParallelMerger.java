package lab9;

import java.util.concurrent.Semaphore;

public class ParallelMerger {
    @FunctionalInterface
    public interface ThreadSafeCharacterWriter {
        void write(char ch);
    }

    /**
     * The worker class which is {@link Runnable}.
     * All worker objects will work together to merge their own char array and put results in the provided instance of {@link ThreadSafeCharacterWriter}.
     * <p>
     * The merge rule is at follows:
     * <p>
     * 1. each worker thread take turns to write one char into the {@link ThreadSafeCharacterWriter}
     * <p>
     * 2. For example if we have two workers, worker0, worker1, with char array "hlo" and "el!" respectively.
     * Then after the merge, {@link ThreadSafeCharacterWriter} should contain: "hello!".
     * 'h' should be written by the first worker, 'e' should be written by the second worker and 'l' should be written by the first worker, so on and so forth.
     * <p>
     * TODO complete this class to implement the above functionality.
     * This class should implement {@link Runnable} so that it can be run in a thread.
     */
    private static class Worker implements Runnable{
    	private String segment;
    	ThreadSafeCharacterWriter resultWriter;
    	Semaphore sem_previous;
    	Semaphore sem_next;
    	private int position;

    	public Worker(String segment, ThreadSafeCharacterWriter resultWriter,Semaphore sem_previous, Semaphore sem_next){
    		this.segment = segment;
    		this.resultWriter = resultWriter;
    		position = 0;
    		this.sem_previous = sem_previous;
    		this.sem_next = sem_next;
    	}

    	@Override
    	public void run(){
    			while(position < segment.length()){
    				try {
						sem_previous.acquire();
						resultWriter.write(segment.charAt(position));
	    				position++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				sem_next.release();

    			}


    	}

    }

    /**
     * TODO This method does the merge in an array of string segments. Here is the desired procedure:
     * <ol>
     *     <li>Create workers and their threads, the number of threads should be the same as the length of array {@code segments}.</li>
     *     <li>Start all threads to merge the {@code segments} in parallel.</li>
     *     <li>Wait all threads to finish process and exit.</li>
     * </ol>
     *
     * @param segments     An array of strings that are to be merged. Each segment should be corresponding to one thread.
     * @param resultWriter A {@link ThreadSafeCharacterWriter} instance that the workers should write the results to.
     *                     The {@code resultWriter} should be written with the characters of merged string.
     *                     For example, suppose the merge result should be "hello", then the {@code resultWriter}
     *                     should be called 5 times with each character in the order.
     */
    public static void merge(String[] segments, ThreadSafeCharacterWriter resultWriter) throws InterruptedException {
    	Thread[] threads = new Thread[segments.length];
    	Semaphore[] sems = new Semaphore[segments.length];
    	if(segments.length > 0){
    		sems[segments.length-1] = new Semaphore(1);
    		sems[0] = new Semaphore(0);
    		threads[0] = new Thread(new Worker(segments[0],resultWriter,sems[segments.length-1],sems[0]));
    	}

    	for(int i = 1; i < segments.length-1 ; i++){
    		sems[i] = new Semaphore(0);
    		threads[i] = new Thread(new Worker(segments[i], resultWriter,sems[i-1], sems[i]));
    		//threads[i].setDaemon(true);
    	}
    	threads[segments.length-1] = new Thread(new Worker(segments[segments.length-1], resultWriter,sems[segments.length-2], sems[segments.length-1]));
    	for(Thread t: threads){
			t.start();
		}
    	try{
			threads[segments.length-1].join();
		}catch (InterruptedException ie) {
			ie.printStackTrace();
		}
    }
}
