import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class WatchDogTimer implements Runnable {
	private Thread t;
	private String threadName;
	private Runnable timedRunnable;
	private Executive executive;
	private long time;
	
	public WatchDogTimer(Executive e) {
		executive = e;
		threadName = "WatchDogTimer";
	}
	
	@Override
	public void run() {
		if (timedRunnable == null) {
			return;
		}
		timeRunnable(timedRunnable, time);
	}
	
	public void start() {
		System.out.println("Starting thread: " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	private void timeRunnable (Runnable r, long seconds) {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		try {
			exec.submit(r).get(seconds, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			executive.notifyTimeout(r);
		}
		System.out.println("After Exec");
	}	
	
	public void SetRunnable(Runnable r) {
		this.timedRunnable = r;
	}
	
	public void SetTimeout(long time) {
		this.time = time;
	}
	
	public Thread getThread() {
		return this.t;
	}
}
