package com.bettercallshao.wiser.spice;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {
    List<Item> findAllByOrderByTimestampDesc(Pageable pageable);
}
