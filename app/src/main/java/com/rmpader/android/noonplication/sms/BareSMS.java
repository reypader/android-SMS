package com.rmpader.android.noonplication.sms;

import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Reynald on 4/4/2015.
 *
 * Initially created to represent SMS table rows as an object but consumes a lot of steps.
 */
public class BareSMS implements Comparable<BareSMS> {
    public static final String ID_COLUMN = "_id";
    public static final String THREAD_ID_COLUMN = "thread_id";
    public static final String ADDRESS_COLUMN = "address";
    public static final String PERSON_COLUMN = "person";
    public static final String DATE_SENT_COLUMN = "date_sent";
    public static final String READ_COLUMN = "read";
    public static final String SUBJECT_COLUMN = "subject";
    public static final String BODY_COLUMN = "body";
    public static final String CREATOR_COLUMN = "creator";
    public static final String SEEN_COLUMN = "seen";
    public static final String[] COLUMNS = {
            ID_COLUMN,
            THREAD_ID_COLUMN,
            ADDRESS_COLUMN,
            PERSON_COLUMN,
            DATE_SENT_COLUMN,
            READ_COLUMN,
            SUBJECT_COLUMN,
            BODY_COLUMN,
            CREATOR_COLUMN,
            SEEN_COLUMN
    };
    private final String id;
    private final String threadId;
    private final String address;
    private final String person;
    private final String dateSent;
    private final boolean read;
    private final String subject;
    private final String body;
    private final String creator;
    private final boolean seen;
    private Map<String, String> fieldMap;

    public BareSMS(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(ID_COLUMN));
        this.threadId = cursor.getString(cursor.getColumnIndex(THREAD_ID_COLUMN));
        this.address = cursor.getString(cursor.getColumnIndex(ADDRESS_COLUMN));
        this.person = cursor.getString(cursor.getColumnIndex(PERSON_COLUMN));
        this.dateSent = cursor.getString(cursor.getColumnIndex(DATE_SENT_COLUMN));
        this.read = cursor.getString(cursor.getColumnIndex(READ_COLUMN)).equals("0") ? false : true;
        this.subject = cursor.getString(cursor.getColumnIndex(SUBJECT_COLUMN));
        this.body = cursor.getString(cursor.getColumnIndex(BODY_COLUMN));
        this.creator = cursor.getString(cursor.getColumnIndex(CREATOR_COLUMN));
        this.seen = cursor.getString(cursor.getColumnIndex(SEEN_COLUMN)).equals("0") ? false : true;
    }

    public String getId() {
        return id;
    }

    public String getThreadId() {
        return threadId;
    }

    public String getAddress() {
        return address;
    }

    public String getPerson() {
        return person;
    }

    public String getDateSent() {
        return dateSent;
    }

    public boolean isRead() {
        return read;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getCreator() {
        return creator;
    }

    public boolean isSeen() {
        return seen;
    }

    @Override
    public int compareTo(BareSMS another) {
        int sentDate = Integer.valueOf(getDateSent());
        int otherSentDate = Integer.valueOf(another.getDateSent());
        if (sentDate == otherSentDate) {
            return 0;
        } else if (sentDate > otherSentDate) {
            //more recent sent date must be placed towards the head of the list
            return -1;
        } else {
            return 1;
        }
    }

    public Map<String, String> toFieldMap() {
        if (fieldMap == null) {
            fieldMap = new HashMap<>();
            fieldMap.put(ID_COLUMN, this.id);
            fieldMap.put(THREAD_ID_COLUMN, this.threadId);
            fieldMap.put(ADDRESS_COLUMN,this.address);
            fieldMap.put(PERSON_COLUMN, this.person);
            DateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm");
            fieldMap.put(DATE_SENT_COLUMN, format.format(new Date(Long.valueOf(this.dateSent))));
            fieldMap.put(READ_COLUMN, this.read ? "1" : "0");
            fieldMap.put(SUBJECT_COLUMN, this.subject);
            fieldMap.put(BODY_COLUMN, this.body);
            fieldMap.put(CREATOR_COLUMN, this.creator);
            fieldMap.put(SEEN_COLUMN, this.seen ? "1" : "0");
        }
        return fieldMap;
    }
}
