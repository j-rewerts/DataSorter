import java.util.ArrayList;
import java.util.Collections;


public class Adjudicator {
	
	public Adjudicator() {
		
	}
	
	public boolean testInputs(ArrayList<Integer> randomUnsorted, ArrayList<Integer> sortingAttempt) {
		Collections.sort(randomUnsorted);
		
		for (int i = 0; i < randomUnsorted.size(); i++) {
			if (!randomUnsorted.get(i).equals(sortingAttempt.get(i))) {
				return false;
			}
		}
		
		return true;
	}
}
