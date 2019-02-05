package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.*;
public class PrimeFinderThread extends Thread {
	
	private final static int TMILISECONDS = 5000;
	int a, b;
	boolean esperar=false;
	boolean seguir=true;
	

	private List<Integer> primes;

	public PrimeFinderThread(int a, int b) {
		super();
		this.primes = new LinkedList<>();
		this.a = a;
		this.b = b;
		
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		for (int i = a; i < b; i++) {
			if (esperar) {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
				
			}//else if(seguir) {this.notify(); seguir=false;}
				if (isPrime(i)) {
					primes.add(i);
				}
			
				
		}
		
		
	}

	boolean isPrime(int n) {
		boolean ans;
		if (n > 2) {
			ans = n % 2 != 0;
			for (int i = 3; ans && i * i <= n; i += 2) {
				ans = n % i != 0;
			}
		} else {
			ans = n == 2;
		}
		return ans;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
	public void waitProcess() {
		esperar=true;
	}
	
	public synchronized void notifyProcess() {
			seguir=true;
			esperar=false;
			this.notifyAll();
		
	}
	
	
	

}