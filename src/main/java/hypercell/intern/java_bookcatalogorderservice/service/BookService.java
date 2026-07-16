package hypercell.intern.java_bookcatalogorderservice.service;

import hypercell.intern.java_bookcatalogorderservice.dto.BookDto;
import hypercell.intern.java_bookcatalogorderservice.exception.NotFoundException;
import hypercell.intern.java_bookcatalogorderservice.model.Book;
import hypercell.intern.java_bookcatalogorderservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookDto.Response createBook(BookDto.Request bookRequest) {
        Book book = Book.builder().title(bookRequest.title())
                .isbn(bookRequest.isbn())
                .author(bookRequest.author())
                .price(bookRequest.price())
                .availableQuantity(bookRequest.availableQuantity())
                .createdAt(ZonedDateTime.now(ZoneId.of("Z")))
                .updatedAt(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        bookRepository.save(book);
        return new BookDto.Response(book.getId(),
                book.getTitle(), book.getIsbn(), book.getAuthor(),
                book.getPrice(), book.getAvailableQuantity(), book.getCreatedAt(), book.getUpdatedAt());
    }

    public BookDto.Response updateBook(Long id, BookDto.updateRequest bookRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
        book.setPrice(bookRequest.price());
        book.setAvailableQuantity(bookRequest.availableQuantity());
        book.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Z")));
        bookRepository.save(book);
        return new BookDto.Response(book.getId(),
                book.getTitle(), book.getIsbn(), book.getAuthor(),
                book.getPrice(), book.getAvailableQuantity(), book.getCreatedAt(), book.getUpdatedAt());
    }

    public BookDto.Response getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
        return new BookDto.Response(
                book.getId(),
                book.getTitle(), book.getIsbn(), book.getAuthor(),
                book.getPrice(), book.getAvailableQuantity(), book.getCreatedAt(), book.getUpdatedAt()
        );
    }

    public List<BookDto.Response> getAllBooks() {
        return bookRepository.findAll()
                .stream().map(book -> new BookDto.Response(
                        book.getId(),
                        book.getTitle(), book.getIsbn(), book.getAuthor(),
                        book.getPrice(), book.getAvailableQuantity(), book.getCreatedAt(), book.getUpdatedAt()
                )).toList();
    }

    public void deleteBookById(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
    }
}
