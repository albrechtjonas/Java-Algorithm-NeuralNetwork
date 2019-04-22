package PuKeCard;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Game {
	public static Game game;
	dsadsa
	private Random random;
	
	private ServerSocket serverSocket;
	
	private ArrayList<Socket> sockets=new ArrayList<Socket>();
	
	private final int playerNumber=1;
	
	private ArrayList<String> cards=new ArrayList<String>();
	
	private ArrayList<String> names=new ArrayList<String>();
	
	private static ArrayList<String> landLord=new ArrayList<String>();
	
	public static void main(String[]args) {
			game=new Game();
	}
	
	public Game() {
		initialize();
		setupServer();
		waitForConnections();
		setupStreams();
		initialCards();
		dealCards();
		decideLandlord();
		running();
	}
	
	private void initialize() {
		random=new Random();
	}
	
	private void setupServer() {
		int port=random.nextInt(5000)+1000;
		
		try {
			serverSocket=new ServerSocket(port);
		}catch(Exception exception) {
			System.out.println("Server cannot be setup...");
		}
		
		System.out.println("Server started waiting for connection on port: "+port);
	}
	
	private void waitForConnections() {
		while(sockets.size()<playerNumber) {
			try {
				Socket socket=serverSocket.accept();
				sockets.add(socket);
				System.out.println("Connected with "+socket.getInetAddress()+" on port "+socket.getLocalPort());
			}catch(Exception exception) {
				System.out.println("This device cannot be connected...");
			}
		}
	}
	
	private void setupStreams() {
		for(int i=0;i<sockets.size();i++) {
			String name=listenToIndividual(sockets.get(i));
			names.add(name);
			
		}
		System.out.println("Users: "+names);
	}
	
	private void initialCards() {
		for(int i=2;i<11;i++) {
			addElement(cards,String.valueOf(i),4);
		}
		addElement(cards,"J",4);
		addElement(cards,"Q",4);
		addElement(cards,"K",4);
		addElement(cards,"A",4);
		addElement(cards,"LittleJoker",1);
		addElement(cards,"BigJoker",1);
	}
	
	private void dealCards() {
		int eachCardsNum=(cards.size()-3)/3;
		
		ArrayList<String> one=new ArrayList<String>();
		for(int i=0;i<eachCardsNum;i++) {
			int index=random.nextInt(cards.size());
			one.add(cards.get(index));
			cards.remove(index);
		}
		if(sockets.size()>=1) {
			for(int i=0;i<one.size();i++) {
				sendToIndividual(sockets.get(0),"card:"+one.get(i));
			}
			sendToIndividual(sockets.get(0),"Card initialize finished");
		}
			
		ArrayList<String> two=new ArrayList<String>();
		for(int i=0;i<eachCardsNum;i++) {
			int index=random.nextInt(cards.size());
			two.add(cards.get(index));
			cards.remove(index);
		}
		if(sockets.size()>=2) {
			for(int i=0;i<two.size();i++) {
				sendToIndividual(sockets.get(1),"card:"+two.get(i));
			}
			sendToIndividual(sockets.get(1),"Card initialize finished");	
		}
		
		ArrayList<String> three=new ArrayList<String>();
		for(int i=0;i<eachCardsNum;i++) {
			int index=random.nextInt(cards.size());
			three.add(cards.get(index));
			cards.remove(index);
		}
		if(sockets.size()>=3) {
			for(int i=0;i<three.size();i++) {
				sendToIndividual(sockets.get(2),"card:"+three.get(i));
			}
			sendToIndividual(sockets.get(2),"Card initialize finished");
		}
		
		sendToAll(sockets,"LandLord cards are "+cards);
	}
	
	private void decideLandlord() {
		for(int i=0;i<sockets.size();i++) {
			sendToIndividual(sockets.get(i),names.get(i)+"'s turn to pick landlord");
			while(true) {
				String thing=listenToIndividual(sockets.get(i));
					
				if(thing.contains("yes") && thing.contains("landlord")) {
					landLord.add(thing.substring(13));
					break;
				}else if(thing.contains("no") && thing.contains("landlord")) {
					break;
				}
			}
		}
		
		int index=random.nextInt(landLord.size());
		
		sendToAll(sockets,"The landlord is "+landLord.get(index));
		
		for(int i=0;i<cards.size();i++) {
			sendToIndividual(sockets.get(index),"Add "+names.get(index)+" :"+cards.get(i));
		}
	}
	
	private void running() {
		while(true) {
			for(int i=0;i<sockets.size();i++) {
				sendToIndividual(sockets.get(i),"StartOptionChoosing");
				
				String thing=listenToIndividual(sockets.get(i));
				
				while(true) {
					if(thing.contains("gave")) {
						sendToAll(sockets,thing+"\n");
						sendToIndividual(sockets.get(i),"EndOptionChoosing");
						break;
					}else if(thing.contains("skip")) {
						break;
					}else if(thing.equals("AllCardFinished")) {
						sendToAll(sockets,names.get(i)+" has finished all his card");
						sleep(1000);
						System.exit(0);
					}
				}
			}
		}
	}
	
	//support methods//
	
	private void addElement(ArrayList<String> list,String element,int times) {
		for(int x=0;x<times;x++) {
			list.add(element);
		}
	}
	
	private String listenToIndividual(Socket socket) {
		String message="";
		try {
			ObjectInputStream input=new ObjectInputStream(socket.getInputStream());
			message=(String)input.readObject();
		}catch(Exception exception) {
			System.out.println("Cannot setup input streams...");
			System.out.println("Server closed for connections...");
			System.exit(0);
		}
		return message;
	}
	
	private void sendToAll(ArrayList<Socket> sockets,String message) {
		for(int i=0;i<sockets.size();i++) {
			try {
				ObjectOutputStream output=new ObjectOutputStream(sockets.get(i).getOutputStream());
				output.writeObject(message);
			}catch(Exception exception) {
				System.out.println("Cannot setup output streams...");
				System.out.println("Server closed for connections...");
				System.exit(0);
			}
		}
	}
	
	private void sendToIndividual(Socket socket,String message) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(message);
		}catch(Exception exception) {
			System.out.println("Cannot setup output streams...");
			System.out.println("Server closed for connections...");
			System.exit(0);
		}
	}
	
	private void sleep(int time) {
		try {
			
		}catch(Exception exception) {
			System.out.println("Cannot sleep...");
		}
	}

}
