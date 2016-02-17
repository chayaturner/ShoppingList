package walmart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WalmartSearchGui extends JFrame {

	private static final long serialVersionUID = 1L; // default

	private JLabel search, logoLabel;
	private JTextArea description, price, available;
	private JList<String> resultsList;
	private JList<String> shoppingList;
	private DefaultListModel<String> resultsListModel, shoppingListModel;
	private JButton searchButton, addButton;
	private JTextField searchInput;
	private JPanel topPanel, bottomPanel, centerPanel, searchPanel, productDetails, resultsPanel;
	private LineBorder border;
	private ImageIcon logo;
	private Item[] items;
	private int index;

	public WalmartSearchGui() {
		setTitle("Walmart Search");
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		Container container = getContentPane();
		setLayout(new BorderLayout());

		Color wmBlue = new Color(65, 105, 250);
		Color wmOrange = new Color(240, 160, 0);
		Color lightBlue = new Color(173, 216, 230);
		border = new LineBorder(Color.BLACK);

		// CENTER
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.LINE_AXIS));
		centerPanel.setBackground(lightBlue);

		shoppingListModel = new DefaultListModel<String>();
		shoppingList = new JList<String>(shoppingListModel);
		shoppingList.setForeground(wmBlue);
		shoppingList.setBackground(new Color(255, 231, 186));
		shoppingList.setPreferredSize(new Dimension(200, Short.MAX_VALUE));
		shoppingList.setMaximumSize(new Dimension(200, Short.MAX_VALUE));
		String title = "   SHOPPING LIST!";
		shoppingListModel.addElement(title);
		shoppingListModel.addElement("____________________________");
		shoppingList.setBorder(border);
		centerPanel.add(shoppingList);

		resultsPanel = new JPanel(new GridLayout(2, 1));
		resultsPanel.setBackground(lightBlue);

		resultsListModel = new DefaultListModel<String>();
		resultsList = new JList<String>(resultsListModel);
		resultsList.setForeground(new Color(0, 0, 139));
		resultsList.setBackground(lightBlue);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);
		resultsList.setVisibleRowCount(-1);

		// set details when item in list is clicked:
		index = resultsList.getSelectedIndex();

		resultsList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent le) {

				if (index != -1 && !le.getValueIsAdjusting()) {
					description.setText("Description: " + items[index].getShortDescription());
					available.setText("Available: " + items[index].getAvailableOnline());
					price.setText("Price: $" + items[index].getSalePrice());

				} else if (index == -1) {
					description.setText("list selection not working properly");
				}

			}
		});

		productDetails = new JPanel(new GridLayout(4, 1));
		productDetails.setBackground(lightBlue);
		description = new JTextArea("Description: ");
		description.setLineWrap(true);
		price = new JTextArea("Price: ");
		price.setLineWrap(true);
		available = new JTextArea("Available: ");
		available.setLineWrap(true);
		description.setForeground(wmBlue);
		price.setForeground(wmBlue);
		available.setForeground(wmBlue);
		productDetails.add(description);
		productDetails.add(available);
		productDetails.add(price);
		resultsPanel.add(resultsList);
		resultsPanel.add(productDetails);
		centerPanel.add(resultsPanel);

		container.add(centerPanel, BorderLayout.CENTER);

		// NORTH
		topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		search = new JLabel("Search Walmart.com:");
		search.setForeground(wmOrange);
		searchInput = new JTextField("                                   ");
		searchInput.setBorder(new LineBorder(wmBlue));
		searchInput.setForeground(wmBlue);
		logo = new ImageIcon("logo.png");
		logoLabel = new JLabel(logo);
		searchButton = new JButton("Search");
		searchButton.setForeground(wmOrange);
		searchButton.setSize(50, 30);
		searchPanel = new JPanel(new GridLayout(3, 1));
		searchPanel.setBackground(Color.WHITE);
		searchPanel.add(search);
		searchPanel.add(searchInput);
		searchPanel.add(searchButton);

		searchButton.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// reset old searches
				resultsListModel.clear();

				description.setText("Description: ");
				price.setText("Price: ");
				available.setText("Available: ");

				// get a new search result list, set items.
				Thread thread = new SearchThread(searchInput, resultsListModel, items);
				thread.start();

			}
		});

		topPanel.add(logoLabel);
		topPanel.add(searchPanel);
		container.add(topPanel, BorderLayout.NORTH);

		// SOUTH
		bottomPanel = new JPanel();
		bottomPanel.setBackground(lightBlue);
		container.add(bottomPanel, BorderLayout.SOUTH);
		addButton = new JButton("Like it? Add to shopping list!");
		addButton.setForeground(Color.BLUE);
		addButton.setBackground(wmOrange);
		bottomPanel.add(addButton);

		addButton.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// add to the list
				shoppingListModel.addElement(resultsList.getSelectedValue());
			}

		});

	}

	public static void main(String[] args) {
		WalmartSearchGui walmartGui = new WalmartSearchGui();
		walmartGui.setVisible(true);
	}
}