package com.antawine.assessment.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.antawine.assessment.AssessmentUtil;

public class Cart {

	private final Map<Item, Integer> bag;
	private BigDecimal subTotal = AssessmentUtil.getEquivalentBigDecimal(0.0);
	private BigDecimal total = AssessmentUtil.getEquivalentBigDecimal(0.0);
	private final Map<Discount, Integer> discountMap = new HashMap<>();

	public Cart() {
		bag = new LinkedHashMap<>();
	}

	public void add(final Item item) {
		add(item, 1);
	}

	public void add(final Item item, final int quantity) {
		if (quantity > 0) {
			final int previousQuantity = bag.containsKey(item) ? bag.get(item) : 0;
			bag.put(item, previousQuantity + quantity);
			updateSubtotal(item, quantity);
		}
	}

	private strictfp void updateSubtotal(final Item item, final int quantity) {
		total = subTotal = subTotal.add(item.getPrice().multiply(AssessmentUtil.getEquivalentBigDecimal(quantity)));
	}

	@Override
	public String toString() {
		final String line = "-------------------------------------------\n";
		final StringBuffer sb = new StringBuffer("\n");
		sb.append(line).append("OUTPUT\n").append(line);
		sb.append("Subtotal: £").append(subTotal).append("\n");
		if (discountMap.isEmpty()) {
			sb.append("(No offers available)\n");
		}
		discountMap.keySet().stream().forEach(disc -> {
			sb.append(disc.getItemName()).append(" ").append(disc.getDiscountPercentage()).append("% off: -£").append(disc.getDiscountAmount());
			if (discountMap.get(disc) > 1) {
				sb.append("\t\t(").append(discountMap.get(disc)).append(" ").append(disc.getItemName()).append("'s qualified for promotion)");
			}
			sb.append("\n");
		});
		
		sb.append("Total: £").append(total);
		return sb.toString();
	}

	public Map<Item, Integer> getBag() {
		return bag;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public int getQuantity(final String itemName) {
		final Optional<Item> found = bag.keySet().stream().filter(item -> item.getName().equals(itemName)).findAny();
		if (found.isPresent()) {
			return bag.get(found.get());
		}
		return 0;
	}

	public void applyPromotion(final String itemName, final double discountPercentage) {
		final Optional<Item> found = bag.keySet().stream().filter(item -> item.getName().equals(itemName)).findAny();
		BigDecimal totalDiscount = AssessmentUtil.getEquivalentBigDecimal(0.0);
		if (found.isPresent()) {
			final Item item = found.get();
			totalDiscount = item.getPrice().multiply(AssessmentUtil.getEquivalentBigDecimal(discountPercentage)).divide(AssessmentUtil.getEquivalentBigDecimal(100));
			updateDiscountMap(discountPercentage, totalDiscount, item);
		}
		total = total.subtract(totalDiscount);
	}

	public void applyPromotion(final String itemName, final double discountPercentage, final int repeatCount) {
		for (int i = 0; i < repeatCount; i++) {
			applyPromotion(itemName, discountPercentage);
		}
	}

	public void applyPromotion(final String itemName, final double discountPercentage, final int repeatCount, final LocalDate validTill) {
		if (LocalDate.now().isBefore(validTill) || LocalDate.now().isEqual(validTill)) {
			applyPromotion(itemName, discountPercentage, repeatCount);
		}
	}

	private void updateDiscountMap(final double discountPercentage, final BigDecimal totalDiscount, final Item item) {
		if (totalDiscount.doubleValue() != 0.0) {
			final Optional<Discount> existingDiscount = discountMap.keySet().stream().filter(t -> t.getItemName().equals(item.getName())).findFirst();
			if (existingDiscount.isPresent()) {
				final Discount discount = existingDiscount.get();
				final int noOfAppliedDiscount = discountMap.get(discount) + 1;
				final BigDecimal accumulatedDiscount = discount.getDiscountAmount().add(totalDiscount);
				discountMap.remove(discount);
				discountMap.put(new Discount(item.getName(), discountPercentage, accumulatedDiscount), noOfAppliedDiscount);
			} else {
				discountMap.put(new Discount(item.getName(), discountPercentage, totalDiscount), 1);
			}
		}
	}

}
