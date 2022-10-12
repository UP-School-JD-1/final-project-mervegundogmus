package org.mervegundogmus.capstoneproject.entity;

import java.util.HashMap;
import java.util.Map;

import org.mervegundogmus.capstoneproject.business.Restaurant;

/**
 * @author <a href="mailto:mervegundogmus@outlook.com">Merve Gundogmus</a>
 *         <p>
 *         For more info please
 * @see <a href="http://www.github.com/mervegundogmus">http://www.github.com/mervegundogmus</a>
 *      </p>
 */
public class Chef extends Employee implements Runnable {

	Thread thread;
	HashMap<Customer, String> orders;
	Restaurant restaurant;

	public Chef(String name, Restaurant restaurant) {
		this.name = name;
		orders = new HashMap<>();
		this.restaurant = restaurant;
		start(); 
	}

	public void setPriority(int newPriority) {
		Thread.currentThread().setPriority(newPriority);
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
		
		while(true) {
			Map.Entry<Customer, String> curOrder = null; /** Hazırlanmak için alınan sipariş */
			
/**	Siparişlere aynı anda tek bir kişinin bakmasını sağlıyorum,
 * 	çünkü iki farklı kişi aynı siparişi hazırlamak için almasın. 
 */			
			synchronized(restaurant.getOrderLog()) {
				for(Map.Entry<Customer, String> order: restaurant.getOrderLog().entrySet()) {
					if(!restaurant.getCookLog().containsKey(order.getKey())) {
						curOrder = order;
						restaurant.getCookLog().put(curOrder.getKey(),curOrder.getValue());{
							break;
/**	Yemek hazırlama işlemi bu blokta yapılmıyor,
 * 	çünkü bu blok diğer şef ve garsonların orderLog'a erişmesini engelliyor.
 */							
						}
					}
				}
				
			if(curOrder != null) { /** Eğer sipariş alındıysa pişirilir.*/
				
/** Yemek hazırlanıyormuş gibi 2-5 sn arası thread'i bir süreliğine beklet. */		
				try {
					Thread.sleep(2500);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(name + ": " + curOrder.getKey().getName() + "'s (" + curOrder.getValue() + ") order has been prepared.");
				restaurant.getReadyToServeOrders().put(curOrder.getKey(), curOrder.getValue());			}
			}

/** Dinlenme */			
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
