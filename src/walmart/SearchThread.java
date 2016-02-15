package walmart;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SearchThread extends Thread {

	private JTextField searchInput;
	private JList<String> resultsList;
	private DefaultListModel<String> listModel;
	private JTextArea description, price, available;
	private Item[] items;
	private String[] itemsString;
	private int index;

	public SearchThread(JTextField searchInput, JList<String> resultsList, DefaultListModel<String> listModel,
			JTextArea description, JTextArea price, JTextArea available) {
		this.searchInput = searchInput;
		this.resultsList = resultsList;
		this.listModel = listModel;
		this.description = description;
		this.price = price;
		this.available = available;
	}

	@Override
	public void run() {
		try {

			listModel.clear(); // refresh list for new searches

			WalmartConnection connection = new WalmartConnection();
			SearchResults results = connection.createWalmartConnection(searchInput.getText().trim());
			items = results.getItems();
			itemsString = new String[items.length + 1];

			for (int i = 0; i < items.length; i++) {
				itemsString[i] = items[i].getName();
			}

			for (String i : itemsString) {
				listModel.addElement(i);
			}

			resultsList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent le) {

					index = resultsList.getSelectedIndex();

					if (index != -1 && le.getValueIsAdjusting() == true) {
						description.setText("Description: " + items[index].getShortDescription());
						available.setText("Available: " + items[index].getAvailableOnline());
						price.setText("Price: $" + items[index].getSalePrice());

					} else {

					}

				}

			});

		} catch (IOException ex1) {
			searchInput.setText("");
		} catch (NullPointerException ex2) {
			listModel.addElement("Reenter item search"); // invalid searches
		}
	}

}