package PuKeCard;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	public static Game game;
	
	public Random random;
	
	public User user;
	
	public Bot bot;
	
	public ArrayList<String> cardList=new ArrayList<String>();
	
	public static void main(String[]args) {
		game=new Game();
	}
	
	public Game() {
		setup();
		deal();
		System.out.print("User: ");
		printArray(user.cardList);
		System.out.print("Bot: ");
		printArray(bot.cardList);
		blockPath();
		
		while(user.cardList.size()!=0 || bot.cardList.size()!=0) {
			gameLoop();
		}
		System.out.println("Game is over");
	}
	
	public void setup() {
		random=new Random();
		
		user=new User();
		bot=new Bot();
		
		for(int i=2;i<11;i++) {
			add(String.valueOf(i),cardList,4);
		}
		add("J",cardList,4);
		add("Q",cardList,4);
		add("K",cardList,4);
		add("A",cardList,4);
		add("BigJoker",cardList,1);
		add("LittleJoker",cardList,1);
	}
	
	public void deal() {
		final int length=cardList.size()/2;
		
		for(int i=0;i<length;i++) {
			int index=random.nextInt(cardList.size());
			user.cardList.add(cardList.get(index));
			cardList.remove(index);
		}
		
		for(int i=0;i<cardList.size();i++) {
			bot.cardList.add(cardList.get(i));
		}
		
		cardList.removeAll(cardList);
	}
	
	public void gameLoop() {
		bot.action(bot.cardList,user.cardList);
		user.action();
	}
	
	public void add(String card,ArrayList<String> list,int times) {
		for(int x=0;x<times;x++) {
			list.add(card);
		}
	}
	
	public void printArray(ArrayList<String> list) {
		for(int i=0;i<list.size();i++) {
			System.out.print(list.get(i));
			System.out.print(", ");
		}
		System.out.println("  The length is "+list.size());
	}
	
	public void blockPath() {
		for(int i=0;i<150;i++) {
		System.out.print("/");
		}
		System.out.println();
		for(int i=0;i<150;i++) {
			System.out.print("/");
			}
		System.out.println("\n");
	}

