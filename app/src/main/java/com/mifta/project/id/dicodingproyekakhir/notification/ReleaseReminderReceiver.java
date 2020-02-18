package com.mifta.project.id.dicodingproyekakhir.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mifta.project.id.dicodingproyekakhir.BuildConfig;
import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.MoviesDetailActivity;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ReleaseReminderReceiver extends BroadcastReceiver {
    public static final String EXTRA_TYPE = "type";
    public static int RELEASE_ID = 2;
    public ArrayList<MoviesItems> listItem;

    public ReleaseReminderReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final int reminder = intent.getIntExtra(EXTRA_TYPE, RELEASE_ID);
        if (reminder == RELEASE_ID) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
            final String today = day.format(new Date());
            AsyncHttpClient client = new AsyncHttpClient();
            listItem = new ArrayList<>();
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.MY_TMDB_API_KEY + "&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
            client.get(url, new JsonHttpResponseHandler() {
                @SuppressLint("ResourceType")
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONArray list = response.getJSONArray("results");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject movie = list.getJSONObject(i);
                            MoviesItems moviesItems = new MoviesItems();
                            moviesItems.setId(movie.getInt("id"));
                            moviesItems.setTitle(movie.getString("title"));
                            moviesItems.setDate(movie.getString("release_date"));
                            moviesItems.setRating(movie.getString("popularity"));
                            moviesItems.setCountry(movie.getString("original_language"));
                            moviesItems.setOverview(movie.getString("overview"));
                            moviesItems.setPhoto("https://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
                            listItem.add(moviesItems);
                            showAlarmNotification(context, context.getResources().getString(R.string.title_release), null, reminder, moviesItems);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }
    }

    public void releaseReminderOn(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminderReceiver.class);
        intent.putExtra(EXTRA_TYPE, RELEASE_ID);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RELEASE_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void releaseReminderOff(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RELEASE_ID, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private PendingIntent getPendingIntent(Context context, int notificationId, MoviesItems item) {
        Intent intent;
        if (notificationId == RELEASE_ID) {
            intent = new Intent(context, MoviesDetailActivity.class);
            intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, item);
        } else {
            return null;
        }
        return PendingIntent.getActivity(context, notificationId, intent, 0);
    }

    private void showAlarmNotification(Context context, String title, @Nullable String message, int notifId, @Nullable MoviesItems item) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager chanel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        if (listItem.size() != 0) {
            for (int i = 0; i < listItem.size(); i++) {
                inboxStyle.addLine(listItem.get(i).getTitle());
            }
        }
        NotificationCompat.Builder builder;
        if (message == null) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle(title)
                    .setStyle(inboxStyle)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent));
        } else {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent));
        }

        PendingIntent pendingIntent = getPendingIntent(context, notifId, item);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(RELEASE_ID, notification);
        }
    }
}
