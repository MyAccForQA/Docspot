package core;

import java.util.*;

public class LowPointFinderOnce {
	public static class Map {
		// do not change anything in the Map class
		private int mGrid[][] = null;

		public int getNumRows() {
			return mGrid.length;
		}

		public int getNumColumns() {
			return mGrid[0].length;
		}

		public int getAltitude(int iRow, int iColumn) {
			return mGrid[iRow][iColumn];
		}

		public void printMap() {
			StringBuilder sbRow = new StringBuilder("    ");
			for (int i = 0; i < mGrid[0].length; i++) {
				String strCell = String.format("%4s", "C" + i);
				sbRow.append(strCell);
			}
			System.out.println(sbRow.toString());
			for (int i = 0; i < mGrid.length; i++) {
				String strCell = String.format("%4s", "R" + i);
				sbRow = new StringBuilder(strCell);
				for (int j = 0; j < mGrid[0].length; j++) {
					strCell = String.format("%4d", getAltitude(i, j));
					sbRow.append(strCell);
				}
				System.out.println(sbRow.toString());
			}
		}

		private int changeAltitude(int iAltitude, Random random) {
			return iAltitude + random.nextInt(11) - 5;
		}

		public Map(int iNumRows, int iNumColumns, int iRandomSeed) {
			mGrid = new int[iNumRows][iNumColumns];
			Random random = new Random(iRandomSeed);
			for (int i = 0; i < iNumRows; i++) {
				for (int j = 0; j < iNumColumns; j++) {
					int iAltitude = 0;
					if (i == 0) {
						iAltitude = random.nextInt(101);
					} else {
						int iSideAltitude = 0;
						if (j == 0)
							iSideAltitude = random.nextInt(101);
						else
							iSideAltitude = getAltitude(i, j - 1);
						int iTopAltitude = getAltitude(i - 1, j);
						iAltitude = (changeAltitude(iSideAltitude, random) + changeAltitude(iTopAltitude, random)) / 2;
						if (iAltitude < 0)
							iAltitude = 0;
						else if (iAltitude > 100)
							iAltitude = 100;
					}
					mGrid[i][j] = iAltitude;
				}
			}
		}
	}

	public static void printLowestPoint(Map map, int iRow, int iColumn) {
		// implement this function (and any necessary helper code);
		// replace the ??? with the correct information
		System.out.println("The lowest reachable point occurs at " + iRow + ", " + iColumn + " with an altitude of "
				+ map.getAltitude(iRow, iColumn));

		// Start
		int iRow_t = iRow;
		int iColumn_t = iColumn;

		// int[] tArr;
		TreeMap<Integer, ArrayList<String>> tMap;

		int min_t = 0;
		ArrayList<String> list_t = null;

		// 1 loop
		do {
			tMap = takeNums(map, iRow_t, iColumn_t);

			// if don't move
			if (tMap.size() == 0) {
				System.out.println("Don't move");
				break;
			}

			// print step
			System.out.println(tMap);

			min_t = tMap.firstKey();
			list_t = tMap.get(min_t);

			// if (list_t.size() == 1) {
			switch (list_t.get(0)) {
			case "U":
				iRow_t = iRow_t - 1;
				break;
			case "D":
				iRow_t = iRow_t + 1;
				break;
			case "L":
				iColumn_t = iColumn_t - 1;
				break;
			case "R":
				iColumn_t = iColumn_t + 1;
				break;
			default:
				break;
			}

		} while (true);

		System.out.println("The lowest reachable point occurs at " + iRow_t + ", " + iColumn_t + " with an altitude of "
				+ map.getAltitude(iRow_t, iColumn_t));

	}

	// ADD Method
	private static TreeMap<Integer, ArrayList<String>> takeNums(Map map, int iRow, int iColumn) {

		TreeMap<Integer, ArrayList<String>> tMap = new TreeMap<Integer, ArrayList<String>>();
		int t = 0;
		ArrayList<String> list = null;

		// U - Up
		try {
			t = map.getAltitude(iRow - 1, iColumn);
			// don't add if t > map.getAltitude(iRow, iColumn);
			if (t > map.getAltitude(iRow, iColumn))
				throw new Exception();
			if (tMap.get(t) == null) {
				list = new ArrayList<String>();
				list.add("U");
			} else {
				list = tMap.get(t);
				list.add("U");
			}
			tMap.put(t, list);
		} catch (Exception e) {
			// tMap.put("L", null);
		}

		// D
		try {
			t = map.getAltitude(iRow + 1, iColumn);
			// don't add if t > map.getAltitude(iRow, iColumn);
			if (t > map.getAltitude(iRow, iColumn))
				throw new Exception();
			if (tMap.get(t) == null) {
				list = new ArrayList<String>();
				list.add("D");
			} else {
				list = tMap.get(t);
				list.add("D");
			}
			tMap.put(t, list);
		} catch (Exception e) {
			// tMap.put("L", null);
		}

		// L - Left
		try {
			t = map.getAltitude(iRow, iColumn - 1);
			// don't add if t > map.getAltitude(iRow, iColumn);
			if (t > map.getAltitude(iRow, iColumn))
				throw new Exception();
			if (tMap.get(t) == null) {
				list = new ArrayList<String>();
				list.add("L");
			} else {
				list = tMap.get(t);
				list.add("L");
			}
			tMap.put(t, list);
		} catch (Exception e) {
			// tMap.put("L", null);
		}

		// R
		try {
			t = map.getAltitude(iRow, iColumn + 1);
			// don't add if t > map.getAltitude(iRow, iColumn);
			if (t > map.getAltitude(iRow, iColumn))
				throw new Exception();
			if (tMap.get(t) == null) {
				list = new ArrayList<String>();
				list.add("R");
			} else {
				list = tMap.get(t);
				list.add("R");
			}
			tMap.put(t, list);
		} catch (Exception e) {
			// tMap.put("L", null);
		}

		return tMap;
	}

	public static void main(String args[]) {
		Map map = new Map(10, 10, 0);
		map.printMap();
		
		printLowestPoint(map, 5, 3);
	}
}
