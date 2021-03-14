package br.com.andremussio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.andremussio.data.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
