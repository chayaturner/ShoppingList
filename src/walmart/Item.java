package walmart;

public class Item {

	private String name;
	private double salePrice;
	private String shortDescription;
	private boolean availableOnline;

	public String getName() {
		return name;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public boolean getAvailableOnline() {
		return availableOnline;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append(": $" + salePrice);
		if (availableOnline == true) {
			builder.append(", available online");
		} else if (availableOnline == false) {
			builder.append(", unavailable online");
		}
		return builder.toString();
	}
}
