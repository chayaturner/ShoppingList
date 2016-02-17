package walmart;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;

public class SearchThread extends Thread {

	private JTextField searchInput;
	private DefaultListModel<String> listModel;
	private Item[] items;
	private String[] itemsString;

	public SearchThread(JTextField searchInput, DefaultListModel<String> listModel, Item[] items) {
		this.searchInput = searchInput;
		this.listModel = listModel;
		this.items = items;

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

		} catch (IOException ex1) {
			searchInput.setText("");
		} catch (NullPointerException ex2) {
			listModel.addElement("Reenter item search"); // invalid searches
		}
	}

}