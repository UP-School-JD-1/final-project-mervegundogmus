package src.org.mervegundogmus.capstoneproject.entity;

import java.util.HashMap;
import java.util.Map;

import src.org.mervegundogmus.capstoneproject.business.Restaurant;

/**
 * @author <a href="mailto:mervegundogmus@outlook.com">Merve Gundogmus</a>
 *         <p>
 *         For more info please
 * @see <a href="http://www.github.com/mervegundogmus">http://www.github.com/mervegundogmus</a>
 *      </p>
 */
public class Waiter extends Employee implements Runnable {

	private Restaurant restaurant;
	private HashMap<Customer, String> orders;
	private boolean isTimeToGo = false;
	private Thread thread;

	public Waiter(String name, Restaurant restaurant) {
		this.name = name;
		orders = new HashMap<>();
		this.restaurant = restaurant;
		start();
	}

	public HashMap<Customer, String> getOrders() {
		return orders;
	}

	public boolean getIsTimeToGo() {
		return isTimeToGo;
	}

	public void setIsTimeToGo(boolean timeToGo) {
		isTimeToGo = timeToGo;
	}

	public void setPriority(int newPro) {
		Thread.currentThread().setPriority(newPro);
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, name);
		}
		thread.start();
	}

	@Override
	public void run() {

		System.out.println(name + " is ready!");

		while (true) {

			/** Müşterilerden sipariş alınması */
			Customer curCustomerForOrder = null;
			synchronized (restaurant.getCustomersWithEnterTime()) {
				for (Customer customer : restaurant.getCustomersWithEnterTime().keySet()) {
					if (!restaurant.getOrderLog().containsKey(customer)) {
						curCustomerForOrder = customer;
						restaurant.getOrderLog().put(curCustomerForOrder, "");
						break;

					}
				}
			}

			if (curCustomerForOrder != null) {
				/**
				 * Siparişi alınmayan müşterinin siparişi alınır. 
				 * İlgili müşterinin siparişine erişilmesini engelliyorum.
				 */
				synchronized (restaurant.getOrderLog().get(curCustomerForOrder)) {

					/** Sipariş almaya giderken thread'i belirli bir süre uyutuyoruz. */
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					restaurant.getOrderLog().put(curCustomerForOrder, curCustomerForOrder.toOrder());
					System.out.println(name + ": " + curCustomerForOrder.getName() + "'s order has been received.");
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			Map.Entry<Customer, String> curOrderForServe = null;
			/** Hazırlanan siparişleri teslim etme */
			synchronized (restaurant.getReadyToServeOrders()) {
				/** Aynı siparişi iki farklı garson teslim etmesin diye */
				for (Map.Entry<Customer, String> order : restaurant.getReadyToServeOrders().entrySet()) {
					curOrderForServe = order;
					restaurant.getReadyToServeOrders().remove(order.getKey());
					break;
				}
			}

			if (curOrderForServe != null) {

				/** Siparişi teslim etmeye gidiyor gibi thread'i bir süre uyutuyoruz. */
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				curOrderForServe.getKey().setIsMyMealServed(true); /** Müşterinin yemeği servis edildi. */
				System.out.println(name + ": " + curOrderForServe.getKey().getName() + "'s order has been served.");
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/**
				 * Müşteri şimdiye kadar kalkmamışsa kalmasını engelleyip garsonla gönderiyorum.
				 */
				synchronized (curOrderForServe.getKey()) {
					if (!curOrderForServe.getKey().getIsTimeToGo()) {
						curOrderForServe.getKey().setIsTimeToGo(true);
						System.out.println(name + ": " + curOrderForServe.getKey().getName()
								+ " was told to leave as there was no room left.");
					}
				}
				/** Müşteri çıkış yaptı. */
				restaurant.getCustomersWithEnterTime().remove(curOrderForServe.getKey()); 

			}

			/** Dinlenme */
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
