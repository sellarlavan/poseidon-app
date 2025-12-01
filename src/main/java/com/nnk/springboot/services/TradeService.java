package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade findById(Integer id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid Trade Id: " + id));
    }

    public Trade update(Integer id, Trade updatedTrade) {
        Trade existing = findById(id);

        existing.setAccount(updatedTrade.getAccount());
        existing.setType(updatedTrade.getType());
        existing.setBuyQuantity(updatedTrade.getBuyQuantity());

        return tradeRepository.save(existing);
    }

    public void delete(Trade trade) {
        tradeRepository.delete(trade);
    }
}
