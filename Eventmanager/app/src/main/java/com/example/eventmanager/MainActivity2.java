package com.example.eventmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private ListView lEvents;
    private ArrayList<Event> events;
    private CustomEventAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        findViewById(R.id.exit_btn).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.create_new_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        findViewById(R.id.logout_btn).setOnClickListener(v -> {
            SharedPreferences sp= getSharedPreferences("useraccount", MODE_PRIVATE);
            SharedPreferences.Editor spEditor = sp.edit();

            spEditor.putBoolean("isremLogin",false);
            spEditor.putBoolean("isremUserid",false);
            spEditor.apply();

            Intent i = new Intent(MainActivity2.this, signuplogin.class);
            startActivity(i);

        });



        lEvents = findViewById(R.id.event_list);

    }

    protected void onStart() {
        super.onStart();
        String[] keys = {"action", "id", "semester"};
        String[] values = {"restore", "2018360082", "2023-1"};
//        httpRequest(keys, values);
        events = new ArrayList<>();
        KeyValueDB db = new KeyValueDB(this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        while (rows.moveToNext()) {
            String key = rows.getString(0);
            String eventData = rows.getString(1);
            String value[] = eventData.split("--");
            Event e = new Event(key, value[0],value[1],value[3],"","","","","","");
            events.add(e);
        }
        db.close();
        adapter = new CustomEventAdapter(this, events);
        lEvents.setAdapter(adapter);
        lEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int
                    position, long id) {

                System.out.println(position);
                Intent i = new Intent(MainActivity2.this,MainActivity.class);
                String value = db.getValueByKey(events.get(position).key);
                String values[] = value.split("--");
                String email = values[6];
                String user_id = values[9];
                i.putExtra("EVENT_KEY", events.get(position).key);
                i.putExtra("userid",user_id);
                i.putExtra("email",email);
                startActivity(i);
            }
        });
// handle the long-click on an event-list item
        lEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int
                    position, long id) {
                showDialogue("Sure to Delete the event "+events.get(position).name, "Confirmation", "Yes", "Cancel", events.get(position).key);
                return true;

            }
        });
    }
//    private void httpRequest(final String keys[],final String values[]){
//        new AsyncTask<Void,Void,String>(){
//            @Override
//            protected String doInBackground(Void... voids) {
//                List<NameValuePair> params=new ArrayList<NameValuePair>();
//                for (int i=0; i<keys.length; i++){
//                    params.add(new BasicNameValuePair(keys[i],values[i]));
//                }
//                String url= "https://www.muthosoft.com/univ/cse489/index.php";
//                String data="";
//                try {
//                    data=JSONParser.getInstance().makeHttpRequest(url,"POST",params);
//                    System.out.println(data);
//                    return data;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//            protected void onPostExecute(String data){
//                if(data!=null){
//                    System.out.println(data);
//                    System.out.println("Ok2");
//                    updateEventListByServerData(data);
//                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
//                }
//            }
//        }.execute();
//    }
//    public void updateEventListByServerData(String data){
//        System.out.println("found");
//        try{
//            JSONObject jo = new JSONObject(data);
//            if(jo.has("events")){
//                JSONArray ja = jo.getJSONArray("events");
//                for(int i=0; i<ja.length(); i++){
//                    JSONObject event = ja.getJSONObject(i);
//                    String eventKey = event.getString("e_key");
//                    String eventValue = event.getString("e_value");
//                    System.out.println(eventValue);
//                    String[] fieldValues = eventValue.split("---");
//                    System.out.println(fieldValues);
//
//                }
//            }
//        }catch(Exception e){}
//    }

    private void showDialogue(String msg, String title, String btn1, String btn2, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle(title);

        builder.setCancelable(false)
                .setPositiveButton(btn1, (dialog, which) -> {
                    if(btn1.equals("Yes")){
                        KeyValueDB db = new KeyValueDB(this);
                        db.deleteDataByKey(key);
                        db.close();
                        this.startActivity(new Intent(this, MainActivity2.class));
                    }
                    else {
                        dialog.cancel();
                    }
                })
                .setNegativeButton(btn2, (dialog, which) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();

    }

}