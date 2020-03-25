package com.bettercallshao.wiser.manhattan;

import com.bettercallshao.wiser.spice.Channel;
import com.bettercallshao.wiser.spice.ChannelRepository;
import com.bettercallshao.wiser.spice.Item;
import com.bettercallshao.wiser.spice.ItemRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

@Component
@ComponentScan("com.bettercallshao.wiser.spice")
@EntityScan("com.bettercallshao.wiser.spice")
@EnableJpaRepositories("com.bettercallshao.wiser.spice")
public class Manhattan {

    private static final Logger log = LoggerFactory.getLogger(Manhattan.class);

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Value("${manhattan.url}")
    private String url;

    private Channel channel;

    @PostConstruct
    private void initialize() {
        if (!channelRepository.existsByUrl(url)) {
            var feed = getFeed();
            if (feed == null) {
                log.error("Failed to get feed for initialization.");
                System.exit(1);
            }
            var channel = new Channel();
            channel.url = url;
            channel.title = feed.getTitle();
            channel.link = feed.getLink();
            channel.timestamp = getTimestamp(new Date(0));
            channelRepository.save(channel);
        }
        channel = channelRepository.findByUrl(url).get(0);
        log.info("Working on Channel: {}", channel);
    }

    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void pollRss() {
        var feed = getFeed();
        if (feed != null) {
            for (var entry : feed.getEntries()) {
                var timestamp = getTimestamp(entry.getPublishedDate());
                if (timestamp.after(channel.timestamp)) {
                    var item = new Item();
                    item.title = entry.getTitle();
                    item.description = entry.getDescription().getValue();
                    item.link = entry.getLink();
                    item.timestamp = timestamp;
                    item.channel = channel;
                    itemRepository.save(item);
                    log.info("Added Item: {}", item);
                } else {
                    break;
                }
            }
            channel.timestamp = getTimestamp(feed.getPublishedDate());
            channelRepository.save(channel);
            log.info("Finished processing feed.");
        }
    }

    private Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    private SyndFeed getFeed() {
        try {
            XmlReader reader = new XmlReader(new URL(url));
            return new SyndFeedInput().build(reader);
        } catch (Exception e) {
            log.error("Failed to get feed from {}: {}", url, e.getMessage());
            return null;
        }
    }
}