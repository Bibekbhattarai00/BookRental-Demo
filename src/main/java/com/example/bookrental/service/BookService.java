package com.example.bookrental.service;

import com.example.bookrental.dto.BookDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Book;

import java.util.List;

public interface BookService {
    public Book addBook(BookDto  bookDto);
    public Book updateBook(BookDto  bookDto);
    public List<Book> getAllBook();
    public Book findById(Long id);
    public String deleteBook(Long id);


}