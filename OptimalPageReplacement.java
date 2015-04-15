import java.util.*;

public class OptimalPageReplacement {

	private int numPages, resSetSize, resSetCnt, numFaults;
	private ArrayList<Integer> refString, resSet;
	static Scanner input = new Scanner(System.in);

	public OptimalPageReplacement(int n, int f) {

		this.numPages = n;
		this.resSetSize = f;
		refString = new ArrayList<Integer>(n);
		resSet = new ArrayList<Integer>(f);
	}

	public void insert() {

		refString = generator(numPages, 100);
		//display(refString);

		for (int i = 0; i < numPages; i++) {

			if (resSetCnt < resSetSize) {
				if (search(resSet, refString.get(i)) == false) {
					resSet.add(refString.get(i));
					numFaults++; resSetCnt++;
				}
			} else {
				if (search(resSet, refString.get(i)) == false) {
					resSet.set(lookAhead(i + 1), refString.get(i));
					numFaults++;
				}
			}
			//display(resSet);
		}
		System.out.println("# of Page Faults: " + numFaults);
	}

	public int lookAhead(int currIndex) {

		int[] indices = new int[resSetSize];
		for (int i = 0; i < resSetSize; i++) {
			indices[i] = returnIndex(refString, resSet.get(i), currIndex);
		}
		return findMax(indices);
	}

	public boolean search(ArrayList<Integer> a, int val) {

		for (int i = 0; i < a.size(); i++) {
			if (val == a.get(i))
				return true;
		}
		return false;
	}

	public int returnIndex(ArrayList<Integer> a, int val, int currIndex) {

		for (int i = currIndex; i < a.size(); i++) {
			if (val == a.get(i))
				return i;
		}
		return Integer.MAX_VALUE;
	}

	public void display(ArrayList<Integer> a) {

		for (int i = 0; i < a.size(); i++) {
			System.out.print(a.get(i) + " ");
		}
		System.out.println();
	}

	public int findMax(int[] a) {

		int max = Integer.MIN_VALUE, maxIndex = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max) {
				max = a[i]; maxIndex = i;
			}
		}
		return maxIndex;
	}

	public ArrayList<Integer> generator(int size, int hi) {

		Random rand = new Random();
		ArrayList<Integer> randNums = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++) {
			int num = rand.nextInt(hi);
			if (num < 90) {
				randNums.add(rand.nextInt(hi - 90));
				while (i > 0 && randNums.get(i) == randNums.get(i - 1)) {
					randNums.set(i, rand.nextInt(hi - 90));
				}
			} else {
				randNums.add(rand.nextInt(hi));
				while (i > 0 && randNums.get(i) == randNums.get(i - 1)) {
					randNums.set(i, rand.nextInt(hi));
				}
			}
		}
		return randNums;
	}

	public static void main(String[] args) {

		OptimalPageReplacement opt = new OptimalPageReplacement(5000, 15);
		opt.insert();
	}
}
