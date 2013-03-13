import java.io.*;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.*;
import java.util.regex.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Twitter {
	
	ObjectMapper map;
	ArrayList<String> urls;
	String hash;
	Scanner s;
	Matcher matcher;
	
	public Twitter() {
		hash = "";
		map = new ObjectMapper();
		urls = new ArrayList<String>();	
	}

	public void uniqueUrls()
	{
		int i = 0;
		
		while(true)
		{
			getinput();
			
			if(hash.length() == 0)
			{
				
				System.out.println("Please enter a valid input.");
				continue;
			}
			if(hash.equalsIgnoreCase("0"))
			{
				//quit option
				System.out.println("Quiting the application");
				s.close();
				System.exit(0);
			}
			else
			{
				int r = inputValidation();
				if(r==-1)
				{
					continue;
				}
			}
			try
			{
				//Invoking the Twitter API
				InputStream is = new URL("http://search.twitter.com/search.json?rpp=150&q=%23"+hash).openStream();
				
				JsonNode root = map.readTree(is);
				JsonNode results = root.path("results"); 
				i = 1;
				
				Pattern urlPattern = Pattern.compile(
			            "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" + 
			            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" + 
			            "|mil|biz|info|mobi|name|aero|jobs|museum" + 
			            "|travel|[a-z]{2}))(:[\\d]{1,5})?" + 
			            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" + 
			            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
			            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" + 
			            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
			            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" + 
			            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");
		
				while(results.has(i))
				{
					String text = results.get(i).get("text").asText();
					
					matcher = urlPattern.matcher(text); 
					while (matcher.find())
					{
						
						if(!urls.contains(matcher.group()))
						{
							urls.add(matcher.group());
						}
					}

					i++;

				}
				//If no records returned
				if(i == 1)
				{
					System.out.println("There are no records for #" + hash + ". Try different one");
					continue;
				}
				
				else
				{
					System.out.println("\n");
					System.out.println("In the " + i +" records received from Twitter for the hash : #" + hash + ", there was/were "+urls.size() +" unique url(s).");
					System.out.println("They are \n");

					for(String str:urls) 
					{
			            System.out.println(str);
			        }

					System.out.println("\n");
					System.out.println("In the " + i +" records received from Twitter for the hashtag : #" + hash + ", there were "+urls.size() +" unique urls.");
					

				}	

			}
			catch(MalformedInputException mue)
			{
				System.out.println("URL is currupt.");
				continue;
			}
			catch(IOException ioe)
			{
				System.out.println("IO Excetion has occurred.");
				continue;
			}
		
		}
	}
	
	public void getinput()
	{
		
		//prompt for the input hashtag
		System.out.println("Please enter hashtag. No digits or special characters. (Enter 0 to quit)");
		s = new Scanner(System.in);
		hash = s.nextLine();
	}
	
	public int inputValidation(){
		
		//INPUT VALIDATION
		hash.trim();
		
		if(hash.startsWith("#"))
		{
			hash = hash.substring(1);
		}
	
		if(hash.contains(" "))
		{
			System.out.println("HashTag can't contain a space. Try different hash tag");
			return -1;
		}
		
		Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
		matcher = p.matcher(hash);

		if(!matcher.matches())
		{
			System.out.println("HashTag can't contain a special character. Try different hash tag");
			return -1;
		}
		
		if(Character.isDigit(hash.charAt(0)))
		{
			System.out.println("HashTag can't start with a number. Try different hash tag");
			return -1;
		}
	
		return 0;
		
	}

	
	public static void main(String[] args) {
	
		
		Twitter twitter=new Twitter();
		twitter.uniqueUrls();

	}

}
