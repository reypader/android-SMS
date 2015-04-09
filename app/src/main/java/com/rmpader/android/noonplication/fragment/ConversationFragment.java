package com.rmpader.android.noonplication.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.rmpader.android.noonplication.R;
import com.rmpader.android.noonplication.activity.ConversationActivity;
import com.rmpader.android.noonplication.sms.BareSMS;
import com.rmpader.android.noonplication.sms.SMSThread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Reynald on 4/4/2015.
 */
public class ConversationFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_COLUMN = "_id";
    public static final String THREAD_ID_COLUMN = "thread_id";
    public static final String ADDRESS_COLUMN = "address";
    public static final String DATE_SENT_COLUMN = "date_sent";
    public static final String READ_COLUMN = "read";
    public static final String BODY_COLUMN = "body";
    public static final String SEEN_COLUMN = "seen";
    public static final String TYPE_COLUMN = "type";
    public static final String[] SELECT_COLUMNS = {
            "distinct " + THREAD_ID_COLUMN,
            ADDRESS_COLUMN,
            "max(" + DATE_SENT_COLUMN + ") as " + DATE_SENT_COLUMN,
            READ_COLUMN,
            BODY_COLUMN,
            SEEN_COLUMN,
            TYPE_COLUMN,
            ID_COLUMN
    };
    private static final int URL_LOADER = 0;
    private Map<String, SMSThread> threads = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(URL_LOADER, null, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("Cannot find any messages.");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CursorWrapper cursor = (CursorWrapper) getListView().getItemAtPosition(position);
        String threadId = cursor.getString(cursor.getColumnIndex(THREAD_ID_COLUMN));
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        Bundle b = new Bundle();
        b.putString("threadId", threadId); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri allMessages = Uri.parse("content://sms/");
        Context context = getActivity();
        switch (id) {
            case URL_LOADER:
                return new CursorLoader(context, allMessages, SELECT_COLUMNS, ADDRESS_COLUMN + " is not null) group by (" + THREAD_ID_COLUMN, null, DATE_SENT_COLUMN + " desc");
            default:
                // An invalid id was passed in
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.convo_row, data, new String[]{
                ADDRESS_COLUMN, BODY_COLUMN, DATE_SENT_COLUMN,
        }, new int[]{R.id.person, R.id.body_preview, R.id.date_sent}) {
            @Override
            public void setViewText(TextView v, String text) {
                super.setViewText(v, formatText(v, text));
            }

            private String formatText(TextView view, String text) {
                String toReturn = text;
                if (view.getId() == R.id.date_sent) {
                    long millis = Long.valueOf(text);
                    Calendar dateSent = Calendar.getInstance();
                    dateSent.setTimeInMillis(millis);
                    DateFormat format;
                    if (withinToday(dateSent)) {
                        format = new SimpleDateFormat("h:mm a");
                    } else {
                        format = new SimpleDateFormat("MMM dd yyyy");
                    }

                    toReturn = format.format(dateSent.getTime());
                }
                return toReturn;
            }
        };
        setListAdapter(adapter);
    }

    private boolean withinToday(Calendar dateSent) {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) == dateSent.get(Calendar.YEAR) && now.get(Calendar.DAY_OF_YEAR) == dateSent.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}


