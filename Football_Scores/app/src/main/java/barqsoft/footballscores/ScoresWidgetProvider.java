package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;

import barqsoft.footballscores.service.myFetchService;

public class ScoresWidgetProvider extends AppWidgetProvider {

    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_today);
            //views.setOnClickPendingIntent(R.id.button, pendingIntent);

            Intent service_start = new Intent(context, myFetchService.class);
            context.startService(service_start);

            View rootView = LayoutInflater.from(context).inflate(R.layout.fragment_main, null, false);
            final ListView score_list = (ListView) rootView.findViewById(R.id.scores_list);
            scoresAdapter mAdapter = new scoresAdapter(context,null,0);
            score_list.setAdapter(mAdapter);
            //getLoaderManager().initLoader(SCORES_LOADER,null,this);
            mAdapter.detail_match_id = MainActivity.selected_match_id;
            score_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            });
            views.addView(R.id.button, rootView);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}