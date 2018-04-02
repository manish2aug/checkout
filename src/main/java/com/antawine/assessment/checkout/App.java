package com.antawine.assessment.checkout;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

import com.antawine.assessment.exception.InvalidInputException;
import com.antawine.assessment.exception.NotFoundException;
import com.antawine.assessment.model.Cart;
import com.antawine.assessment.model.Item;
import com.antawine.assessment.persistence.ItemRepositoryImpl;
import com.antawine.assessment.persistence.Repository;

public class App {

	private Cart cart;

	public static void main(final String... input) throws Exception {
		final App app = new App();
		app.processCheckout(input);
		System.out.println(app.getCart());
	}
	
	public void processCheckout(final String... input) throws SQLException, InvalidInputException, NotFoundException {
		updateCart(input);
		applyPromotions();
	}
	
	private void applyPromotions() {
		final KieSession session = KieServices.Factory.get().getKieClasspathContainer().newKieSession();
		session.insert(cart);
		session.fireAllRules();
	}

	private void updateCart(final String[] input) throws SQLException, InvalidInputException, NotFoundException {
		final String msg = null;

		Objects.requireNonNull(input, "Invalid input supplied");
		if (input.length < 2) {
			throw new InvalidInputException("Bad Request - At least two arguments required.");
		}
		if (!input[0].equals("AnatwineBasket")) {
			throw new InvalidInputException("Bad Request - First argument should be AnatwineBasket.");
		}
		if (Objects.nonNull(msg)) {
			throw new InvalidInputException(msg);
		}
		containesInvalidItems(Arrays.copyOfRange(input, 1, input.length));
	}

	private void containesInvalidItems(final String[] inputs) throws SQLException, NotFoundException {
		final Repository<Item> repo = new ItemRepositoryImpl();
		for (final String input : inputs) {
			final Item item = repo.findByName(input);
			if (item == null) {
				throw new NotFoundException("Bad Request - No item found in databse with name: " + input);
			}
			if (Objects.isNull(cart)) {
				cart = new Cart();
			}
			cart.add(item);
		}
	}
	
	public Cart getCart() {
		return cart;
	}
	
}
