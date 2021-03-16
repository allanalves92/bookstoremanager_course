
package com.bookstore.bookstoremanager.author.exception;

import javax.persistence.*;

public class AuthorNotFoundException extends EntityNotFoundException {

	public AuthorNotFoundException(Long id) {
		super(String.format("Author with id %s not exists!", id));
	}
}
