package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class ThreadCheckList extends Thread {
	
	HostBlacklistsDataSourceFacade skds;
	 private static final int BLACK_LIST_ALARM_COUNT=5;
	 LinkedList<Integer> blackListOcurrences=new LinkedList<>();
     
     int ocurrencesCount=0;
     
     String ipAddress;
     int listasRecorridas;
     int upper, lower;
	
	public ThreadCheckList(HostBlacklistsDataSourceFacade skds1,int upper1,int lower1, String ipAddress1) {
		this.skds=skds1;
		this.upper=upper1;
		this.lower=lower1;
		this.ipAddress=ipAddress1;
	}
	
	public void run() {
		boolean finalizar=false;
		for (int i = lower;i <= upper && !finalizar;i++){
			
            if (skds.isInBlackListServer(i, ipAddress)){
                blackListOcurrences.add(i);
                ocurrencesCount++;
                if (ocurrencesCount == BLACK_LIST_ALARM_COUNT) {
                	listasRecorridas = i- lower;
                	finalizar=true;
                }
            }
            listasRecorridas = i - lower;

        }
	}
	
	public int ask() {
		
		return this.ocurrencesCount;
	}
	
	public  LinkedList<Integer> getBlackListOcurrences(){
		
		return blackListOcurrences;
	}
	
	public int getListasRecorridas() {
		return listasRecorridas;
	}
}
