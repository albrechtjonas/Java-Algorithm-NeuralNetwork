package PuKeCard;

import java.util.ArrayList;

public class Bot {
	
	public User user;
	
	public ArrayList<String> cardList=new ArrayList<String>();
	
	public Bot() {
		//useless constructor
	}
	
	public void action(ArrayList<String> botCardList,ArrayList<String> userCardList) {
		//System.out.println("Total Card Remained: "+calculateAllRemainedCard(botCardList,userCardList).size());
		
		findBoom(userCardList);
		
		//findCombo(userCardList);
	}
	
	public ArrayList<String> calculateAllRemainedCard(ArrayList<String> botCardList,ArrayList<String> userCardList) {
		ArrayList<String> allRemainedCard=new ArrayList<String>();
		for(int i=0;i<botCardList.size();i++) {
			allRemainedCard.add(botCardList.get(i));
		}
		
		for(int i=0;i<userCardList.size();i++) {
			allRemainedCard.add(userCardList.get(i));
		}
		return allRemainedCard;
	}
	
	public void findBoom(ArrayList<String> userCardList) {
		ArrayList<String> boomList=new ArrayList<String>();
		int counts=0;
		boolean boomExists=false;
		for(int i=0;i<userCardList.size();i++) {
			counts=0;
			String card=userCardList.get(i);
			
			for(int x=0;x<userCardList.size();x++) {
				if(userCardList.get(x).equals(card)) {
					counts++;
				}
			}
			
			if(counts==4) {
				boolean repeat=false;
				for(int y=0;y<boomList.size();y++) {
					
					if(boomList.get(y).equals(card)) {
						repeat=true;
					}
				}
				if(repeat==false) {
					boomList.add(card);
				}
				boomExists=true;
			}
		}
		
		if(boomExists==false) {
			System.out.println("No boom for the user");
		}else {
			System.out.print("The booms are: ");
			for(int i=0;i<boomList.size();i++) {
				System.out.print(boomList.get(i));
				System.out.print(", ");
			}
		}
	}
	
	public void findCombo(ArrayList<String> userCardList) {
		ArrayList<Integer> list=stringToIntArray(userCardList);
		
		System.out.println(list);
		
		ArrayList<String> comboList=new ArrayList<String>();
		
		
	}
	
	public ArrayList<Integer> stringToIntArray(ArrayList<String> userCardList) {
		
		ArrayList<Integer> intList=new ArrayList<Integer>();
		
		for(int i=0;i<userCardList.size();i++) {
			
			for(int x=3;x<11;x++) {
				if(userCardList.get(i).equalsIgnoreCase(String.valueOf(x))) {
					intList.add(x);
				}
			}
			
			if(userCardList.get(i).equals("J")) {
				intList.add(11);
			}else if(userCardList.get(i).equals("Q")) {
				intList.add(12);
			}else if(userCardList.get(i).equals("K")) {
				intList.add(13);
			}else if(userCardList.get(i).equals("A")) {
				intList.add(14);
			}else if(userCardList.get(i).equals("2")) {
				intList.add(15);
			}else if(userCardList.get(i).equals("LittleJoker")) {
				intList.add(16);
			}else if(userCardList.get(i).equals("BigJoker")) {
				intList.add(17);
			}
		}
		return intList;
	}
	
}
