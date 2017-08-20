package labs.lab14;

public class GetFibonacciThreaded extends Thread {
	private static final int NUM_TO_CALC = 10;
	int start;
	long[] fibs;

    public GetFibonacciThreaded(int start) {
		this.start = start;
	}

    @Override
    public void run() {
        System.out.println("Started...");
        if (fibs == null)
            calcFibs();
        System.out.println("Done.");
    }

	public final long[] getFibonaccis() {
		if(fibs == null) {
			calcFibs();
		}
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

    public static void main(String[] args) {
		long startTime = System.nanoTime();
		GetFibonacciThreaded[] array = new GetFibonacciThreaded[50000];

		for(int i = 0; i < array.length; i++) {
			array[i] = new GetFibonacciThreaded(i * NUM_TO_CALC);
		}
		for(int i = 0; i < array.length; i++) {
			array[i].start();
		}
		for(int i = 0; i < array.length; i++) {
            try {
			    array[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}

		long endTime = System.nanoTime();
		System.out.println();
		System.out.println("Total execution time = " + (endTime - startTime));
    }
}