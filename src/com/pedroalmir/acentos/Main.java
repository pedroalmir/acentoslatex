package com.pedroalmir.acentos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	private static ArrayList<File> files;
	private static Map<String, String> replaceMap;
	private static boolean revert = true;
	
	static{
		replaceMap = new HashMap<String, String>();
		replaceMap.put("á", "\\'a");
		replaceMap.put("à", "\\'a");  
		replaceMap.put("ã", "\\~a");  
		replaceMap.put("â", "\\^a");  
		replaceMap.put("é", "\\'e"); 
		replaceMap.put("ê", "\\^e"); 
		replaceMap.put("í", "\\'{\\i}"); 
		replaceMap.put("Í", "\\'I");
		replaceMap.put("ó", "\\'o");
		replaceMap.put("õ", "\\~o");
		replaceMap.put("ô", "\\^o");
		replaceMap.put("ú", "\\'u");
		replaceMap.put("ü", "\\\"u");
		replaceMap.put("ç", "\\c{c}");
		replaceMap.put("Ç", "\\c{C}");
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		files = new ArrayList<File>();
		final File folder = new File("LOCATION");
		listFilesForFolder(folder);
		
		for (File file : files) {
			String content = getContent(file);
			for(String key : replaceMap.keySet()){
				if(revert){
					content = content.replace(replaceMap.get(key), key);
				}else{
					content = content.replace(key, replaceMap.get(key));
				}
			}
			saveContent(file, content);
		}
	}
	
	/**
	 * @param folder
	 */
	public static void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	            files.add(fileEntry);
	        }
	    }
	}
	
	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static String getContent(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	/**
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	private static void saveContent(File file, String content) throws IOException{
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}
}
