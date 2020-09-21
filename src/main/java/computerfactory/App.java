package computerfactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 *
 */
public class App {

	private static boolean isDayOver = false;

	public static void main(String[] args) {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Work day finished!");
				timer.cancel();
				isDayOver = true;
				FactoryEvent.setDayOver(isDayOver);
			}
		}, 60000);
		int motherboardNum = 4, processorNum = 3, hddNum = 3, ramNum = 3;
		FactoryEvent.setEmployees(motherboardNum, processorNum, hddNum, ramNum);
		while (!isDayOver) {
			for (int x = 0; x < motherboardNum; x++) {
				FactoryEvent f = new FactoryEvent();
				if (!isDayOver) {
					f.start();
				} else {
					System.out.println("Computers made: " + FactoryEvent.getComputersMade());
					System.out.println("Total discarded parts: " + FactoryEvent.getDiscardedParts());
					System.exit(0);
				}

			}
		}
		System.out.println("Computers made: " + FactoryEvent.getComputersMade());
	}
}
