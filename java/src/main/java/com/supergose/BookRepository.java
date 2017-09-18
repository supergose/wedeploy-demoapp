package com.supergose;

public interface BookRepository {

    Book getByIsbn(String isbn);

}