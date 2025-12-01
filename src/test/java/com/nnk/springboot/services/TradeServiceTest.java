package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    void testFindAll() {
        when(tradeRepository.findAll()).thenReturn(List.of(new Trade()));

        List<Trade> result = tradeService.findAll();

        assertEquals(1, result.size());
        verify(tradeRepository).findAll();
    }

    @Test
    void testSave() {
        Trade trade = new Trade("Account", "Type");

        when(tradeRepository.save(any())).thenReturn(trade);

        Trade result = tradeService.save(trade);

        assertNotNull(result);
        assertEquals("Account", result.getAccount());
        verify(tradeRepository).save(trade);
    }

    @Test
    void testFindById() {
        Trade trade = new Trade("Account", "Type");
        trade.setId(1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.findById(1);

        assertEquals("Account", result.getAccount());
        verify(tradeRepository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tradeService.findById(1));
    }

    @Test
    void testUpdate() {
        Trade existing = new Trade("Account", "Type");
        existing.setId(1);

        Trade updated = new Trade("New Account", "New Type");

        when(tradeRepository.findById(1)).thenReturn(Optional.of(existing));
        when(tradeRepository.save(any())).thenReturn(existing);

        Trade result = tradeService.update(1, updated);

        assertEquals("New Account", result.getAccount());
        assertEquals("New Type", result.getType());
        verify(tradeRepository).save(existing);
    }

    @Test
    void testDelete() {
        Trade trade = new Trade();
        trade.setId(1);

        tradeService.delete(trade);

        verify(tradeRepository).delete(trade);
    }
}
