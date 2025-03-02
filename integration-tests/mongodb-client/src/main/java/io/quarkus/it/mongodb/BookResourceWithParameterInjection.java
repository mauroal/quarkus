package io.quarkus.it.mongodb;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import io.quarkus.mongodb.MongoClientName;
import io.smallrye.common.annotation.Blocking;

@Path("/books-with-parameter-injection")
@Blocking
public class BookResourceWithParameterInjection {
    private final MongoClient client;

    @Inject
    public BookResourceWithParameterInjection(@MongoClientName("parameter-injection") MongoClient client) {
        this.client = client;
    }

    private MongoCollection<Book> getCollection() {
        return client.getDatabase("books").getCollection("books-with-parameter-injection", Book.class);
    }

    @DELETE
    public Response clearCollection() {
        getCollection().deleteMany(new Document());
        return Response.ok().build();
    }

    @GET
    public List<Book> getBooks() {
        FindIterable<Book> iterable = getCollection().find();
        List<Book> books = new ArrayList<>();
        for (Book doc : iterable) {
            books.add(doc);
        }
        return books;
    }

    @POST
    public Response addBook(Book book) {
        getCollection().insertOne(book);
        return Response.accepted().build();
    }

    @GET
    @Path("/{author}")
    public List<Book> getBooksByAuthor(@PathParam("author") String author) {
        FindIterable<Book> iterable = getCollection().find(eq("author", author));
        List<Book> books = new ArrayList<>();
        for (Book doc : iterable) {
            String title = doc.getTitle();
            books.add(new Book().setTitle(title).setAuthor(author));
        }
        return books;
    }

}
