import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Grid {
	public State[][] grid;
	public int height, width;
	private int gen;

	public Grid(int height, int width) {
		grid = new State[width + 2][height + 2];
		for (int x = 0; x < width + 2; x++) {
			for (int y = 0; y < height + 2; y++) {
				grid[x][y] = State.DEAD;
			}
		}
		this.height = height;
		this.width = width;
		gen = 1;
		initialSetup();
	}

	public void initialSetup() {
		for (int x = 50; x < width - 50; x++) {
			for (int y = 50; y < height - 50; y++) {
				if (Math.random() > 0.5)
					grid[x][y] = State.ALIVE;
			}
		}
	}

	public Image createImage() {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.createGraphics();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (grid[x + 1][y + 1] == State.ALIVE) {
					bufferedImage.setRGB(x, y, 0xFFFFFF);
				} else {
					bufferedImage.setRGB(x, y, 0);
				}
				;
			}
		}
		g.setColor(Color.WHITE);
		g.drawString("CURRENT GENERATION: " + Integer.toString(gen), 5, 15);
		return bufferedImage;
	}

	public int update() {
		State[][] newGrid = new State[width + 2][height + 2];
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				newGrid[i][j] = grid[i][j];

		for (int x = 1; x < width + 1; x++) {
			for (int y = 1; y < height + 1; y++) {
				int count = neighbourCount(x, y, grid);
				if (grid[x][y] == State.ALIVE) {
					if (count < 2) {
						newGrid[x][y] = State.DEAD;
					} else if (count > 3) {
						newGrid[x][y] = State.DEAD;
					}
				} else if (grid[x][y] == State.DEAD && count == 3) {
					newGrid[x][y] = State.ALIVE;
				}
			}
		}
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				grid[i][j] = newGrid[i][j];
		gen++;
		return gen;
	}

	private int neighbourCount(int x, int y, State[][] grid) {
		int count = 0;
		for (int y1 = y - 1; y1 <= y + 1; y1++) {
			for (int x1 = x - 1; x1 <= x + 1; x1++) {
				if ((y1 != y || x1 != x) && grid[x1][y1] == State.ALIVE) {
					count++;
				}
			}
		}
		return count;
	}
}
