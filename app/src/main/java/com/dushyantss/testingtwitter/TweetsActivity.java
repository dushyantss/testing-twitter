package com.dushyantss.testingtwitter;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dushyantss.testingtwitter.helpers.Constants;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TweetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        UserTimeline userTimeline = new UserTimeline.Builder()
                .userId(Constants.MR_MODI_ID)
                .maxItemsPerRequest(Constants.MAX_ITEMS_PER_REQUEST)
                .build();

        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();

        View noTweets = findViewById(R.id.loading_indicator);
        ListView listView = (ListView) findViewById(android.R.id.list);

        listView.setEmptyView(noTweets);
        listView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
