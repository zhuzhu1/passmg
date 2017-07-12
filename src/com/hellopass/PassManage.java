package com.hellopass;


import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.configuration.PropertiesConfiguration;

public class PassManage {
	private PropertiesConfiguration config;
	private AESUtil aesUtil;
	
	private void mainLogic()
	{
		
		Scanner scanner = null;
		try{
			scanner = new Scanner(System.in);
			System.out.print("Please enter the master password: ");
			String mainPass = scanner.nextLine();
			
//			System.out.println("Please enter the pass.properties path.");
//			System.out.print("Such as '/Users/aaa/Desktop/pass.properties': ");
//			String filepath = scanner.nextLine();
			
			aesUtil = new AESUtil();
			aesUtil.setSalt(mainPass);
			
			PropertiesUtil propertiesUtil = new PropertiesUtil();
			config = propertiesUtil.getConfig();
			
			pringHelpInfo();
			while(true)
			{
				System.out.print("Please enter the command to execute: ");
				String command = scanner.nextLine();
				String[] inputArr = command.split(" ");
				switch(inputArr[0])
				{
					case "get":
						getPass(inputArr);
						break;
					case "set":
						setPass(inputArr);
						break;
					case "getall":
						getAllPass();
						break;
					case "quit":
						return;
					default:
						System.out.println("Please enter the correct command.");
							
				}
			}
		}
		finally
		{
			if(scanner != null)
			{
				scanner.close();
			}
		}
	}
	
	private void getPass(String[] inputArr)
	{
		for(int i = 1; i < inputArr.length; i++)
		{
			String ciphertext = config.getString(inputArr[i]);
			String plaintext = aesUtil.decrypt(ciphertext);
			System.out.println(inputArr[i]+"="+plaintext);
		}
	}
	
	private void setPass(String[] inputArr)
	{
		if(inputArr.length != 3)
		{
			System.out.println("The lenth is wrong, please input command such as 'set item ***'.");
			return;
		}
		String ciphertext = aesUtil.encrypt(inputArr[2]);
		config.setProperty(inputArr[1], ciphertext);
		System.out.println(inputArr[1]+"="+ciphertext);
	}
	
	private void getAllPass()
	{
		Iterator<String> keys = config.getKeys();
		while(keys.hasNext())
		{
			String key = keys.next();
			String ciphertext = config.getString(key);
			String plaintext = aesUtil.decrypt(ciphertext);
			System.out.println(key+"="+plaintext);
		}
	}
	
	private void pringHelpInfo()
	{
		System.out.println("***********Begin*************");
		System.out.println("get item			Get the password for item.");
		System.out.println("get item1 item2...	Get the password for items. Items are separated by spaces.");
		System.out.println("set item ***		Set the password for item.");
		System.out.println("getall				Get the password for all items.");
		System.out.println("quit				Exit the program.");
		System.out.println("***********End*************");
	}
	
	public static void main(String[] args) {
		PassManage passManage = new PassManage();
		passManage.mainLogic();

	}

}
