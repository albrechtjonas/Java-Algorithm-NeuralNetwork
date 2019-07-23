package Demo;

import java.util.Scanner;

public class Demo {
	
	private int[][] data=new int[][]{{1,0,0,1},{1,0,1,1},{1,1,0,1},{1,1,1,1},{0,0,1,0},{0,1,1,0},{0,1,0,0}};
	
	private double[] weights=new double[3];
	
	private int times=10000000;
	
	private Scanner input;
	
	public static void main(String[]args) {
		new Demo();
	}
	
	private Demo() {
		
		input=new Scanner(System.in);
		
		createWeights();
		
		for(int i=0;i<times;i++) {
			int[] sample=data[(int)(Math.random()*data.length)];
			
			int target=sample[sample.length-1];
		
			double value=0;
			
			for(int q=0;q<sample.length-1;q++) {
				value+=(sample[q]*weights[q]);
			}
			
			double output=sigmoid(value);
			
			double error=target-output;
			
			double adjustment=error*derSigmoid(output);
			
			for(int w=0;w<weights.length;w++) {
				weights[w]+=adjustment*sample[w];
			}
			
		}
		
		System.out.println("finished training\n");
		
		while(true) {
		
			System.out.println("Input One");
			int one=input.nextInt();
		
			System.out.println("Input Two");
			int two=input.nextInt();
		
			System.out.println("Input Three");
			int three=input.nextInt();
		
			double value=one*weights[0]+two*weights[1]+three*weights[2];
		
			System.out.println("The value is "+sigmoid(value)+"\n");
		}
		
	}
	
	private void createWeights() {
		for(int i=0;i<weights.length;i++) {
			weights[i]=Math.random();
		}
	}
	
	private double sigmoid(double x) {
		return 1/(1+Math.exp(-x));
	}
	
	private double derSigmoid(double x) {
		return 1*(1-x);
	}
}
