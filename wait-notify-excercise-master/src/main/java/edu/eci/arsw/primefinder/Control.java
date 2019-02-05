/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];
    
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];

        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1);
    }
    
    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
    	Scanner sc = new Scanner(System.in);
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }
        
        long end = System.currentTimeMillis() + TMILISECONDS;
       
        while(System.currentTimeMillis() <= end) {
        	//System.out.println(pft[0].getPrimes().size());
        	if(System.currentTimeMillis() >=  end && !terminate()) {
        		
        		for (int i = 0; i < pft.length; i++)  {
        			
        			pft[i].waitProcess();
        			System.out.println("El thread " + i+" ha encontrado "+pft[i].getPrimes().size());
        			
        		}
        		
        		System.out.println("Oprime un boton");
				sc.nextLine();
				for (int i = 0; i < pft.length; i++)  {
        			pft[i].notifyProcess();        			
        		}
				synchronized (this){this.notifyAll();}
        		end = System.currentTimeMillis()+TMILISECONDS;
        	}else if(System.currentTimeMillis() >=  end && terminate()) {
        		for (int i = 0; i < pft.length; i++) System.out.println("El thread " + i+" ha encontrado "+pft[i].getPrimes().size());
        		System.out.println("ya acabamos");
        		System.exit(0);
        	}
        }
        
        
        
       
        
    }
    
    
    private boolean terminate() {
    	boolean acabar = true;
    	for (int i = 0; i < pft.length && acabar; i++) {
			if(pft[i].isAlive()) acabar = false;
		}
    	return acabar;
    }

    
}