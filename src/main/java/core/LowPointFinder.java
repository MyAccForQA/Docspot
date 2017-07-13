package core;

import java.util.*;

public class LowPointFinder {
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
		int[][] zero = new int[map.getNumRows()][map.getNumColumns()];
		for (int i = 0; i < zero.length; i++)
			for (int j = 0; j < zero[i].length; j++)
				zero[i][j] = 99999;

		int iRow_t = iRow;
		int iColumn_t = iColumn;

		// {step; [value, count, x, y]}
		TreeMap<Integer, Object> map_res = new TreeMap<Integer, Object>();
		map_res.put(0, new int[] { map.getAltitude(iRow_t, iColumn_t), 0, iRow_t, iColumn_t });

		// iRow_t, iColumn_t };
		TreeMap<Integer, Object> arr_res = new TreeMap<Integer, Object>();
		arr_res.putAll(map_res);

		// (value, count, x, y)
		int[] data = new int[] { map.getAltitude(iRow_t, iColumn_t), 0, iRow_t, iColumn_t };

		TreeMap<Integer, ArrayList<String>> tMap;
		ArrayList<String> list_t = null;

		// loop
		for (int i = 1;;) {

			tMap = takeNums(map, iRow_t, iColumn_t, zero, arr_res);

			// if first time
			if (zero[iRow_t][iColumn_t] == 99999) {
				if (tMap.size() == 0) {
					zero[iRow_t][iColumn_t] = 0;
				} else {
					zero[iRow_t][iColumn_t] = tMap.firstEntry().getValue().size();
				}
			}

			// if first element - don't move and to go print result...
			if (tMap.size() == 0 && iRow_t == iRow && iColumn_t == iColumn && zero[iRow_t][iColumn_t] == 0) {
				// System.out.println("Don't move and to go print result...");
				break;
			}

			// tMap doesn't have element and to go back...
			if (tMap.size() == 0) {
				// in
				i--;

				// return corrent item - x,y
				data = (int[]) map_res.get(i);

				// check min-value
				if (data[0] <= ((int[]) arr_res.lastEntry().getValue())[0]) {
					arr_res.putAll(map_res);
				}
				// check count
				else if (data[0] == ((int[]) arr_res.lastEntry().getValue())[0]
						&& data[1] <= ((int[]) arr_res.lastEntry().getValue())[1]) {
					arr_res.putAll(map_res);
				}
				// check X
				else if (data[0] == ((int[]) arr_res.lastEntry().getValue())[0]
						&& data[1] == ((int[]) arr_res.lastEntry().getValue())[1]
						&& data[2] <= ((int[]) arr_res.lastEntry().getValue())[2]) {
					arr_res.putAll(map_res);
				}
				// check Y
				else if (data[0] == ((int[]) arr_res.lastEntry().getValue())[0]
						&& data[1] == ((int[]) arr_res.lastEntry().getValue())[1]
						&& data[2] == ((int[]) arr_res.lastEntry().getValue())[2]
						&& data[3] <= ((int[]) arr_res.lastEntry().getValue())[3]) {
					arr_res.putAll(map_res);
				}

				// delete last item
				map_res.remove(i);
				zero[iRow_t][iColumn_t] = 0;

				// I need previous value
				i--;
				data = (int[]) map_res.get(i);
				if (data == null)
					break;
				iRow_t = data[2];
				iColumn_t = data[3];
				zero[iRow_t][iColumn_t] = zero[iRow_t][iColumn_t] - 1;

				i++;
				continue;

			} else { // tMap has elements...
				// tMap_size = 0;
				if (tMap.firstEntry().getValue().size() != 0) {

					// take first of sort map - min value
					list_t = tMap.firstEntry().getValue();
					boolean b = false;

					switch (list_t.get(0)) {
					case "U":
						if (zero[iRow_t - 1][iColumn_t] != 0) {
							iRow_t = iRow_t - 1;
							b = true;
						}
						break;
					case "D":
						if (zero[iRow_t + 1][iColumn_t] != 0) {
							iRow_t = iRow_t + 1;
							b = true;
						}
						break;
					case "L":
						if (zero[iRow_t][iColumn_t - 1] != 0) {
							iColumn_t = iColumn_t - 1;
							b = true;
						}
						break;
					case "R":
						if (zero[iRow_t][iColumn_t + 1] != 0) {
							iColumn_t = iColumn_t + 1;
							b = true;
						}
						break;
					}

					// boolean b = false; is true
					if (b) {
						map_res.put(i, new int[] { map.getAltitude(iRow_t, iColumn_t), i, iRow_t, iColumn_t });
					} else {
						// delete last item
						map_res.remove(i);
						zero[iRow_t][iColumn_t] = 0;

						// I need previous value
						i--;
						data = (int[]) map_res.get(i);
						iRow_t = data[2];
						iColumn_t = data[3];
						zero[iRow_t][iColumn_t] = zero[iRow_t][iColumn_t] - 1;
					}
				} // if (tMap.size() != 0) {
			}

			i++;
			continue;

		} // for (int i = 1;;) {

		data = (int[]) arr_res.lastEntry().getValue();
		System.out.println("The lowest reachable point occurs at " + data[2] + ", " + data[3] + " with an altitude of "
				+ data[0] + " with " + data[1] + " count");
		System.out.print("Full path - ");
		map_resPrint(arr_res);
		System.out.println();

	}

	// ADD Method
	private static TreeMap<Integer, ArrayList<String>> takeNums(Map map, int iRow, int iColumn, int[][] zero,
			TreeMap<Integer, Object> arr_res) {

		TreeMap<Integer, ArrayList<String>> tMap = new TreeMap<Integer, ArrayList<String>>();
		ArrayList<String> list = null;

		// U - Up
		try {
			if (map.getAltitude(iRow - 1, iColumn) > map.getAltitude(iRow, iColumn))
				throw new Exception();
			if (zero[iRow - 1][iColumn] == 0)
				throw new Exception();

			if (zero[iRow - 1][iColumn] == 99999) {
				if (tMap.get(map.getAltitude(iRow - 1, iColumn)) == null) {
					list = new ArrayList<String>();
					list.add("U");
				} else {
					list = tMap.get(map.getAltitude(iRow - 1, iColumn));
					list.add("U");
				}
				tMap.put(map.getAltitude(iRow - 1, iColumn), list);
			}
		} catch (Exception e) {
			// tMap.put("L", null);
		}

		// D
		try {
			if (map.getAltitude(iRow + 1, iColumn) > map.getAltitude(iRow, iColumn))
				throw new Exception();
			if (zero[iRow + 1][iColumn] == 0)
				throw new Exception();

			if (zero[iRow + 1][iColumn] == 99999) {
				if (tMap.get(map.getAltitude(iRow + 1, iColumn)) == null) {
					list = new ArrayList<String>();
					list.add("D");
				} else {
					list = tMap.get(map.getAltitude(iRow + 1, iColumn));
					list.add("D");
				}
				tMap.put(map.getAltitude(iRow + 1, iColumn), list);
			}
		} catch (Exception e) {
			// tMap.put("L", null);
		}

		// L - Left
		try {
			if (map.getAltitude(iRow, iColumn - 1) > map.getAltitude(iRow, iColumn))
				throw new Exception();
			if (zero[iRow][iColumn - 1] == 0)
				throw new Exception();

			if (zero[iRow][iColumn - 1] == 99999) {
				if (tMap.get(map.getAltitude(iRow, iColumn - 1)) == null) {
					list = new ArrayList<String>();
					list.add("L");
				} else {
					list = tMap.get(map.getAltitude(iRow, iColumn - 1));
					list.add("L");
				}
				tMap.put(map.getAltitude(iRow, iColumn - 1), list);
			}
		} catch (Exception e) {
			// tMap.put("L", null);
		}

		// R
		try {
			if (map.getAltitude(iRow, iColumn + 1) > map.getAltitude(iRow, iColumn))
				throw new Exception();
			if (zero[iRow][iColumn + 1] == 0)
				throw new Exception();

			if (zero[iRow][iColumn + 1] == 99999) {
				if (tMap.get(map.getAltitude(iRow, iColumn + 1)) == null) {
					list = new ArrayList<String>();
					list.add("R");
				} else {
					list = tMap.get(map.getAltitude(iRow, iColumn + 1));
					list.add("R");
				}
				tMap.put(map.getAltitude(iRow, iColumn + 1), list);
			}
		} catch (Exception e) {
			// tMap.put("L", null);
		}

		return tMap;
	}

	private static void map_resPrint(TreeMap<Integer, Object> map_res) {

		// print
		System.out.print("{");

		for (Integer key : map_res.keySet()) {

			System.out.print(key + "=");
			System.out.print("[");
			int[] arr = (int[]) map_res.get(key);

			for (int i = 0; i < arr.length; i++)
				if (i == arr.length - 1)
					System.out.print(arr[i]);
				else
					System.out.print(arr[i] + ",");

			if (key == map_res.size() - 1)
				System.out.print("]");
			else
				System.out.print("], ");

		} // for (Integer key : map_res.keySet()) {

		System.out.println("}");

	}

	public static void main(String args[]) {
		Map map = new Map(10, 10, 0);
		map.printMap();

		printLowestPoint(map, 9, 0);
		printLowestPoint(map, 5, 3);
		printLowestPoint(map, 1, 9);
		printLowestPoint(map, 0, 6);
		printLowestPoint(map, 0, 3);
		printLowestPoint(map, 4, 4);
	}
}
