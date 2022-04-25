package lab8;

import java.util.ArrayList;

/**
 *
 * COMP 3021
 *
This is a class that prints the maximum value of a given array of 90 elements

This is a single threaded version.

Create a multi-thread version with 3 threads:

one thread finds the max among the cells [0,29]
another thread the max among the cells [30,59]
another thread the max among the cells [60,89]

Compare the results of the three threads and print at console the max value.

 *
 * @author valerio
 *
 */
public class FindMax {
	// this is an array of 90 elements
	// the max value of this array is 9999
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };

	public static void main(String[] args) {
		//new FindMax().printMax();
		new FindMax().printMax(3);
	}

	public void printMax() {
		// this is a single threaded version
		int max = findMax(0, array.length - 1);
		System.out.println("the max value is " + max);
	}

	public void printMax(int thread){
		ArrayList<Integer> maxs = new ArrayList<>();
		ArrayList<MyTask> tasks = new ArrayList<>();
		ArrayList<Thread> threads = new ArrayList<>();
		int lengthOfArray = array.length/thread;
		for(int i = 0; i < thread; i++){
			tasks.add(new MyTask(lengthOfArray*i,lengthOfArray*(i+1)-1));
			threads.add(new Thread(tasks.get(i)));
			threads.get(i).start();
		}

		try{
			for(Thread t: threads){
				t.join();
			}
		}catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		for(MyTask task: tasks){
			maxs.add(task.getOutput());
		}

		int max = maxs.get(0);
		for (int i = 1; i < thread; i++) {
			if (maxs.get(i) > max) {
				max = maxs.get(i);
			}
		}

		System.out.println("the max value is " + max);
	}

	/**
	 * returns the max value in the array within a give range [begin,range]
	 *
	 * @param begin
	 * @param end
	 * @return
	 */
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}

	class MyTask implements Runnable{
		private int begin;
		private int end;
		private int output;

		public MyTask(int begin, int end){
			this.begin = begin;
			this.end = end;
		}

		public int getOutput(){
			return this.output;
		}

		@Override
		public void run(){
			output = findMax(begin,end);
		}
	}
}
