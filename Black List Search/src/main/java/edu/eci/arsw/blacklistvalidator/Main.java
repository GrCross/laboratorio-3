/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.lang.Runtime;
/**
 *
 * @author hcadavid5
 */
public class Main {
    
    public static void main(String a[]) throws NumberFormatException, IOException{
    	Runtime run = Runtime.getRuntime();
    	System.out.println(run.availableProcessors());
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("cuantos thread desea?");
    	int n = Integer.parseInt(br.readLine());
    	
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        System.out.println("corriendo");
        List<Integer> blackListOcurrences=hblv.checkHost("212.24.24.55",n);
        
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        
    }
    
}
