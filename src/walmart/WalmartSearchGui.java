package walmart;

import java.awt.BorderLayout;
import javax.inject.Inject;
import javax.swing.JFrame;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class WalmartSearchGui extends JFrame {

	private static final long serialVersionUID = 1L; // default

	// private double totalCount;
	// private int index;

	@Inject
	public WalmartSearchGui(WalmartComponents components) {
		setTitle("Walmart Search");
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		add(components, BorderLayout.CENTER);

		setVisible(true);
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new WalmartModule());
		WalmartSearchGui walmartGui = injector
				.getInstance(WalmartSearchGui.class);

	}
}