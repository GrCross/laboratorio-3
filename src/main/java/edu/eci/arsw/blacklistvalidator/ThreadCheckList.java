package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class ThreadCheckList extends Thread {
	
	HostBlacklistsDataSourceFacade skds;
	
	 LinkedList<Integer> blackListOcurrences=new LinkedList<>();
     
     int ocurrencesCount=0;
     
     String ipAddress;
     
     int upper, lower;
	
	public ThreadCheckList(HostBlacklistsDataSourceFacade skds1,int upper1,int lower1, String ipAddress1) {
		this.skds=skds1;
		this.upper=upper1;
		this.lower=lower1;
		this.ipAddress=ipAddress1;
	}
	
	public void run() {
		
		for (int i=lower;i<upper;i++){
            
            if (skds.isInBlackListServer(i, ipAddress)){
                
                blackListOcurrences.add(i);
                
                ocurrencesCount++;
            }
        }
	}
	
	public int ask() {
		return this.ocurrencesCount;
	}
	
}
