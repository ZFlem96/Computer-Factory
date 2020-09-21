package computerfactory;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class FactoryEvent extends Thread {
	private static Calendar today;
	private static Semaphore motherboardEmployeeSemaphore, processorEmployeeSemaphore, hddEmployeeSemaphore,
			ramEmployeeSemaphore;
	private static int computersMade = 0, discardedParts = 0;
	private static boolean isDayOver = false;

	public static void setEmployees(int employeeNum1, int employeeNum2, int employeeNum3, int employeeNum4) {
		FactoryEvent.motherboardEmployeeSemaphore = new Semaphore(employeeNum1, true);
		FactoryEvent.processorEmployeeSemaphore = new Semaphore(employeeNum2, true);
		FactoryEvent.hddEmployeeSemaphore = new Semaphore(employeeNum3, true);
		FactoryEvent.ramEmployeeSemaphore = new Semaphore(employeeNum4, true);
	}

	public static int getComputersMade() {
		return computersMade;
	}

	public static int getDiscardedParts() {
		return discardedParts;
	}

	public static boolean isDayOver() {
		return isDayOver;
	}

	public static void setDayOver(boolean isDayOver) {
		FactoryEvent.isDayOver = isDayOver;
	}

	private void installingMotherboard() {
		Random rand = new Random();
		double randValue = rand.nextDouble();
		final long serviceTime = (long) (-Math.log((1 - randValue)) * 10000);
		try {
			Thread.sleep(serviceTime);
			FactoryEvent.today = Calendar.getInstance();
			FactoryEvent.today.set(Calendar.HOUR_OF_DAY, 0);
			if (!FactoryEvent.isDayOver) {
				System.out.println("Motherboard installed. (Work day time: " + (today.getTime()) + " )");

			} else {
				System.out.println("Motherboard discarded. (Work day time: " + (today.getTime()) + " )");
				discardedParts++;

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertProcessor() {
		Random rand = new Random();
		double randValue = rand.nextDouble();
		final long serviceTime = (long) (-Math.log((1 - randValue)) * 2000);
		try {
			Thread.sleep(serviceTime);
			FactoryEvent.today = Calendar.getInstance();
			FactoryEvent.today.set(Calendar.HOUR_OF_DAY, 0);
			if (!FactoryEvent.isDayOver) {
				System.out.println("Processor inserted. (Work day time: " + (today.getTime()) + " )");
			} else {
				System.out.println("Processor discarded. (Work day time: " + (today.getTime()) + " )");
				discardedParts++;

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertHDD() {
		Random rand = new Random();
		double randValue = rand.nextDouble();
		final long serviceTime = (long) (-Math.log((1 - randValue)) * 1000);
		try {
			Thread.sleep(serviceTime);
			FactoryEvent.today = Calendar.getInstance();
			FactoryEvent.today.set(Calendar.HOUR_OF_DAY, 0);
			if (!FactoryEvent.isDayOver) {
				System.out.println("HDD inserted. (Work day time: " + (today.getTime()) + " )");
			} else {
				System.out.println("HDD discarded. (Work day time: " + (today.getTime()) + " )");
				discardedParts++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertRAM() {
		Random rand = new Random();
		double randValue = rand.nextDouble();
		final long serviceTime = (long) (-Math.log((1 - randValue)) * 450);
		try {
			Thread.sleep(serviceTime);
			FactoryEvent.today = Calendar.getInstance();
			FactoryEvent.today.set(Calendar.HOUR_OF_DAY, 0);
			computersMade++;
			if (!FactoryEvent.isDayOver) {
				System.out.println("RAM inserted. Computer completed. Number of computers made: " + computersMade
						+ ". (Work day time: " + (today.getTime()) + " )");
			} else {
				System.out.println("RAM discarded. (Work day time: " + (today.getTime()) + " )");
				discardedParts++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		// int x = computersMade;
		if (!isDayOver) {
			try {
				motherboardEmployeeSemaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				installingMotherboard();
				try {
					processorEmployeeSemaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					insertProcessor();
					try {
						hddEmployeeSemaphore.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						insertHDD();
						try {
							ramEmployeeSemaphore.acquire();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						try {
							insertRAM();
							// x++;
							// computersMade = x;
						} finally {
							ramEmployeeSemaphore.release();
						}
					} finally {
						hddEmployeeSemaphore.release();
					}
				} finally {
					processorEmployeeSemaphore.release();
				}
			} finally {
				motherboardEmployeeSemaphore.release();
			}
		}
	}

}
