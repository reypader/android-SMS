package com.rmpader.android.noonplication.activity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rmpader.android.noonplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConversationActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_COLUMN = "_id";
    public static final String THREAD_ID_COLUMN = "thread_id";
    public static final String DATE_COLUMN = "date";
    public static final String BODY_COLUMN = "body";
    public static final String TYPE_COLUMN = "type";
    public static final String[] SELECT_COLUMNS = {
            ID_COLUMN, DATE_COLUMN, BODY_COLUMN, TYPE_COLUMN
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Bundle b = getIntent().getExtras();
        getSupportLoaderManager().initLoader(1, b, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri allMessages = Uri.parse("content://sms/");
        Context context = this;
        switch (id) {
            case 1:
                return new CursorLoader(context, allMessages, SELECT_COLUMNS, THREAD_ID_COLUMN + "=?", new String[]{
                        args.getString("threadId")
                }, DATE_COLUMN + " asc");
            default:
                // An invalid id was passed in
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        final LinearLayout messages = (LinearLayout) findViewById(R.id.Messages);
        final ScrollView scrollView = (ScrollView) findViewById(R.id.MessagesScroll);
        messages.removeAllViews();
        int i = 0;
        while (data.moveToNext()) {
            if (data.getString(data.getColumnIndex(TYPE_COLUMN)).equals("1")) {
                getLayoutInflater().inflate(R.layout.message_in, messages, true);
                View rootView = messages.getChildAt(i);
                TextView body = (TextView) rootView.findViewById(R.id.MessageIn);
                body.setMaxWidth((int)(messages.getWidth() * 0.75f));
                body.setText(data.getString(data.getColumnIndex(BODY_COLUMN)));
                TextView time = (TextView) rootView.findViewById(R.id.TimeIn);
                time.setText(formatText(data.getString(data.getColumnIndex(DATE_COLUMN))));
            } else {
                getLayoutInflater().inflate(R.layout.message_out, messages, true);
                View rootView = messages.getChildAt(i);
                TextView body = (TextView) rootView.findViewById(R.id.MessageOut);
                body.setMaxWidth((int)(messages.getWidth() * 0.75f));
                body.setText(data.getString(data.getColumnIndex(BODY_COLUMN)));
                TextView time = (TextView) rootView.findViewById(R.id.TimeOut);
                time.setText(formatText(data.getString(data.getColumnIndex(DATE_COLUMN))));
            }
            i++;
        }
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private String formatText(String text) {
        long millis = Long.valueOf(text);
        Calendar dateSent = Calendar.getInstance();
        dateSent.setTimeInMillis(millis);
        DateFormat format;
        if (withinToday(dateSent)) {
            format = new SimpleDateFormat("h:mm a");
        } else {
            format = new SimpleDateFormat("MMM dd yyyy");
        }

        return format.format(dateSent.getTime());
    }

    private boolean withinToday(Calendar dateSent) {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) == dateSent.get(Calendar.YEAR) && now.get(Calendar.DAY_OF_YEAR) == dateSent.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        final LinearLayout messages = (LinearLayout) findViewById(R.id.Messages);
        Log.d("DERP", "DEEERRRP");
        messages.removeAllViews();
    }
}
