package cz.tul.stin.paveltyl.quotes2.service;

import cz.tul.stin.paveltyl.quotes2.model.ExternalQuote;
import cz.tul.stin.paveltyl.quotes2.model.Quote;
import cz.tul.stin.paveltyl.quotes2.repository.QuoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.web.client.RestOperations;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class QuoteServiceTest {
    private static final String RANDOM_QUOTE_URL = "https://zenquotes.io/api/random";

    @Mock
    RestOperations restOperations;
    @Mock
    QuoteRepository quoteRepository;
    @InjectMocks
    QuoteService quoteService;

    @Test
    void getRandomQuote_returnsMappedQuote() {
        ExternalQuote externalQuote = new ExternalQuote();
        externalQuote.setQ("Testovaci citat.");
        externalQuote.setA("Testovaci jmeno");

        when(restOperations.getForObject(
                eq(RANDOM_QUOTE_URL),
                eq(ExternalQuote[].class)
        )).thenReturn(new ExternalQuote[]{externalQuote});

        Quote result = quoteService.getRandomQuote();

        verify(restOperations).getForObject(
                eq(RANDOM_QUOTE_URL),
                eq(ExternalQuote[].class)
        );

        assertEquals("Testovaci citat.", result.getText());
        assertEquals("Testovaci jmeno", result.getAuthor());

        assertNull(result.getId());
    }

    @Test
    void getRandomQuote_returnsEmptyQuote() {

    }

    @Test
    void saveRandomQuote_returnsSavedQuote() {
        ExternalQuote externalQuote = new ExternalQuote();
        externalQuote.setQ("Ulozeny testovaci citat.");
        externalQuote.setA("Ulozeny testovaci autor");

        when(restOperations.getForObject(
                eq(RANDOM_QUOTE_URL),
                eq(ExternalQuote[].class)
        )).thenReturn(new ExternalQuote[]{externalQuote});

        Quote savedQuote = new Quote();
        savedQuote.setId(100L);
        savedQuote.setText("Ulozeny testovaci citat.");
        savedQuote.setAuthor("Ulozeny testovaci autor");

        when(quoteRepository.save(any(Quote.class))).thenReturn(savedQuote);

        Quote result = quoteService.saveRandomQuote();

        verify(restOperations).getForObject(
                eq(RANDOM_QUOTE_URL),
                eq(ExternalQuote[].class)
        );

        assertEquals("Ulozeny testovaci citat.", result.getText());
        assertEquals("Ulozeny testovaci autor", result.getAuthor());
        assertEquals(100L, result.getId());
    }
}
