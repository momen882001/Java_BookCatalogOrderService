package hypercell.intern.java_bookcatalogorderservice.controller;

import hypercell.intern.java_bookcatalogorderservice.dto.BookDto;
import hypercell.intern.java_bookcatalogorderservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto.Response> createBook(@RequestBody BookDto.Request bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookRequest));
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<BookDto.Response> updateBook(@PathVariable Long id, @RequestBody BookDto.updateRequest bookRequest) {
//        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BookDto.Response> getBookById(@PathVariable Long id) {
//        return ResponseEntity.ok(bookService.getBookById(id));
//    }

//    @GetMapping
//    public ResponseEntity<List<BookDto.Response>> getAllBooks() {
//        return ResponseEntity.ok(bookService.getAllBooks());
//    }
}
