package br.com.tomcat.weld;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named(value = "bookFactory")

@SessionScoped

public class BookFactory implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String text1 = "Write your book in a sec!";
    private final String text2 = "List of written books";

    @Inject
    EntityManager em;

    private Book book = new Book();
    List<Book> books = new ArrayList<Book>();

    public String getText1()
    {
        return text1;
    }

    public void setBook(Book book)
    {
        this.book = book;
    }

    public Book getBook()
    {
        return book;
    }

    public List<Book> getBooks()
    {
        return books;
    }

    @SuppressWarnings({"unchecked"})
    @Transactional
    public void saveBook()
    {
    	//FIXME bug do postgres com JPA. nao consegui fazer funcionar com sequence
    	book.setId(new Long( 1 + (int)(Math.random()*999999999)));
        em.persist(book);
        books = em.createQuery("from Book").getResultList();
        book = new Book();
    }

    public String getText2()
    {
        return text2;  //To change body of created methods use File | Settings | File Templates.
    }
}