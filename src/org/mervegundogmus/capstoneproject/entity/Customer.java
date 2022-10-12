package org.mervegundogmus.capstoneproject.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:mervegundogmus@outlook.com">Merve Gundogmus</a>
 *         <p>
 *         For more info please
 * @see <a href="http://www.github.com/mervegundogmus">http://www.github.com/mervegundogmus</a>
 *      </p>
 */
public class Customer implements Runnable {

	private String name;
	private String order = null;
	private List<String> foods = Arrays.asList("Krabby Pattie, Seafoam Soda", "Krusty Deluxe, Coke", "Seaweed Salad",
			"Krabby Fries", "Kelp Shake", "Seaweed Salad", "Krabby Pattie, Kelp Shake","Jelly Pancake, Coffee",
			"Krusty Krab Pizza, Coke", "Krusty Kid's Meal", "Coffee", "Krusty Deluxe", "Jelly Pancake");
	private boolean isTimeToGo = false;
	private boolean isMyMealServed = false;
	private Thread thread;
 
	public Customer(String name) {
		this.name = name;
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, name);
		}
		thread.start();
	}

	public String getName() {
		return name;
	}

	public void setIsMyMealServed(boolean isServed) {
		this.isMyMealServed = isServed;
	}

	public boolean getIsTimeToGo() {
		return isTimeToGo;
	}

	public void setIsTimeToGo(Boolean isTimeToGo) {
		this.isTimeToGo = isTimeToGo;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	/** Sipariş verme */
	public String toOrder() {

		if (order == null) {
			Random rand = new Random();
			int randIndex = rand.nextInt(foods.size());
			order = foods.get(randIndex);
		}
		return order;
	}

	@Override
	public void run() {
		System.out.println(name + " entered the restaurant.");

		while (!isTimeToGo) {

			if (isMyMealServed) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(name + ": The food was very tasty.");
				/** Yemek yeme işleminin tekrar edilmemesi */
				isMyMealServed = false;

				/** Yemek bittikten sonra çıkış yapacak mı?
				 *  Müşteri kendisi kalkar ya da garson bildirir.
				 */
				if (isEnoughToSit()) {
					isTimeToGo = true;
				}
			}
		}

		System.out.println(name + " checked out of the restaurant.");
	}

	private boolean isEnoughToSit() {
		Random rand = new Random();
		return rand.nextBoolean();
	}

}
