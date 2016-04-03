package walmart;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;

public class SearchThread extends Thread {

	private JTextField searchInput;
	private DefaultListModel<String> listModel;
	private Item[] items;

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

			for (int i = 0; i < items.length; i++) {
				// listModel.addElement(items[i].getName());
				listModel.addElement(items[i].toString());
			}

		} catch (IOException ex1) {
			searchInput.setText("");
			System.out.println("ERROR!!");
		} catch (NullPointerException ex2) {
			listModel.addElement("Reenter item search"); // invalid searches
			System.out.println("ERROR2");
		}
	}

}