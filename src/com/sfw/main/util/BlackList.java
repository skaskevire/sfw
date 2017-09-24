package com.sfw.main.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class BlackList implements Runnable{
	private static final CopyOnWriteArraySet<String> BLACK_LIST = new CopyOnWriteArraySet<>();
	private File blackListFile;
	private Long readInterval;
	
	public static Boolean contains(String ip)
	{
		return BLACK_LIST.contains(ip);
	}
	
	public BlackList(String blackListFilePath, Long readInterval)
	{
		this.readInterval = readInterval;
		blackListFile = new File(blackListFilePath);
	}

	@Override
	public void run() {
		
		while(true)
		{
			try(BufferedReader fileReader = 
					new BufferedReader(new FileReader(blackListFile)))  {
				String blackListedIP = null;
				List<String> newBL = new ArrayList<>(); 
				while( (blackListedIP = fileReader.readLine()) != null)
				{
					newBL.add(blackListedIP);
				}
				
				BLACK_LIST.addAll(newBL);
				BLACK_LIST.retainAll(newBL);
				Thread.sleep(readInterval);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}	
}
