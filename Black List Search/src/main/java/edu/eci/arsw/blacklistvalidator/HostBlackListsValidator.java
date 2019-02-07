/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    ThreadCheckList[] threads;
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int n){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        int checkedListsCount = 0;
        int ocurrencesCount = 0;
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
        int lenOfBlackList = skds.getRegisteredServersCount();
        ThreadCheckList[] threads = new ThreadCheckList[n];
        int nObjects = 0;
        if(lenOfBlackList % n != 0)nObjects = lenOfBlackList/n + 1;
        else nObjects = lenOfBlackList/n;
        int step = 0;

        for (int i = 0; i < n; i++) {
        	if (i == n-1) {
        		int fin = lenOfBlackList % n; 
        		ThreadCheckList threadCheckList = new ThreadCheckList(skds, skds.getRegisteredServersCount(), step, ipaddress);
        		threads[i] = threadCheckList;
        		threadCheckList.start();
        	}else {
        		ThreadCheckList threadCheckList = new ThreadCheckList(skds, step+nObjects, step, ipaddress);
        		threads[i] = threadCheckList;
        		step+=nObjects;
        		threadCheckList.start();
        		
        	}
		}
        
        boolean flag = true;
    	while(flag) {
    		 for (int j = 0; j < n; j++) {
    			 if(threads[j].ask()>= 5 || acabaronLosThreads(threads)) {
    				 flag = false; 
    			 }  
    		 }
    		 for (int j = 0; j < n  && !flag; j++) {
    			 threads[j].stop();
    		 }
    	}
        
        
        for (int i = 0; i < n; i++) {
        	try {threads[i].join();} catch (InterruptedException e) {e.printStackTrace();}
    		int nApariciones = (threads[i]).ask();
    		checkedListsCount += threads[i].getListasRecorridas();
    		ocurrencesCount += nApariciones;
    		blackListOcurrences.addAll(threads[i].getBlackListOcurrences());
        }
        
        System.out.print("numero de ocurrencias: ");
        System.out.println(ocurrencesCount);
        if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
		LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{ checkedListsCount, skds.getRegisteredServersCount()} );
        
        return blackListOcurrences;
    }
    
    private boolean acabaronLosThreads(ThreadCheckList[] threads) {
    	boolean acabaron = true;
    	for (int i = 0; i < threads.length && acabaron; i++) {
			if (threads[i].isAlive()) {
				acabaron = false;
			}
		}
    	return acabaron;
    }
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
