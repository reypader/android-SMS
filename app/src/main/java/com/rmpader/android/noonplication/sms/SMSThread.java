package com.rmpader.android.noonplication.sms;

import com.rmpader.android.noonplication.exception.MismatchedMessageException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Reynald on 4/4/2015.
 */
public class SMSThread {

    private final String threadId;
    private List<BareSMS> messages = new ArrayList<>();

    public SMSThread(String threadId) {
        this.threadId = threadId;
    }

    public void addSMS(BareSMS sms) throws MismatchedMessageException {
        if (sms.getThreadId().equals(threadId)) {
            messages.add(sms);
        } else {
            throw new MismatchedMessageException("SMS with _id: " + sms.getId() + " does not belong here (thread_id:" + threadId + ")");
        }
    }

    public List<BareSMS> sort() {
        Collections.sort(messages);
        return messages;
    }

    public BareSMS getLatestMessage() {
        return messages.get(0);
    }

    public boolean hasNew() {
        return !getLatestMessage().isRead();
    }

}
