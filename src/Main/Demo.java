package Main;

import java.util.Scanner;

public class Demo {
	
	private int[][] data=new int[][] {{1,0,0,1},{1,0,1,1},{1,1,0,1},{1,1,1,1},{0,1,1,0},{0,1,0,0},{0,0,1,0},{0,0,0,0}};
	
	private double[] weights=new double[] {Math.random(),Math.random(),Math.random()};
	
	private int iteration=10000000;
	
	private Scanner scanner;
	
	public static void main(String[]args) {
		new Demo();
	}
	
	private Demo() {
		scanner=new Scanner(System.in);
		
		for(int i=0;i<iteration;i++) {
			int[] sample=data[(int)(Math.random()*data.length)];
			
			int target=sample[sample.length-1];
			
			double value=0;
			
			for(int q=0;q<sample.length-1;q++) {
				value+=(sample[q]*weights[q]);
			}
			
			double output=sigmoid(value);
			
			double error=target-output;
			
			double adjustment=error*derivativeSigmoid(output);
			
			for(int w=0;w<weights.length;w++) {
				weights[w]+=adjustment*sample[w];
			}
		}
		
		System.out.println("Finished Training");
		
		while(true) {
			double value=0;
			
			String input=scanner.nextLine();
			
			if(input.equals("exit")) {
				break;
			}
			
			String[] data=input.split(" ");
			
			for(int i=0;i<data.length;i++) {
				value+=(Integer.valueOf(data[i])*weights[i]);
			}
			
			value=sigmoid(value);
			
			if(value>0.999) {
				System.out.println("Rounded to 1 from actual output "+value);
			}else if(value<0.001) {
				System.out.println("Rounded to 0 from actual output "+value);
			}
		}	
	}
	
	private double sigmoid(double x) {
		return 1/(1+Math.exp(-x));
	}
	
	private double derivativeSigmoid(double x) {
		return 1*(1-x);
	}
}
that is cool though
