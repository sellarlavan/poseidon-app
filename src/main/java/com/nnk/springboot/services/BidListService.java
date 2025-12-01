package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {
    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public BidList save(BidList bid) {
        return bidListRepository.save(bid);
    }

    public BidList findById(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id : " + id));
    }

    public BidList update(Integer id, BidList updatedBid) {
        BidList existing = findById(id);

        existing.setAccount(updatedBid.getAccount());
        existing.setType(updatedBid.getType());
        existing.setBidQuantity(updatedBid.getBidQuantity());

        return bidListRepository.save(existing);
    }

    public void delete(BidList bidList) {
        bidListRepository.delete(bidList);
    }
}
