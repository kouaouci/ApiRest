package controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import entity.Book;
import repository.BookRepository;

@RestController
public class BookController {
	
	@Autowired
    BookRepository bookRepository;

	//find all books
    @GetMapping("/books")
    public List<Book> index(){
        return bookRepository.findAll();
    }

    //find one book by its id
    @GetMapping("/books/{id}")
    public Optional<Book> show(@PathVariable Long id){
        return Optional.of(bookRepository.findById(id).get());
    }

    
    @PostMapping("/books/search")
    public List<Book> search(@RequestBody Map<String, String> body){
        String searchTerm = body.get("text");
        return bookRepository.findByTitleContainingOrDescriptionContaining(searchTerm, searchTerm);
    }

    //create a new book and save in database
    @PostMapping("/books")
    public Book create(@RequestBody Book book){
        return bookRepository.save(book);
    }

    //update elements of a book and save them in database
    @PutMapping("/books/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book){
        // getting book
        Book bookToUpdate = bookRepository.findById(id).get();
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setDescription(book.getDescription());
        return bookRepository.save(bookToUpdate);
    }

    // delete a book from database
    @DeleteMapping("books/{id}")
    public boolean delete(@PathVariable Long id){
        bookRepository.deleteById(id);
        return true;
    }

}
