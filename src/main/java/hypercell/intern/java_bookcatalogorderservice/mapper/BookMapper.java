package hypercell.intern.java_bookcatalogorderservice.mapper;

import hypercell.intern.java_bookcatalogorderservice.dto.BookDto;
import hypercell.intern.java_bookcatalogorderservice.dto.UserDTO;
import hypercell.intern.java_bookcatalogorderservice.model.Book;
import hypercell.intern.java_bookcatalogorderservice.model.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BookMapper {
    public static Book toEntity(BookDto.Request book, User createdBy) {
        return Book.builder()
                .title(book.title())
                .isbn(book.isbn())
                .author(book.author())
                .price(book.price())
                .createdAt(ZonedDateTime.now(ZoneId.of("Z")))
                .updatedAt(ZonedDateTime.now(ZoneId.of("Z")))
                .availableQuantity(book.availableQuantity())
                .createdBy(createdBy)
                .build();
    }

    public static BookDto.Response toResponse(Book bookEntity) {
        return new BookDto.Response(
                bookEntity.getId(),
                bookEntity.getTitle(),
                bookEntity.getIsbn(),
                bookEntity.getAuthor(),
                bookEntity.getPrice(),
                bookEntity.getAvailableQuantity(),
                bookEntity.getCreatedAt(),
                bookEntity.getUpdatedAt(),
                new UserDTO.Response(
                        bookEntity.getCreatedBy().getId(),
                        bookEntity.getCreatedBy().getFirstname(),
                        bookEntity.getCreatedBy().getLastname(),
                        bookEntity.getCreatedBy().getCreatedAt(),
                        null
                )
        );
    }
}
