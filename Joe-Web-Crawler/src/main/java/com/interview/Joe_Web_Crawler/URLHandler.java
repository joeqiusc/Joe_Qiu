package com.interview.Joe_Web_Crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Base;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author Joe Qiu 5/28/2021
 * This is the URL handle class
 */
public class URLHandler {

	static int topicIndex = 0;
	static Connection.Response loginForm;
	
	/**
	* this method will initiate the session
	*
	* @param  takes no parameter
	* @return      the document object or null
	* 
	*/
	public static Document intiateSession()
	{
		String LoginUrl = "https://www.cochranelibrary.com/c/portal/login";
		String mainPageurl ="http://www.cochranelibrary.com/";
		
		try {
			loginForm = Jsoup.connect(mainPageurl)
			        .method(Connection.Method.GET)
			        .execute();
	
			
			Connection.Response mainPage = Jsoup.connect(LoginUrl)
					.cookies(loginForm.cookies())
		            .execute();
         
	 Map<String, String> cookies = mainPage.cookies();
	 	Thread.sleep(1000);
	 	
	 	Document evaluationPage = Jsoup.connect(mainPageurl)
	            .cookies(cookies)
	            .execute().parse();
        
	 	return evaluationPage;
	 	
		} catch (IOException | InterruptedException e) {
			
			return null;
		}
		
	}
	/**
	* This method fetch the topic from the page then save it to the element
	*
	* @param  takes the document object as parameter
	* @return the topic object
	* 
	*/
	public static Element getTopic(Document homePage)
	{
		Elements topics = homePage.getElementsByClass("browse-by-list-item");
		Element topic = topics.get(topicIndex);
		topicIndex++;
		return topic;
	}
	
	/**
	* this method gets reviews from the page
	*
	* @param  String
	* @return the document object or null
	* 
	*/
	
	public static Document getReviewsPage(String topicUrl)
	{
		try {
			Thread.sleep(1000);
			
			// using the cookies to each time visiting any page
			Document reviews = Jsoup.connect(topicUrl)
					.cookies(loginForm.cookies())
		            .execute().parse();
			
			return reviews;
			
		} catch (InterruptedException | IOException e) {
			
			return null;
		}
		
	}
	
	/**
	* this method gets reviews from the page
	*
	* @param  document object
	* @return array list
	* 
	*/
	public static ArrayList<String> getNextPagesUrl(Document reviewPage)
	{
		ArrayList<String> nextPages = new ArrayList<>();
		
		Elements allNextPages = reviewPage.getElementsByClass("pagination-page-list-item");
		
		for(Element e : allNextPages)
		{
		
			nextPages.add(e.select("a[href]").attr("href").toString());
		}
		
		return nextPages;
	}
		
	
}
