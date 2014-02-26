package client_chat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.text.BadLocationException;

public class Downloaded {

	private Map<String, JPanel> fileReferences = new HashMap<String, JPanel>();

	private final JPanel pnl_main;
	private final JFrame frame;
	private boolean background_color = true;

	private Color LIGHT_LIGHT_GRAY = new Color(243, 243, 243);
	private Color ALMOST_WHITE = new Color(251, 251, 251);
	private Dimension frameSize = new Dimension(300, 85);

	public Downloaded() throws BadLocationException {
		frame = new JFrame();
		frame.setSize(frameSize);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setTitle("Downloads");
		frame.setLocationRelativeTo(null);
		pnl_main = new JPanel();
		pnl_main.setBackground(Color.WHITE);
		frame.getContentPane().add(pnl_main);
		BoxLayout a = new BoxLayout(pnl_main, BoxLayout.Y_AXIS);

		pnl_main.setLayout(a);
		pnl_main.setAlignmentX(Component.LEFT_ALIGNMENT);

		frame.setBackground(Color.WHITE);

	}

	private JPanel buildFilePanel(String fileName, int max) {

		JPanel container = new JPanel();
		container.setAlignmentX(Component.LEFT_ALIGNMENT);
		container.setMaximumSize(new Dimension(400, 55));
		container.setLayout(null);
		container.setName("container");
		if (background_color == false) {
			container.setBackground(LIGHT_LIGHT_GRAY);
		} else {
			container.setBackground(ALMOST_WHITE);
		}
		background_color = !background_color;

		JLabel fileicon = new JLabel();
		fileicon.setIcon(new ImageIcon("resources/file.png"));
		fileicon.setLocation(5, 15);
		fileicon.setSize(fileicon.getPreferredSize());
		fileicon.setName("fileicon");
		container.add(fileicon);

		JLabel filename = new JLabel(fileName);
		filename.setLocation(50, 15);
		filename.setSize(filename.getPreferredSize());
		filename.setName("filename");
		container.add(filename);

		JProgressBar progressbar = new JProgressBar(0, max);
		progressbar.setStringPainted(true);
		progressbar.setLocation(50, 35);
		progressbar.setSize(200, 15);
		progressbar.setName("progressbar");
		container.add(progressbar);

		return container;
	}

	public static class FileReference {
		public FileReference() {

		}
	}

	public boolean updateProgressBar(String filename, int percentage) {
		JPanel pnl = fileReferences.get(filename);

		if (pnl == null) {
			return false;
		} else {
			for (Component ccommp : pnl.getComponents()) {
				if (ccommp.getName() == "progressbar") {
					JProgressBar bar = (JProgressBar) ccommp;

					bar.setValue(bar.getValue() + percentage);
				}

			}
			return true;
		}
	}

	public synchronized void addFile(String filename, int max) throws Exception {
		if (fileReferences.get(filename) != null) {
			throw new Exception("File Already in list, duplicate!");
		}

		JPanel local_pnl = buildFilePanel(filename, max);
		fileReferences.put(filename, local_pnl);
		pnl_main.add(local_pnl);
		frameSize.height += 55;
		frame.setSize(frameSize);
	}

	public void showFrame(boolean show) {
		frame.setVisible(show);
	}

	public boolean isVisible() {
		return frame.isVisible();
	}

	public synchronized void eraseFile(String filename) {
		JPanel pnl = fileReferences.get(filename);
		pnl_main.remove(pnl);
		fileReferences.remove(filename);
		redraw_colors();
	}

	private void redraw_colors() {
		background_color = true;
		for (JPanel cpanel : fileReferences.values()) {

			if (background_color == false) {
				cpanel.setBackground(LIGHT_LIGHT_GRAY);
			} else {
				cpanel.setBackground(ALMOST_WHITE);
			}
			background_color = !background_color;
		}
	}

}