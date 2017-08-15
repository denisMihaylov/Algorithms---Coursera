import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private WeightedQuickUnionUF wquf;

	private final int size;
	private int openSites = 0;
	private boolean[] open;
	private boolean[] connectedToBottom;
	private boolean percolates = false;

	public Percolation(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		size = n;
		open = new boolean[n * n + 1];
		wquf = new WeightedQuickUnionUF(n * n + 1);
		connectedToBottom = new boolean[n * n + 1];
		for (int i = 1; i <= size; i++) {
			wquf.union(i, 0);
			connectedToBottom[size * size + 1 - i] = true;
		}
	}

	public void open(int row, int col) {
		checkArguements(row, col);
		if (!isOpen(row, col)) {
			openSites++;
			open[toIndex(row, col)] = true;
			union(row, col, row - 1, col);
			union(row, col, row + 1, col);
			union(row, col, row, col - 1);
			union(row, col, row, col + 1);
			if (size == 1) {
				percolates = true;
			}
		}
	}

	public boolean isOpen(int row, int col) {
		checkArguements(row, col);
		return open[toIndex(row, col)];
	}

	public boolean isFull(int row, int col) {
		return isOpen(row, col) && wquf.connected(0, toIndex(row, col));
	}

	public int numberOfOpenSites() {
		return openSites;
	}

	public boolean percolates() {
		return percolates;
	}

	private void checkArguements(int row, int col) {
		if (validateArguements(row, col)) {
			throw new IllegalArgumentException();
		}
	}

	private boolean validateArguements(int row, int col) {
		return row < 1 || row > size || col < 1 || col > size;
	}

	private int toIndex(int row, int col) {
		return (row - 1) * size + col;
	}

	private void union(int row1, int col1, int row2, int col2) {
		if (!validateArguements(row1, col1) && !validateArguements(row2, col2) && isOpen(row1, col1) && isOpen(row2, col2)) {
			int root1 = wquf.find(toIndex(row1, col1));
			int root2 = wquf.find(toIndex(row2, col2));
			if (connectedToBottom[root1] && wquf.connected(0, root2) || connectedToBottom[root2] && wquf.connected(0, root1)) {
				percolates = true;
			}
			if (connectedToBottom[root1] || connectedToBottom[root2]) {
				connectedToBottom[root1] = connectedToBottom[root2] = true;
			}
			wquf.union(root1, root2);
		}
	}

}
