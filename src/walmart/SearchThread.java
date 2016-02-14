package walmart;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;

public class SearchThread extends Thread {

	private JTextField searchInput;
	private DefaultListModel<String> listModel;

	public SearchThread(JTextField searchInput, JList<String> resultsList, DefaultListModel<String> listModel) {
		this.searchInput = searchInput;
		this.listModel = listModel;
	}

	@Override
	public void run() {
		try {

			listModel.clear(); // refresh list for new searches

			WalmartConnection connection = new WalmartConnection();
			SearchResults results = connection.createWalmartConnection(searchInput.getText().trim());
			Item[] items = results.getItems();
			String[] itemsString = new String[items.length + 1];

			for (int i = 0; i < items.length; i++) {
				itemsString[i] = items[i].toString();
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