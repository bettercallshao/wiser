package com.bettercallshao.wiser.spice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Integer> {
    boolean existsByUrl(String url);

    List<Channel> findByUrl(String url);
}
