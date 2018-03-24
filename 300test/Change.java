import java.util.Scanner;
//The name of the class is change
public class Change {

	public static void main(String[] args) {
		System.out.print("How much change is needed: ");
		Scanner keyboard = new Scanner(System.in);
		int changeInput = keyboard.nextInt();
		int numToonie = changeInput / 200;
		changeInput = changeInput - numToonie * 200;
		int numLoonie = changeInput / 100;
		changeInput = changeInput - numLoonie * 100;
		int numQuarter = changeInput / 25;
		changeInput = changeInput - numQuarter * 25;
		int numDime = changeInput / 10;
		changeInput = changeInput - numDime * 10;
		int numNickel = changeInput / 5;
		changeInput = changeInput - numNickel * 5;
		int numPenny = changeInput;
		System.out.println("Twoonies: " + numToonie);
		System.out.println("Loonies: " + numLoonie);
		System.out.println("Quarters: " + numQuarter);
		System.out.println("Dimes: " + numDime);
		System.out.println("Nickels: " + numNickel);
		System.out.println("Pennies: " + numPenny);


	}

}
