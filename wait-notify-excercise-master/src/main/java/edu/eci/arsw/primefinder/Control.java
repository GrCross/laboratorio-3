/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import javax.swing.*;
import java.awt.event.*;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 9000;

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
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
           
        }
        javax.swing.Timer t = new javax.swing.Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for(int i = 0;i < NTHREADS;i++ ) {
                    try {
                    	 synchronized(pft[i]){
                    		 System.out.println(pft[i].getPrimes().size()+" ");
                    		 pft[i].wait();
                         }
						
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
                }
        		System.out.println("hola");
            }
         });
        while (true) t.start();
        
        
        /*long end = System.currentTimeMillis() + TMILISECONDS;
        //System.out.println("start: "+start);
        while(true) {
        	
        	
        		System.out.println("tiempo real: "+System.currentTimeMillis());
        		System.out.println("soy end:"+end);
        		System.out.println("hola");
        		//end = System.currentTimeMillis()+TMILISECONDS;
        	
        }*/
        
    }

    
}
