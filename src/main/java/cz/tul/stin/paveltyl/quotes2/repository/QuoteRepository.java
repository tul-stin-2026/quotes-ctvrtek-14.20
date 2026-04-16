package cz.tul.stin.paveltyl.quotes2.repository;

import cz.tul.stin.paveltyl.quotes2.model.Quote;

import java.util.List;

public interface QuoteRepository {
    List<Quote> findAll();
    Quote findById(Long id);
    Quote save(Quote quote);
}
