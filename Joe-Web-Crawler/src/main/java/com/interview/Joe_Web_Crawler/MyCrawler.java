package com.interview.Joe_Web_Crawler;

import java.util.ArrayList;

import org.jsoup.nodes.Document;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.interview.Joe_Web_Crawler.FileHandler;
import com.interview.Joe_Web_Crawler.URLHandler;
/**
 * @author Joe Qiu 5/28/2021
 * This is the main class that launch the crawler
 */
public class MyCrawler {

	public static void main(String[] args) {
	
		System.out.println("^^_^^Launch the crawler............");
		// Initiate the homePage
		Document homePage = URLHandler.intiateSession();
		
		// Fetch the element from the List
		Element top = URLHandler.getTopic(homePage);

		// Iterate the review from the first page
		Document reviewPage = URLHandler.getReviewsPage(top.select("a[href]").attr("href").toString());
		
		//traverse all the review pages and save them to the ArrayList
		ArrayList<String> allReviewsPages = URLHandler.getNextPagesUrl(reviewPage);
		
		// fetch the elements
		ArrayList<Elements> allReviews =  new ArrayList<>();
		
		// Traversing through all pages to get the reviews
		for(String pageurl : allReviewsPages)
		{
			Document page = URLHandler.getReviewsPage(pageurl);
			Elements reviews = page.getElementsByClass("search-results-item-body");
			allReviews.add(reviews);
		}
		
		// Write the reviews to file
		if(FileHandler.createFile(allReviews,top.text()))
			{
			System.out.println("Job Done");
			};
	
	}
		
}
