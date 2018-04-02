package com.antawine.assessment.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.antawine.assessment.exception.InvalidInputException;
import com.antawine.assessment.exception.NotFoundException;

public class AppTest {
	
	// Failure scenarios
	@Test
	@DisplayName("Failure Scenario: Passed null as argument")
	public final void testMainWithNullArgument() throws Exception {
		assertEquals("Invalid input supplied", assertThrows(NullPointerException.class, () -> App.main(null)).getMessage());
	}
	
	@Test
	@DisplayName("Failure Scenario: Passed empty array as argument")
	public final void testMainWithEmptyArgument() throws Exception {
		assertEquals("Bad Request - At least two arguments required.", assertThrows(InvalidInputException.class, () -> App.main(new String[] {})).getMessage());
	}
	
	@Test
	@DisplayName("Failure Scenario: Passed no argument")
	public final void testMainWithNoArgument() throws Exception {
		assertEquals("Bad Request - At least two arguments required.", assertThrows(InvalidInputException.class, () -> App.main()).getMessage());
	}
	
	@Test
	@DisplayName("Failure Scenario: Passed one valid argument")
	public final void testMainWithOneValidArgument() throws Exception {
		assertEquals("Bad Request - At least two arguments required.", assertThrows(InvalidInputException.class, () -> App.main("AnatwineBasket")).getMessage());
	}
	
	@Test
	@DisplayName("Failure Scenario: Passed one invalid argument")
	public final void testMainWithOneInvalidArgument() throws Exception {
		assertEquals("Bad Request - At least two arguments required.", assertThrows(InvalidInputException.class, () -> App.main("test")).getMessage());
	}
	
	@Test
	@DisplayName("Failure Scenario: Passed two arguments, first invalid")
	public final void testMainWithTwoArgumentsFirstInvalid() throws Exception {
		assertEquals("Bad Request - First argument should be AnatwineBasket.", assertThrows(InvalidInputException.class, () -> App.main("Anatwine", "Tie")).getMessage());
	}
	
	@Test
	@DisplayName("Failure Scenario: Passed two arguments, non existing product")
	public final void testMainWithTwoArgumentsWithWrongProduct() throws Exception {
		assertEquals("Bad Request - No item found in databse with name: Tshirt", assertThrows(NotFoundException.class, () -> App.main("AnatwineBasket", "Tshirt")).getMessage());
	}
	
	// Success scenarios
	@Test
	@DisplayName("Success Scenario: Passed two valid arguments with no offer applicable")
	public final void testMainWithValidArgumentsHavingNoOffer() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Tie");
		assertTrue(cartRepresentation.contains("Subtotal: £9.50"));
		assertTrue(cartRepresentation.contains("(No offers available)"));
		assertTrue(cartRepresentation.contains("Total: £9.50"));
	}
	
	@Test
	@DisplayName("Success Scenario: To test Shirt promotion (Single promotion)")
	public final void testMainWithValidArgumentsHavingShirtPromotion() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Shirt", "Shirt", "Tie");
		assertTrue(cartRepresentation.contains("Subtotal: £34.50"));
		assertTrue(cartRepresentation.contains("Tie 50.0% off: -£4.75"));
		assertTrue(cartRepresentation.contains("Total: £29.75"));
	}
	
	@Test
	@DisplayName("Success Scenario: To test Trouser promotion (Single promotion)")
	public final void testMainWithValidArgumentsHavingTrouserPromotion() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Trouser");
		assertTrue(cartRepresentation.contains("Subtotal: £35.50"));
		assertTrue(cartRepresentation.contains("Trouser 10.0% off: -£3.55"));
		assertTrue(cartRepresentation.contains("Total: £31.95"));
	}
	
	@Test
	@DisplayName("Success Scenario: To test Trouser and Shirt promotion (Multiple promotions)")
	public final void testMainWithValidArgumentsHavingMultiplePromotions() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Trouser", "Shirt", "Shirt", "Tie");
		assertTrue(cartRepresentation.contains("Subtotal: £70.00"));
		assertTrue(cartRepresentation.contains("Trouser 10.0% off: -£3.55"));
		assertTrue(cartRepresentation.contains("Tie 50.0% off: -£4.75"));
		assertTrue(cartRepresentation.contains("Total: £61.70"));
	}
	
	@Test
	@DisplayName("Success Scenario: To test Trouser promotion does not affect Tie price")
	public final void testMainWithValidArgumentsHavingMultipleTrouserPromotions() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Trouser", "Trouser", "Tie");
		assertTrue(cartRepresentation.contains("Subtotal: £80.50"));
		assertTrue(cartRepresentation.contains("Trouser 10.0% off: -£7.1"));
		assertTrue(cartRepresentation.contains("Total: £73.40"));
	}
	
	@Test
	@DisplayName("Success Scenario: To test cart price havin all items")
	public final void testMainWithValidArgumentsHavingAllProducts() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Jacket", "Trouser", "Shirt", "Shirt", "Tie");
		assertTrue(cartRepresentation.contains("Subtotal: £119.90"));
		assertTrue(cartRepresentation.contains("Trouser 10.0% off: -£3.55"));
		assertTrue(cartRepresentation.contains("Tie 50.0% off: -£4.75"));
		assertTrue(cartRepresentation.contains("Total: £111.60"));
	}
	
	@Test
	@DisplayName("Success Scenario: To test single Shirt applicability for promotion")
	public final void testMainWithValidArgumentsHavingOneShirtTwoTie() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Shirt", "Tie", "Tie");
		assertTrue(cartRepresentation.contains("Subtotal: £31.50"));
		assertTrue(cartRepresentation.contains("(No offers available)"));
		assertTrue(cartRepresentation.contains("Total: £31.50"));
	}
	
	@Test
	@DisplayName("Success Scenario: To test single promotion(Shirt) doesn't affect multiple Tie prices")
	public final void testMainWithValidArgumentsHavingTwoShirtTwoTie() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Shirt", "Shirt", "Tie", "Tie");
		assertTrue(cartRepresentation.contains("Subtotal: £44.00"));
		assertTrue(cartRepresentation.contains("Tie 50.0% off: -£4.75"));
		assertTrue(cartRepresentation.contains("Total: £39.25"));
	}
	
	@Test
	@DisplayName("Success Scenario: To test Shirt promotion if there is no Tie bought")
	public final void testMainWithValidArgumentsHavingTwoShirtButNoTie() throws Exception {
		final String cartRepresentation = getCartRepresentation("AnatwineBasket", "Shirt", "Shirt", "Trouser");
		assertTrue(cartRepresentation.contains("Subtotal: £60.50"));
		assertTrue(cartRepresentation.contains("Trouser 10.0% off: -£3.55"));
		assertTrue(cartRepresentation.contains("Total: £56.95"));
	}
	
	private String getCartRepresentation(final String... args) throws Exception {
		final App app = new App();
		app.processCheckout(args);
		return app.getCart().toString();
	}
}
