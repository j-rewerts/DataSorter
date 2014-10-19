import java.util.ArrayList;
import java.util.PriorityQueue;


public class HeapSort implements Runnable {
	private Thread t;
	private String threadName;
	private ArrayList<Integer> ints;
	
	public HeapSort(ArrayList<Integer> ints) {
		threadName = "HeapSort";
		this.ints = ints;
	}
	
	@Override
	public void run() {
		System.out.println("Running " +  threadName );
		ints = this.Sort();
	}
	
	public void start() {
		System.out.println("Starting thread: " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	public ArrayList<Integer> Sort() {
		// Acts as a min heap.
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>(ints.size());
		for (Integer i : ints) {
			heap.add(i);
		}
		
		// Add them back into an arraylist
		ArrayList<Integer> sortedInts = new ArrayList<Integer>();
		for (int i = 0; i < ints.size(); i++) {
			sortedInts.add(heap.remove());
		}
		
		return sortedInts;
	}
	
	public ArrayList<Integer> getList() {
		return ints;
	}
}
