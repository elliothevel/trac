package com.example.newtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.example.newtest.CalendarActivity.AsyncServiceCall;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class RosterActivity extends ListActivity{
	//protected Context context;
	private String access_token;
	private ArrayList<Results> positionArray;
	private View mLoginStatusView;
	private AlertDialog alertDialog;
	private  SwipeRefreshLayout swipeLayout;
	private String url;
	private AsyncServiceCall asyncCall;
	public RosterAdapter var;
	private boolean executing = false;
	private OnScrollListener scrollListener;
    protected TextView firstVisibleItemText;
    protected TextView visibleItemCountText;
    protected TextView totalItemCountText;
    private ListView list;
    public boolean asyncExecuted = false;
    private int nextFifteen;
    int fakedTotalItemCount = 16;
    int maxTotalSessions;
	private String urlID;
	


	public void onPause(){
		super.onPause();
		   asyncCall.cancel(true);
	}
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//This controls the OnText Change Listener and Inflates it

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rostermenu, menu);

	    // Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_searchroster).getActionView();
	    // Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setIconifiedByDefault(false);
	    
	    // Do not iconify the widget; expand it by default

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                try{// this is your adapter that will be filtered
                	System.out.println("on text chnge text: "+newText);
                	((RosterAdapter) getListAdapter()).getFilter(newText);
                
                return true;
                }
                catch(Exception e){
                	System.out.println("Error " + e.getMessage());
                	Log.d("early","finally");
                	return true;
                	}
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                try{// this is your adapter that will be filtered
                	((RosterAdapter) getListAdapter()).getFilter(query);
                	System.out.println("on query submit: "+query);
                	return true;
                }
                finally{Log.d("early","finally");
                	return true;}
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
	    
	    return true;


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//If signout clicked, check for token in Shard Preferences and override
		//Bring to LoginActivity.class then clear the stack of previous pages.
		if (id == R.id.action_signout) {
			SharedPreferences pref = getSharedPreferences("userdetails", MODE_PRIVATE);
			Editor edit = pref.edit();
			edit.putString("token", "");
			edit.commit();
			asyncCall.cancel(true);
			

		}

		return super.onOptionsItemSelected(item);
	}

	
	
	 public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    //initialize content views
		    setContentView(R.layout.activity_roster);
		    mLoginStatusView = findViewById(R.id.login_status);
		    mLoginStatusView.setVisibility(View.VISIBLE);
		    
		    //Set Listeners for infinite scroll
		    //listView = (InfiniteScrollListView) this.getListView();
		    list = getListView();

		    Intent intent = getIntent();
			
	        // 2. get message value from intent
	       urlID = intent.getStringExtra("urlID");
		    //Get token from Shared Preferences and create url endpoint with token inserted
		    SharedPreferences userDetails = getSharedPreferences("userdetails",MODE_PRIVATE);
			   access_token = userDetails.getString("token","");
			
			   
		    //Initialize swipe to refresh layout, what happens when swiped: async task called again
		    swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		    swipeLayout.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
	        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
	            @Override
	            public void onRefresh() {
	                swipeLayout.setRefreshing(true);
	                Log.d("Swipe", "Refreshing Number");
	                asyncExecuted = false;
	                url = "https://trac-us.appspot.com/api/reg_tag/?id="+urlID+"&access_token=" + access_token;
	                asyncCall = (AsyncServiceCall) new AsyncServiceCall().execute(url);
	                ( new Handler()).postDelayed(new Runnable() {
	                    @Override
	                    public void run() {
	                        swipeLayout.setRefreshing(false);
	                        Log.d("Refresh","REfresh");
	                        //new AsyncServiceCall().execute(url);
	                    }
	                }, 3000);
	            }
		    
	        });
		    

	        
		  
		   //When No Internet connection, display alert dialog
		   
	        alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("No Internet Connectivity");
			alertDialog.setMessage("Please connect to the internet and reopen application.");
			alertDialog.setIcon(R.drawable.trac_launcher);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}});
			
			 url = "https://trac-us.appspot.com/api/reg_tag/?id="+urlID+"&access_token=" + access_token;
			//OnCreate Async Task Called, see below for async task class
			asyncCall =  (AsyncServiceCall) new AsyncServiceCall().execute(url);
		    
		  }
	 

		  @Override
		  protected void onListItemClick(ListView l, View v, int position, long id) {

		  }
		  
		  OkHttpClient client = new OkHttpClient();
			Gson gson = new Gson();
			private static final String DEBUG_TAG = "Debug";
			
			  public class AsyncServiceCall extends AsyncTask<String, Void, List<RosterJson>> {
				  
					@Override
					protected List<RosterJson> doInBackground(String... params) {
						Request request = new Request.Builder()
				        .url(params[0])
				        .build();
						try {
							   Response response = client.newCall(request).execute();
							   RosterJson[] preFullyParsed = gson.fromJson(response.body().charStream(), RosterJson[].class);
							   List<RosterJson> list = Arrays.asList(preFullyParsed);

						    return list;
						    
						} catch (IOException e) {
							//Log.d(DEBUG_TAG, "this is griffins fault now" + e.getMessage());
							return null;
						}
					}
					
					@Override
					protected void onPostExecute(List<RosterJson> result) {
						//Log.d("Finished", "Calendar Activity");
						
						//If the array/string doesnt come through alert will popup, hide spinner
						
						
						if(result==null){
							alertDialog.show();
							mLoginStatusView.setVisibility(View.GONE);
							// swipeLayout.setRefreshing(false);
						}
						else{
							//else parse the result and put in adapter, hide spinner
							var = new RosterAdapter(result, getApplicationContext());
							setListAdapter(var);

							mLoginStatusView.setVisibility(View.GONE);
							executing = false;
							// swipeLayout.setRefreshing(false);
						}
					}
			  }

			  

			



			
}

