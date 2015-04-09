package com.rmpader.android.noonplication.sms;

import android.database.Cursor;

/**
 * Created by Reynald on 4/4/2015.
 */
public class FullSMS {
    public static final String DATE_COLUMN = "date";
    public static final String PROTOCOL_COLUMN = "protocol";
    public static final String STATUS_COLUMN = "status";
    public static final String TYPE_COLUMN = "type";
    public static final String REPLY_PATH_PRESENT_COLUMN = "reply_path_present";
    public static final String SERVICE_CENTER_COLUMN = "service_center";
    public static final String LOCKED_COLUMN = "locked";
    public static final String SUB_ID_COLUMN = "sub_id";
    public static final String ERROR_CODE_COLUMN = "error_code";

    public static final String[] COLUMNS = {
            DATE_COLUMN,
            PROTOCOL_COLUMN,
            STATUS_COLUMN,
            TYPE_COLUMN,
            REPLY_PATH_PRESENT_COLUMN,
            SERVICE_CENTER_COLUMN,
            LOCKED_COLUMN,
            SUB_ID_COLUMN,
            ERROR_CODE_COLUMN
    };

    private final BareSMS bareSMS;
    private final String date;
    private final String protocol;
    private final String status;
    private final String type;
    private final String replyPathPresent;
    private final String serviceCenter;
    private final String locked;
    private final String subId;
    private final String errorCode;

    public FullSMS(BareSMS bareSMS, Cursor cursor) {
        this.bareSMS = bareSMS;
        this.date = cursor.getString(cursor.getColumnIndex(DATE_COLUMN));
        this.protocol = cursor.getString(cursor.getColumnIndex(PROTOCOL_COLUMN));
        this.status = cursor.getString(cursor.getColumnIndex(STATUS_COLUMN));
        this.type = cursor.getString(cursor.getColumnIndex(TYPE_COLUMN));
        this.replyPathPresent = cursor.getString(cursor.getColumnIndex(REPLY_PATH_PRESENT_COLUMN));
        this.serviceCenter = cursor.getString(cursor.getColumnIndex(SERVICE_CENTER_COLUMN));
        this.locked = cursor.getString(cursor.getColumnIndex(LOCKED_COLUMN));
        this.subId = cursor.getString(cursor.getColumnIndex(SUB_ID_COLUMN));
        this.errorCode = cursor.getString(cursor.getColumnIndex(ERROR_CODE_COLUMN));
    }

    public String getId() {
        return bareSMS.getId();
    }

    public String getThreadId() {
        return bareSMS.getThreadId();
    }

    public String getAddress() {
        return bareSMS.getAddress();
    }

    public String getPerson() {
        return bareSMS.getPerson();
    }

    public String getDate() {
        return date;
    }

    public String getDateSent() {
        return bareSMS.getDateSent();
    }

    public String getProtocol() {
        return protocol;
    }

    public boolean isRead() {
        return bareSMS.isRead();
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getReplyPathPresent() {
        return replyPathPresent;
    }

    public String getSubject() {
        return bareSMS.getSubject();
    }

    public String getBody() {
        return bareSMS.getBody();
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    public String getLocked() {
        return locked;
    }

    public String getSubId() {
        return subId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getCreator() {
        return bareSMS.getCreator();
    }

    public boolean isSeen() {
        return bareSMS.isSeen();
    }

}


     