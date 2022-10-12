package src.org.mervegundogmus.capstoneproject.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;

import src.org.mervegundogmus.capstoneproject.entity.Chef;
import src.org.mervegundogmus.capstoneproject.entity.Customer;
import src.org.mervegundogmus.capstoneproject.entity.Waiter;

/**
 * @author <a href="mailto:mervegundogmus@outlook.com">Merve Gundogmus</a>
 *         <p>
 *         For more info please
 * @see <a href="http://www.github.com/mervegundogmus">http://www.github.com/mervegundogmus</a>
 *      </p>
 */

public class Restaurant implements Runnable {

	private ArrayList<Chef> chefs;
	private ArrayList<Waiter> waiters;
	private HashMap<Customer, Long> customersWithEnterTime;
	private HashMap<Customer, String> orderLog; /** Sipariş listesi */
	private HashMap<Customer, String> cookLog; /** Pişirilme sırasında olan ya da pişirilen sipariş */
	private HashMap<Customer, String> readyToServeOrders;
	private int chefCount = 2;
	private int waiterCount = 3;
	private int tableCount = 6;
	private int customerCounter = 0;
	private long maxTimeForSitting = 100000L;
	private Calendar calendar;
	private Thread thread;

	public Restaurant(int chefCount, int waiterCount, int tableCount, long maxTimeForSitting) {
		this.chefs = new ArrayList<>();
		this.waiters = new ArrayList<>();
		this.customersWithEnterTime = new HashMap<>();
		readyToServeOrders = new HashMap<>();
		cookLog = new HashMap<>();
		calendar = new GregorianCalendar();
		orderLog = new HashMap<>();
		this.chefCount = chefCount;
		this.waiterCount = waiterCount;
		this.tableCount = tableCount;
		this.maxTimeForSitting = maxTimeForSitting;
	}

	public Restaurant() {
		orderLog = new HashMap<>();
		chefs = new ArrayList<>();
		waiters = new ArrayList<>();
		calendar = new GregorianCalendar();
		customersWithEnterTime = new HashMap();
		cookLog = new HashMap<>();
		readyToServeOrders = new HashMap<>();
	}
	
/**	Random üetilen iki sayıdan hangisi büyük kontrol edilir. */
	public boolean isGoodForNewCustomer() {

		Random rand = new Random();
		return rand.nextBoolean();
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, "Restaurant");
		}
		thread.start();
	}

	@Override
	public void run() {

		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		System.out.println("Mr.Krabs: Welcome to The Krusty Krab!");

		for (int i = 0; i < chefCount; i++) {
			chefs.add(new Chef("Sponge Bob-" + (i + 1), this));
		}

		for (int i = 0; i < waiterCount; i++) {
			waiters.add(new Waiter("Squidward-" + (i + 1), this));
		}

		while (true) {
/**	 Yeterli müşteri yoksa yeni müşteri kabul edilir.*/
			if (customersWithEnterTime.size() < tableCount) {  /** Müşteri ekleme */
				if (isGoodForNewCustomer()) {
					Customer newCustomer = new Customer("Customer-" + (customerCounter++));
					newCustomer.start();
					customersWithEnterTime.put(newCustomer, calendar.getTimeInMillis());
				}
			}

			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	private boolean isTimeToGo(Long entryTime) {
		return(calendar.getTimeInMillis() - entryTime)>maxTimeForSitting;  //Şimdiki saat - giriş saati = 10000L
	}
	
	public ArrayList<Chef> getChefs(){
		return chefs;
	}
	
	public void setChefs(ArrayList<Chef> chefs) {
		this.chefs = chefs;
	}
	
	public ArrayList<Waiter> getWaiters(){
		return waiters;
	}
	
	public void setWaiters(ArrayList<Waiter> waiters) {
		this.waiters = waiters;
	}
	
	public HashMap<Customer, Long> getCustomersWithEnterTime(){
		return customersWithEnterTime;
	}
	
	public void setCustomersWithEnterTime(HashMap<Customer, Long> customersWithEnterTime) {
		this.customersWithEnterTime = customersWithEnterTime;
	}
	
	public int getChefCount() {
		return chefCount;
	}
	
	public void setChefCount(int chefCount) {
		this.chefCount = chefCount;
	}
	
	public int getWaiterCount() {
		return waiterCount;
	}
	
	public void setWaiterCount(int waiterCount) {
		this.waiterCount = waiterCount;
	}
	
	public int getTableCount() {
		return tableCount;
	}
	
	public void setTableCount(int tableCount) {
		this.tableCount = tableCount;
	}
	
	public long getMaxTimeForSitting() {
		return maxTimeForSitting;
	}
	
	public void setMaxTimeForSitting(long maxTimeForSitting) {
		this.maxTimeForSitting = maxTimeForSitting;
	}
	
	public HashMap<Customer, String> getOrderLog(){
		return orderLog;
	}
	
	public HashMap<Customer, String> getReadyToServeOrders(){
		return readyToServeOrders;
	}
	
	public HashMap<Customer, String> getCookLog(){
		return cookLog;
	}

}
