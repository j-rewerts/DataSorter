
public class Driver {
	
	// Typical use: EXE input.txt output.txt failureProbVar1 failureProbVar2 timeLimit
	// Where input is a file that exists, output.txt is a file that will be created, 
	// failureProbVar1 and Var 2 are 0 to 1 and timeLimit is the time in milliseconds.
	public static void main(String[] args) {
		if (args.length < 5) {
			System.out.println("Error: Typical use: EXE input.txt output.txt failureProbVar1 failureProbVar2 timeLimit");
			return;
		}
		
		String inputStr = args[0];
		String outputStr = args[1];
		double var1Failure;
		double var2Failure;
		long timeLimit;
		
		try {
			var1Failure = Double.parseDouble(args[2]);
		} catch (NumberFormatException nfe) {
			System.out.println("Error: failureProbVar1 parse to Double failed.");
			return;
		}
		
		if (var1Failure < 0 || var1Failure > 1) {
			System.out.println("Error: failureProbVar1 must be between 0 and 1.");
			return;
		}
		
		try {
			var2Failure = Double.parseDouble(args[3]);
		} catch (NumberFormatException nfe) {
			System.out.println("Error: failureProbVar2 parse to Double failed.");
			return;
		}
		
		if (var2Failure < 0 || var2Failure > 1) {
			System.out.println("Error: failureProbVar2 must be between 0 and 1.");
			return;
		}
		
		try {
			timeLimit = Long.parseLong(args[4]);
		} catch (NumberFormatException nfe) {
			System.out.println("Error: timeLimit parse to Long failed.");
			return;
		}
		
		if (timeLimit < 0) {
			System.out.println("Error: timeLimit must be positive.");
			return;
		}
		
		Executive exec = new Executive(inputStr, outputStr, var1Failure, var2Failure, timeLimit);
		exec.run();
	}

}
