package cz.tul.stin.paveltyl.quotes2.service;

import cz.tul.stin.paveltyl.quotes2.model.ExternalQuote;
import cz.tul.stin.paveltyl.quotes2.model.Quote;
import cz.tul.stin.paveltyl.quotes2.repository.QuoteRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuoteService {
    private static final String RANDOM_QUOTE_URL = "https://zenquotes.io/api/random";

    @Getter
    private final List<Quote> quotes = new ArrayList<>();
    private final RestOperations restOperations;
    private final QuoteRepository quoteRepository;

    public QuoteService(RestOperations restOperations, QuoteRepository quoteRepository) {
        this.restOperations = restOperations;
        this.quoteRepository = quoteRepository;
        quotes.add(new Quote(1L, "Komu se neleni, tomu se zeleni.", "neznamy autor"));
    }

    public Quote getQuote(Long id) {
        return quotes.stream().filter(quote -> quote.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Quote with id " + id + " not found."));
    }

    public Quote saveQuote(@RequestBody Quote quote) {
        quote.setId((long) quotes.size() + 1);
        quotes.add(quote);
        return quote;
    }

    public Quote getRandomQuote() {

        ExternalQuote[] response =
                restOperations.getForObject(RANDOM_QUOTE_URL, ExternalQuote[].class);

        if (response == null || response.length == 0) {
            throw new RuntimeException("Empty response!");
        }

        ExternalQuote external = response[0];

        return new Quote(null, external.getQ(), external.getA());
    }

    public Quote saveRandomQuote() {
        Quote quote = getRandomQuote();
        return quoteRepository.save(quote);
    }
}
