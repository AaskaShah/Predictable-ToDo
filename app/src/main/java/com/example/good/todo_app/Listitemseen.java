package com.example.good.todo_app;

/**
 * Created by good on 25/12/15.
 */
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.good.todo_app.database.TablelistDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Listitemseen extends Activity{
    TextView name;
    TextView details,datedisplay,infodisplay;
    TextView wantanalarm;
    TextView dateenter,timeenter;
    EditText date,time;
    Button notification;
    int id=0;
    private int mYear,mMonth,mDay,mHour,mMinute;
    private TablelistDataSource datasource;
    Listitem itemwithid =new Listitem(null,null,null,null,0);

    @Override
    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listitem);
        name=(TextView)findViewById(R.id.listitemhead);
        details=(TextView)findViewById(R.id.listitemdetails);
        datedisplay=(TextView)findViewById(R.id.listitemdate);
        infodisplay=(TextView)findViewById(R.id.listiteminfo);

        datasource = new TablelistDataSource(this);
        datasource.open();



        final Calendar current = Calendar.getInstance();
        System.out.println( "Present Time is: "+current.getTime());

        dateenter=(TextView)findViewById(R.id.enterdate);
        timeenter=(TextView)findViewById(R.id.entertime);
        date=(EditText)findViewById(R.id.date);
        time=(EditText)findViewById(R.id.time);
        wantanalarm=(TextView)findViewById(R.id.wantanalarm);


        //thing_des.setText(getIntent().getExtras().getString("ThingDes"));
        name.setText(getIntent().getExtras().getString("n"));
        details.setText(getIntent().getExtras().getString("det"));
        datedisplay.setText(getIntent().getExtras().getString("date"));
        id=getIntent().getExtras().getInt("id");

        itemwithid=datasource.getitemwithid(id);
        if(itemwithid.getInfo().equals("")){


            notification=(Button)findViewById(R.id.noti);
            //----date picker
            final Calendar c=Calendar.getInstance();
            mYear=c.get(Calendar.YEAR);
            mMonth=c.get(Calendar.MONTH);
            mDay=c.get(Calendar.DAY_OF_MONTH);

            final DatePickerDialog dpd = new DatePickerDialog(/*'this' here is not working*/this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            date.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dpd.show();
                }
            });
//-------
//----time picker----

            mHour=c.get(Calendar.HOUR_OF_DAY);
            mMinute=c.get(Calendar.MINUTE);

            final TimePickerDialog tpd=new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time.setText(hourOfDay + ":" + minute);
                }
            },mHour,mMinute,false);
            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tpd.show();
                }

            });
//-------notification
            notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String datetime=date.toString()+" "+time.toString();




                    if(c.compareTo(current) <= 0){
                        //The set Date/Time already passed
                        Toast.makeText(getApplicationContext(),
                                "Invalid Date/Time",
                                Toast.LENGTH_LONG).show();
                    }else{

                        setNotification(c);
                        itemwithid.setInfo(" Alarm is set for " + c.getTime());
                        datasource.alterlist(itemwithid," Alarm is set for "+ c.getTime());
                        infodisplay.setText(itemwithid.getInfo());
                    }

                }
            });
            //    /----

        }
        else{
            infodisplay.setText(itemwithid.getInfo());
        }



    }
    public void setNotification(Calendar c){
        /*SimpleDateFormat formatToCompare = new SimpleDateFormat(
                "MM-dd-yyyy hh:mm");
        Date dateNotification = null;

        try {
            dateNotification = formatToCompare
                    .parse(dateTimeStr);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/


        Intent intent = null;
        intent=new Intent(getApplicationContext(),TimeAlarm.class);
        /*intent.putExtra("NOTIFICATION", "Time to complete your list" + name);
        intent.putExtra("ID", pos);
        intent.putExtra("LONG", dateNotification.getTime());*/

        PendingIntent sender = PendingIntent.getBroadcast(
                getApplicationContext(), 001,
                intent, 0);
        AlarmManager am = null;
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                sender);
    }

}