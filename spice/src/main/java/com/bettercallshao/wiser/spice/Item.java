package com.bettercallshao.wiser.spice;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.MessageFormat;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(length = 4096)
    public String title;

    @Column(length = 4096)
    public String description;

    @Column(length = 4096)
    public String link;

    public Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id")
    public Channel channel;

    public String toString() {
        return MessageFormat.format("{0}: {1}, {2}, {3}, {4}", getClass().getName(), id, title, timestamp, channel.url);
    }
}
