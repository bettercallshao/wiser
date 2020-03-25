package com.bettercallshao.wiser.spice;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.MessageFormat;

@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(length = 4096)
    public String url;

    @Column(length = 4096)
    public String title;

    @Column(length = 4096)
    public String link;

    public Timestamp timestamp;

    public String toString() {
        return MessageFormat.format("{0}: {1}, {2}, {3}, {4}, {5}", getClass().getName(), id, url, title, link, timestamp);
    }
}
