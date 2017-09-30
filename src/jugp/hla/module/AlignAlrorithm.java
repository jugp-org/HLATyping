package jugp.hla.module;


import jugp.hla.matrix.Matrix;
import jugp.hla.model.Alignment;
import jugp.hla.model.Sequence;
import jugp.hla.model.Markups;
import jugp.hla.model.Directions;
import jugp.hla.model.Cell;

import java.util.logging.Logger;

public class AlignAlrorithm {

	private AlignAlrorithm() {
		super();
	}

	private static final Logger logger = Logger
			.getLogger(SmithWatermanGotoh.class.getName());

	// ¬ызов процедуры выравнивани€
	public static Alignment align(Sequence s1, Sequence s2) {
		logger.info("Started...");
		long start = System.currentTimeMillis();

		AlignAlrorithm sw = new AlignAlrorithm();

		int m = s1.length() + 1;
		int n = s2.length() + 1;
		
		// матрица переходов - в видем массива 
		byte[] pointers = new byte[m * n];

		// »нициализируем границы матрицы трассировки дл€ остановки.
		// столбец по i от 0 до m-1 заполн€ем элемениты с периодом n (!)  - 0, n, 2*n, 3*n, .... , (m-1)*n 
		for (int i = 0, k = 0; i < m; i++, k += n) {
			pointers[k] = Directions.STOP;
		}
		// строка по j от 1 до n-1 с периодом n  - 0, n, 2*n, 3*n, .... , (m-1)*n 
		for (int j = 1; j < n; j++) {
			pointers[j] = Directions.STOP;
		}
		
		// —троим матрицу переходов, переходы записываютс€ в массив pointers  
		Cell cell = sw.construct(s1, s2, pointers);
		
		// проходим матрицу в обратном пор€дке, чтобы получить выравненные последовательности
		Alignment alignment = sw.traceback(s1, s2, pointers, cell);
		
		// заполн€ем свойства матрицы
		alignment.setName1(s1.getId()); 
		alignment.setName2(s2.getId()); 
		
		logger.info("Finished in " + (System.currentTimeMillis() - start)
				+ " milliseconds");
		return alignment;
	}
	
	private Cell construct(Sequence s1, Sequence s2, byte[] pointers) {
		
		logger.info("Started...");
		long start = System.currentTimeMillis();
		
		// задаем массивы символов последовательостей (!)
		char[] a1 = s1.toArray();
		char[] a2 = s2.toArray();
		
		int match  = 0; // совпадение символов
		int insert = 1; // вставка лишнего символа
		int delete = 1; // пропуск символа

		// увеличиваем на 1, так как есть 0 столбик
		int m = s1.length() + 1;
		int n = s2.length() + 1;

		Cell cell = new Cell();
		
		// цикл построени€ матрицы по строке 1 (m шагов)
		for (int i = 1, k = n; i < m; i++, k += n) {
			
			// цикл по строке 2 (n шагов)
			for (int j = 1, l = k + 1; j < n; j++, l++) {
				
			}
		}
	
		logger.info("Finished in " + (System.currentTimeMillis() - start)
				+ " milliseconds");
		return cell;
	}

	private Alignment traceback(Sequence s1, Sequence s2, byte[] pointers, Cell cell) {
		
		logger.info("Started...");
		long start = System.currentTimeMillis();
		
		char[] a1 = s1.toArray();
		char[] a2 = s2.toArray();
		
//		float[][] scores = m.getScores();

		int n = s2.length() + 1;

		Alignment alignment = new Alignment();
		alignment.setScore(cell.getScore());

		int maxlen = s1.length() + s2.length(); // maximum length after the
												// aligned sequences

		char[] reversed1 = new char[maxlen]; // reversed sequence #1
		char[] reversed2 = new char[maxlen]; // reversed sequence #2
		char[] reversed3 = new char[maxlen]; // reversed markup

		int len1 = 0; // length of sequence #1 after alignment
		int len2 = 0; // length of sequence #2 after alignment
		int len3 = 0; // length of the markup line

		int identity = 0; // count of identitcal pairs
		int similarity = 0; // count of similar pairs
		int gaps = 0; // count of gaps

		char c1, c2;

		int i = cell.getRow(); // traceback start row
		int j = cell.getCol(); // traceback start col
		int k = i * n;

		boolean stillGoing = true; // traceback flag: true -> continue & false
								   // -> stop
/*		
		// цикл по матрице от последней €чейки
		while (stillGoing) {
			// указатели направлени€
			switch (pointers[k + j]) {
			case Directions.UP:
				for (int l = 0, len = sizesOfVerticalGaps[k + j]; l < len; l++) {
					reversed1[len1++] = a1[--i];
					reversed2[len2++] = Alignment.GAP;
					reversed3[len3++] = Markups.GAP;
					k -= n;
					gaps++;
				}
				break;
			case Directions.DIAGONAL:
				c1 = a1[--i];
				c2 = a2[--j];
				k -= n;
				reversed1[len1++] = c1;
				reversed2[len2++] = c2;
				if (c1 == c2) {
					reversed3[len3++] = Markups.IDENTITY;
					identity++;
					similarity++;
				} else if (scores[c1][c2] > 0) {
					reversed3[len3++] = Markups.SIMILARITY;
					similarity++;
				} else {
					reversed3[len3++] = Markups.MISMATCH;
				}
				break;
			case Directions.LEFT:
				for (int l = 0, len = sizesOfHorizontalGaps[k + j]; l < len; l++) {
					reversed1[len1++] = Alignment.GAP;
					reversed2[len2++] = a2[--j];
					reversed3[len3++] = Markups.GAP;
					gaps++;
				}
				break;
			case Directions.STOP:
				stillGoing = false;
			}
		}
*/

		alignment.setSequence1(reverse(reversed1, len1));
		alignment.setStart1(i);
		alignment.setSequence2(reverse(reversed2, len2));
		alignment.setStart2(j);
		alignment.setMarkupLine(reverse(reversed3, len3));
		alignment.setIdentity(identity);
		alignment.setGaps(gaps);
		alignment.setSimilarity(similarity);

		logger.info("Finished in " + (System.currentTimeMillis() - start)
				+ " milliseconds");
		return alignment;
	}

	/**
	 * Returns the maximum of 4 float numbers.
	 * 
	 * @param a
	 *            float #1
	 * @param b
	 *            float #2
	 * @param c
	 *            float #3
	 * @param d
	 *            float #4
	 * @return The maximum of a, b, c and d.
	 */
	private static float maximum(float a, float b, float c, float d) {
		if (a > b) {
			if (a > c) {
				return a > d ? a : d;
			} else {
				return c > d ? c : d;
			}
		} else if (b > c) {
			return b > d ? b : d;
		} else {
			return c > d ? c : d;
		}
	}

	/**
	 * Reverses an array of chars
	 * 
	 * @param a
	 * @param len
	 * @return the input array of char reserved
	 */
	private static char[] reverse(char[] a, int len) {
		char[] b = new char[len];
		for (int i = len - 1, j = 0; i >= 0; i--, j++) {
			b[j] = a[i];
		}
		return b;
	}

}
