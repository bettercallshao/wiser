package com.bettercallshao.wiser.spice;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChannelRepository extends CrudRepository<Channel, Integer> {
    boolean existsByUrl(String url);

    List<Channel> findByUrl(String url);
}
