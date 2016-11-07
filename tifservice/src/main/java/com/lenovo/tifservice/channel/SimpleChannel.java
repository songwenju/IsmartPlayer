package com.lenovo.tifservice.channel;

/**
 * songwenju on 16-11-3 : 16 : 45.
 * 邮箱：songwenju@outlook.com
 */

public class SimpleChannel {

    public String title;
    public String playAddress;

    public SimpleChannel() {
    }

    public SimpleChannel(String title, String playAddress) {
        this.title = title;
        this.playAddress = playAddress;
    }

    @Override
    public String toString() {
        return "SimpleChannel{" +
                "title='" + title + '\'' +
                ", playAddress='" + playAddress + '\'' +
                '}';
    }
}
