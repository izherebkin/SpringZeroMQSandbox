package org.izherebkin.integration.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.List;

public class MessageDto {

    private String senderName;

    private LocalDateTime senderTime;

    private List<RandomDto> randoms;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public LocalDateTime getSenderTime() {
        return senderTime;
    }

    public void setSenderTime(LocalDateTime senderTime) {
        this.senderTime = senderTime;
    }

    public List<RandomDto> getRandoms() {
        return randoms;
    }

    public void setRandoms(List<RandomDto> randoms) {
        this.randoms = randoms;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("senderName", senderName)
                .append("senderTime", senderTime).append("randoms", randoms).toString();
    }

}
