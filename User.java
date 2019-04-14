package PuKeCard;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
	
	public Scanner input;
	
	public ArrayList<String> cardList=new ArrayList<String>();
	
	public User() {
		input=new Scanner(System.in);
	}
	
	public void action() {
		System.out.println("Your card?");
		String option=input.nextLine();
		boolean exist=false;
		int index=0;
		
		for(int i=0;i<cardList.size();i++) {
			if(cardList.get(i).equalsIgnoreCase(option)) {
				exist=true;
				index=i;
				break;
			}
		}
		
		if(exist==true) {
			cardList.remove(index);
			System.out.println("User Card Remained: "+cardList.size());
		}else {
			System.out.println("Wrong option");
		}
		
		
	}
}
