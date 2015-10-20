import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	private static GameOfLife grid;

	public void paint(Graphics g) {
		Image img = grid.createImage();
		g.drawImage(img, 0, 0, 720, 720, this);
	}

	public static void main(String[] args) {
		grid = new GameOfLife(360, 360);
		JFrame frame = new JFrame("Game of life");
		frame.getContentPane().add(new Game());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(720, 720);
		frame.setVisible(true);
		frame.setResizable(false);
		while (true) {
			// Wait 10ms
			try {
				Thread.sleep(10); // 1000 milliseconds is one second.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			grid.update();
			frame.repaint();
		}
	}
}
