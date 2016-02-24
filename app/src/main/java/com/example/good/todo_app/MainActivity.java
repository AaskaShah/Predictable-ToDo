package com.example.good.todo_app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.good.todo_app.database.TablelistDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {

    //final  List<Listitem> detaillist =new ArrayList<Listitem>();
    List<Listitem> values =new ArrayList<Listitem>();
    private TablelistDataSource datasource;
    //final List<ToDoThing> TheList = new ArrayList<ToDoThing>();
    ListView listview;
    Spinner Mainspinner, Subspinner;
    TextView MainCat, SubCat;
    EditText adddetails;
    TextView priorenter,addhead;
    String[] SubCatOptions = {" ", "hey"};
    String MainTxt, SubTxt,name;
    int viewId_Delete = 0;
    int itemCount=0;
    TextView dateenter;
    EditText date;
    private int mYear,mMonth,mDay;


    int flagdelete=0;

    final String[] MainCatOptions = {" ","Appointments","Chores and Errands","Back to books","Shopping","Time for some fun!","Anything else?"};
    final String[] SubShop = {" ","Groceries","Medicines","Clothes and Accessories","Gift"};
    final String[] SubBook = {" ","Tests","Assignments","Library","Buy books"};
    final String[] SubAppoint = {" ","Doctor","Meeting","Get-together"};
    final String[] SubChores = {" ","Pay bills","Laundry","Cleaning","Run to the Gas Station"};
    final String[] SubFun = {" ","Travel","Sight-seeing","Restaurants","Amusement Parks"};
    final String[] SubElse = {" ","Hi","Hello"};

    RadioGroup radiopriority;
    RadioButton priorityset;

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabs=(TabHost)findViewById(R.id.tabHost);
        tabs.setup();
//tab lists
        TabHost.TabSpec tabspec=tabs.newTabSpec("Lists");
        tabspec.setContent(R.id.lists);
        tabspec.setIndicator("Lists");
        tabs.addTab(tabspec);
//tab add
        tabspec=tabs.newTabSpec("Add");
        tabspec.setContent(R.id.add);
        tabspec.setIndicator("Make list");
        tabs.addTab(tabspec);

        listview=(ListView)findViewById(R.id.listView);


        //stuff in add..
        addhead=(TextView)findViewById(R.id.addhead);
        priorenter=(TextView)findViewById(R.id.prior);
        MainCat = (TextView)findViewById(R.id.textView);
        SubCat = (TextView)findViewById(R.id.textView2);
        Mainspinner = (Spinner)findViewById(R.id.Mainspinner);
        Subspinner = (Spinner)findViewById(R.id.Subspinner);
        adddetails=(EditText)findViewById(R.id.adddetails);
        Button btn=(Button)findViewById(R.id.addbutton);
//-----date-times
        dateenter=(TextView)findViewById(R.id.enterdate);

        date=(EditText)findViewById(R.id.date);


//add database to list

        datasource = new TablelistDataSource(this);
        datasource.open();
        values = datasource.getAllItems();

        if(values.size()==0)
            itemCount=0;
        else {
            int max=0;
            for (Listitem l:values
                    ) {
                if(max<l.getId())
                    max=l.getId();
            }
            itemCount = max;
        }

        ArrayAdapter<Listitem> adapter = new ToDoListAdapter(values);
        listview.setAdapter(adapter);


//Spinners
        ArrayAdapter<String> MspinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MainCatOptions);
        Mainspinner.setAdapter(MspinnerAdapter);
        Mainspinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        MainTxt = MainCatOptions[position];
                        switch (position){
                            case 1: {SubCatOptions = SubAppoint;break;}
                            case 2: {SubCatOptions = SubChores;break;}
                            case 3: {SubCatOptions = SubBook;break;}
                            case 4: {SubCatOptions = SubShop;break;}
                            case 5: {SubCatOptions = SubFun;break;}
                            default: SubCatOptions = SubElse;
                        }

                        ArrayAdapter<String> SspinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,SubCatOptions);
                        Subspinner.setAdapter(SspinnerAdapter);
                        Subspinner.setEnabled(position != 0);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        ArrayAdapter<String> SspinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,SubCatOptions);
        Subspinner.setAdapter(SspinnerAdapter);
        Subspinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        SubTxt = SubCatOptions[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
//-------


//--
        radiopriority=(RadioGroup)findViewById(R.id.radiogroup);//add setonCheckedChangeListner here if priority not changing..

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


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adddetails.toString().length() == 0){
                    adddetails.setError("Player name is required!");//not working
                }
                else {
                    adddetails.setError(null);

                    int selectedid = radiopriority.getCheckedRadioButtonId();
                    priorityset = (RadioButton) findViewById(selectedid);
                    RadioButton high = (RadioButton) findViewById(R.id.radiohigh);
                    RadioButton low = (RadioButton) findViewById(R.id.radiolow);
                    if (itemCount == 0 || selectedid == low.getId()) {   //when priority low,add to last.
                        name = MainTxt + " : " + SubTxt;
                        itemCount++;
                        Listitem x = new Listitem(name, adddetails.getText().toString(), date.getText().toString(),"", itemCount);
                        values.add(x);
                        //added in database
                        datasource.insertlist(name, adddetails.getText().toString(), date.getText().toString(), "", itemCount);

                    } else {       //when priority high,add to first place.

                        //database done for high priority
                        name = MainTxt + " : " + SubTxt;
                        itemCount++;
                        Listitem x = new Listitem(name, adddetails.getText().toString(), date.getText().toString(), "", itemCount);

                        for (Listitem l:values
                             ) {
                            datasource.deletelist(l);
                        }
                        values.add(0, x);
                        for (Listitem l:values
                                ) {
                            datasource.insertlist(l.getName(),l.getDetails(),l.getDate(),l.getInfo(),l.getId());
                        }

                    }
                    populatelist();
                    i++;
                    Toast.makeText(getApplicationContext(), "You've got something to do!", Toast.LENGTH_SHORT).show();
                    adddetails.setText("");
                    date.setText("");

                }

            }
        });


    }


    public void populatelist(){

        ArrayAdapter<Listitem> adapter = new ToDoListAdapter(values);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    final Context contxt=this;
    private class ToDoListAdapter extends ArrayAdapter<Listitem> {
        List<Listitem> thislist;
        public ToDoListAdapter(List<Listitem> list) {
            super(MainActivity.this, R.layout.listview_item, list);
            thislist=list;
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            final Listitem current = thislist.get(position);

            TextView name = (TextView) view.findViewById(R.id.textView);
            name.setText(current.getName());

            TextView Des = (TextView) view.findViewById(R.id.textView2);
            Des.setText(current.getDetails());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,Listitemseen.class);
                    //try-(com.example.lenovo.finalapp.Listitemseen)

                    intent.putExtra("n", current.getName().toString());
                    intent.putExtra("det", current.getDetails().toString());
                    intent.putExtra("date", current.getDate().toString());
                    intent.putExtra("id", current.getId());

                    startActivity(intent);
                }


            });
            //registerForContextMenu(view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                    popupMenu.inflate(R.menu.menu_main);
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.Delete) {
                                //delete dialog
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(contxt);
                                builder1.setMessage("Confirm delete?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Delete",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                thislist.remove(current);
                                                datasource.deletelist(current);
                                                ArrayAdapter<Listitem> adapter = new ToDoListAdapter(thislist);
                                                listview.setAdapter(adapter);
                                                dialog.cancel();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }

                            else if (item.getItemId() == R.id.Edit) {
                                Toast.makeText(getApplicationContext(), "Editting will be added later",Toast.LENGTH_LONG).show();
                                
                            }

                            return false;
                        }
                    });

                    return true;
                }
            });

            return view;
        }
    }
/*
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
    }*/
}
