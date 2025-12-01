package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    void testFindAll() {
        when(bidListRepository.findAll()).thenReturn(List.of(new BidList()));

        List<BidList> result = bidListService.findAll();

        assertEquals(1, result.size());
        verify(bidListRepository).findAll();
    }

    @Test
    void testSave() {
        BidList bid = new BidList("Account", "Type", 10.0);
        when(bidListRepository.save(any())).thenReturn(bid);

        BidList result = bidListService.save(bid);

        assertNotNull(result);
        verify(bidListRepository).save(bid);
    }

    @Test
    void testFindById() {
        BidList bid = new BidList("Account", "Type", 10.0);
        bid.setBidListId(1);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));
        BidList result = bidListService.findById(1);

        assertEquals("Account", result.getAccount());
        verify(bidListRepository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bidListService.findById(1));
    }

    @Test
    void testUpdate() {

        BidList existingBid = new BidList("Account", "Type", 5.0);
        existingBid.setBidListId(1);

        BidList updatedData = new BidList("New Account", "New Type", 20.0);
        updatedData.setBidListId(1);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(existingBid));
        when(bidListRepository.save(any())).thenReturn(existingBid);

        BidList result = bidListService.update(1, updatedData);

        assertEquals("New Account", result.getAccount());
        assertEquals("New Type", result.getType());
        assertEquals(20.0, result.getBidQuantity());

        verify(bidListRepository).findById(1);
        verify(bidListRepository).save(existingBid);
    }


    @Test
    void testDelete() {
        BidList bid = new BidList();
        bid.setBidListId(1);

        bidListService.delete(bid);

        verify(bidListRepository).delete(bid);
    }

}
