package com.azad.springframeworkessentials.common.datetime;

import lombok.Getter;

public class DateInterval {

    @Getter
    private SimpleDate start;
    private SimpleDate end;

    public DateInterval(SimpleDate start, SimpleDate end) {
        this.start = start;
        this.end = end;
    }

    public SimpleDate getEnd() {
        return end;
    }
}
