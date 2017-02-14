package other;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class BoomFilterDemo {
	public enum BookFunnel implements Funnel<Book> {
		FUNNEL;
		public void funnel(Book from, PrimitiveSink into) {
		into.putBytes(from.getIsbn().getBytes(Charsets.UTF_8))
			.putDouble(from.getPrize());
		}
	}
 public static class Book{
	 private String isbn;
	 private double prize;
	 private String author;
	 private String publisher;
	 
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public double getPrize() {
		return prize;
	}
	public void setPrize(double prize) {
		this.prize = prize;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
  public static void main(String[] args) {
	BloomFilter<Book>boomFileter=BloomFilter.create(BookFunnel.FUNNEL, 5);
	Book book=new Book();
	book.setIsbn("20171214-001");
	book.setPrize(12.5);
	book.setAuthor("zuiyanwu");
	book.setPublisher("wenl");
	boomFileter.put(book);
	//
	Book book2=new Book();
	book2.setIsbn("20171214-002");
	book2.setPrize(12.6);
	book2.setAuthor("zuiyanwu");
	book2.setPublisher("wenl");
	boomFileter.put(book2);
	
	Book q=new Book();
	q.setAuthor(book.getAuthor());
	q.setIsbn("20171214-001");
	q.setPrize(book.getPrize());

	System.out.println(boomFileter.mightContain(q));
	Book q2=new Book();
	q2.setIsbn("20171214-003");
	System.out.println(boomFileter.mightContain(q2));
  }
}
