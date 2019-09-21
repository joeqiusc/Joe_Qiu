package com.revature.models;

import java.util.Objects;

public class Book {
	
	private int id;
	private String isbn;
	private String title;
	private String author;
	private String genre;
	private int stockCount;
	
	public Book() {
		super();
	}

	public Book(String isbn, String title, String author, String genre, int stockCount) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.stockCount = stockCount;
	}

	public Book(int id, String isbn, String title, String author, String genre, int stockCount) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.stockCount = stockCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getStockCount() {
		return stockCount;
	}

	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, genre, id, isbn, stockCount, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Book))
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(genre, other.genre) && id == other.id
				&& Objects.equals(isbn, other.isbn) && stockCount == other.stockCount
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title + ", author=" + author + ", genre=" + genre
				+ ", stockCount=" + stockCount + "]";
	}

}
