package walmart;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;

public class SearchThread extends Thread {

	private JTextField searchInput;
	private JList<Item> listModel;
	private Item[] items;

	public SearchThread(JTextField searchInput, JList<Item> listModel,
			Item[] items) {
		this.searchInput = searchInput;
		this.listModel = listModel;
		this.items = items;
	}

	@Override
	public void run() {
		try {

			listModel.clearSelection(); // refresh list for new searches

			WalmartConnection connection = new WalmartConnection();
			SearchResults results = connection
					.createWalmartConnection(searchInput.getText().trim());
			items = results.getItems();
			listModel.setListData(items);

		} catch (IOException ex1) {
			searchInput.setText("");
		} catch (NullPointerException ex2) {
			searchInput.setText("Renenter Search");
		}
	}

	

	public Item[] getItems() {
		return this.items;
	}
}