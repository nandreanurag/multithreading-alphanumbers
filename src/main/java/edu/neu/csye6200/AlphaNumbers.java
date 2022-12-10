package edu.neu.csye6200;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 100 TOTAL POINTS FINAL EXAM
 * 
 * DEDUCTIONS: 40 POINTS DEDUCTED IF LATE or incorrectly submitted 20 POINTS
 * DEDUCTED IF INCORRECT OUTPUT 20 POINTS DEDUCTED IF NOT using Threads as
 * specified 20 POINTS DEDUCTED IF NOT COORDINATING and managing Threads
 * correctly 10 POINTS DEDUCTED IF NOT using Enum
 * 
 * REQUIREMENTS (Friday CSYE6200 02 CRN 13268):
 * 
 * output to console (stdout) ALL SUPPLIED K_A_CHAR_DATA in SPECIFIC alphabetic
 * order (NO EXTRA SPACES):
 * 
 * number (1) followed by LOWER case letter (a) followed by UPPER case letter
 * (A) followed by comma (,)
 * 
 * for example: 1aA,2bB,2cC, ... 26zZ
 *
 * THEN REPEAT 2nd time BUT, in REVERSE alphabetic order (UPPER case, LOWER
 * case): number (1) followed by UPPER case letter (a) followed by LOWER case
 * letter (A) followed by comma (,) for example: 1Zz,2Yy, ... 24Cc,25Bb,26Aa
 *
 * THEN REPEAT 3rd time, again in alphabetic order (UPPER case, LOWER case):
 * number (1) followed by UPPER case letter (a) followed by LOWER case letter
 * (A) followed by comma (,) for example: 1Aa,2Bb,2Cc, ... 25Yy26Zz
 * 
 * USING: coordinated threads - Thread #1: numberThread outputs only number -
 * Thread #2: lowerCaseThread outputs only lower case letter - Thread #3:
 * upperCaseThread outputs only upper case letter - Thread #4: spacerThread
 * outputs only spacer character, e.g. comma
 * 
 * @author dpeters
 *
 */
public class AlphaNumbers {
	public static final int MAJOR_VERSION = 5;
	public static final int MINOR_VERSION = 0;
	public static final String REVISION = Integer.valueOf(MAJOR_VERSION) + "." + Integer.valueOf(MINOR_VERSION);
	private int index = 0;

	/**
	 * supplies data for all threads
	 * 
	 * @author dpeters
	 *
	 */
	public enum PrintType {
		a2Z, Z2a, A2z
	}

	public final static Character[] K_A_CHAR_DATA = { Character.valueOf('c'), Character.valueOf('a'),
			Character.valueOf('b'), Character.valueOf('f'), Character.valueOf('e'), Character.valueOf('d'),
			Character.valueOf('z'), Character.valueOf('j'), Character.valueOf('i'), Character.valueOf('m'),
			Character.valueOf('n'), Character.valueOf('o'), Character.valueOf('g'), Character.valueOf('p'),
			Character.valueOf('l'), Character.valueOf('u'), Character.valueOf('k'), Character.valueOf('y'),
			Character.valueOf('s'), Character.valueOf('t'), Character.valueOf('r'), Character.valueOf('v'),
			Character.valueOf('w'), Character.valueOf('x'), Character.valueOf('h'), Character.valueOf('q') };

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void IncrementIndex() {
		synchronized (this) {
			this.index++;
			System.out.print(index);
		}
	}

	public void DecrementIndex() {
		synchronized (this) {
			this.index--;
			
			System.out.print(27-index);
		}
	}

	/**
	 * STUDENT TODO: LOWERCASE char to console (stdout)
	 */
	private void outCharLowerCase() {
		System.out.print(K_A_CHAR_DATA[index - 1]);
	}

	/**
	 * STUDENT TODO: UPPERCASE char to console stdout
	 */
	private void outCharUpperCase() {
		System.out.print(Character.toUpperCase(K_A_CHAR_DATA[index - 1]));
	}

	private void outCharComma() {
		System.out.print(",");
	}

	/**
	 * STUDENT TODO: execute coordinated threads
	 */
	private void execute(PrintType type) {
		System.out.println();
		System.out.println();
		Arrays.sort(K_A_CHAR_DATA);
		if (type == PrintType.a2Z) {
			this.index = 0;
		}
		else if (type == PrintType.Z2a) {
			this.index = 27;
		}
		else if (type == PrintType.A2z) {
			this.index = 0;
		}
		Runnable r1 = () -> {
			if (type == PrintType.a2Z)
				IncrementIndex();
			else if (type == PrintType.Z2a)
				DecrementIndex();
			else if (type == PrintType.A2z) 
				IncrementIndex();
		};
		Runnable r2 = () -> {
			outCharLowerCase();
		};
		Runnable r3 = () -> {
			outCharUpperCase();
		};
		Runnable r4 = () -> {
			outCharComma();
		};
		if (type == PrintType.a2Z)
		this.runWorkers(26, r1, r2, r3, r4);
		else if(type == PrintType.Z2a)
		this.runWorkers(26, r1, r3, r2, r4);
		else if (type == PrintType.A2z)
		this.runWorkers(26, r1, r3, r2, r4);	
		/**
		 * STUDENT TODO: start Threads
		 */

		System.out.println();
		/**
		 * STUDENT TODO: start Threads
		 */

		System.out.println();
	}

	private void runWorkers(int numWorkers, Runnable work1, Runnable work2, Runnable work3, Runnable work4) {
		ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
		/**
		 * execute each worker thread
		 */
		for (int i = 0; i < numWorkers; i++) {
			executor.execute(work1);
			try {
				executor.awaitTermination(1, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			executor.execute(work2);
			try {
				executor.awaitTermination(1, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			executor.execute(work3);
			try {
				executor.awaitTermination(1, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			executor.execute(work4);
			try {
				executor.awaitTermination(1, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		;

		try {
			if (false == executor.awaitTermination(1, TimeUnit.SECONDS))
//				System.out.println("executor.awaitTermination: TIMEOUT!");
				executor.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * STUDENT TODO: REQUIREMENTS (Friday CSYE6200 03 CRN 13268):
	 */
	private static void demoSolutionFridayCSYE6200_02_13268() {
		System.out.println(AlphaNumbers.class.getSimpleName() + " Friday CSYE6200 02 CRN 13268");
		/**
		 */
		AlphaNumbers obj = new AlphaNumbers();

		/**
		 * STUDENT TODO: output in alphabetical order
		 */
		obj.execute(PrintType.a2Z);
		/**
		 * STUDENT TODO: output in reverse alphabetical order
		 */
		obj.execute(PrintType.Z2a);
		/**
		 * STUDENT TODO: output in alphabetical order again
		 */
		obj.execute(PrintType.A2z);

	}

	/**
	 * demonstrate use of this class
	 */
	public static void demo() {
		demoSolutionFridayCSYE6200_02_13268(); // STUDENT TODO:
	}
}
