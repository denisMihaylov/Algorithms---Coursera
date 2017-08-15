public class Percolation {

	private boolean[] site;
	private int[] siteParent;
	private int[] siteCount;
	private final int size;
	private int openSites = 0;
	private boolean[] connectedToBottom;
	private boolean percolates = false;
	
	public Percolation(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		size = n;
		site = new boolean[n * n + 2];
		siteParent = new int[n * n + 2];
		siteCount = new int[n * n + 2];
		connectedToBottom = new boolean[n * n + 1];
		for (int i = 0; i < n * n + 2; i++) {
			site[i] = false;
			siteParent[i] = i;
			siteCount[i] = 1;
		}
		for (int i = 1; i <= size; i++) {
			union(i, 0);
//			union(size * size + 1 - i, size * size + 1);
			connectedToBottom[size * size + 1 - i] = true;
		}
	}

	public void open(int row, int col) {
		checkArguements(row, col);
		if (!isOpen(row, col)) {
			openSites++;
			site[toIndex(row, col)] = true;
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
		return site[toIndex(row, col)];
	}

	public boolean isFull(int row, int col) {
		return isOpen(row, col) && areConnected(0, toIndex(row, col));
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

	private boolean areConnected(int i, int j) {
		return getRoot(i) == getRoot(j);
	}

	private int getRoot(int index) {
		int parentIndex = index;
		while (siteParent[parentIndex] != parentIndex) {
			parentIndex = getRoot(siteParent[parentIndex]);
		}
		return siteParent[index] = parentIndex;
	}

	private void union(int i, int j) {
		int root1 = getRoot(i);
		int root2 = getRoot(j);
		if (root1 != root2) {
			if (connectedToBottom[root1] && areConnected(0, root2) || connectedToBottom[root2] && areConnected(0, root1)) {
				percolates = true;
			}
			if (siteCount[root1] > siteCount[root2]) {
				siteParent[root2] = root1;
				siteCount[root1] += siteCount[root2];
			} else {
				siteParent[root1] = root2;
				siteCount[root2] += siteCount[root1];
			}
			if (connectedToBottom[root1] || connectedToBottom[root2]) {
				connectedToBottom[root1] = connectedToBottom[root2] = true;
			}
		}
	}

	private void union(int row1, int col1, int row2, int col2) {
		if (!validateArguements(row1, col1) && !validateArguements(row2, col2) && isOpen(row1, col1) && isOpen(row2, col2)) {
			union(toIndex(row1, col1), toIndex(row2, col2));
		}
	}
	
	public static void main(String[] args) {
		Percolation perc = new Percolation(1);
		perc.percolates();
		perc.open(1, 1);
		perc.percolates();
	}
}
