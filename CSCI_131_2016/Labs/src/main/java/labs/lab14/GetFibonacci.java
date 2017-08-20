package labs.lab14;

public class GetFibonacci {
	private static final int NUM_TO_CALC = 10;
	int start;
	long[] fibs;

	public GetFibonacci(int start) {
		this.start = start;
	}

	public final long[] getFibonaccis() {
        System.out.println("Started...");
		if(fibs == null)
			calcFibs();
        System.out.println("Done.");
		return fibs;
	}

	private final void calcFibs() {
		fibs = new long[NUM_TO_CALC];
		long f1 = 1;
		long f2 = 1;
		long f3 = 0;

		for(int i = 1; i < start; i++) {
			f3 = f1 + f2;
			f1 = f2;
			f2 = f3;
		}

		for(int i = 0; i < fibs.length; i++) {
			fibs[i] = f1;
			f3 = f1 + f2;
			f1 = f2;
			f2 = f3;
		}
	}

	public static final void main(String[] args) {
		long startTime = System.nanoTime();
		GetFibonacci[] array = new GetFibonacci[50000];

		for(int i = 0; i < array.length; i++) {
			array[i] = new GetFibonacci(i * NUM_TO_CALC);
		}
		for(int i = 0; i < array.length; i++) {
			array[i].getFibonaccis();
		}

		long endTime = System.nanoTime();
		System.out.println();
		System.out.println("Total execution time = " + (endTime - startTime));
	}
}