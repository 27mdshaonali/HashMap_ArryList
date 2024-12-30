package com.example.hashmaparrylist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String, String> hashMap = new HashMap<>();
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    ListView listView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FloatingActionButton addCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        hashMap1();

        listView = findViewById(R.id.listView);
        addCon = findViewById(R.id.addCon);
        preferences = getSharedPreferences("Contact", MODE_PRIVATE);
        editor = preferences.edit();

        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);

        addCon.setOnClickListener(View -> {

            //I want that I my user click on this button then I want to add new contact. Basically I want that here will open an custom dialog box to get input from user to add new contact.

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_add_contact, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();

            EditText etName = view.findViewById(R.id.etName);
            EditText etContact = view.findViewById(R.id.etContact);
            EditText etMail = view.findViewById(R.id.etMail);
            Button btnAdd = view.findViewById(R.id.btnAdd);

            btnAdd.setOnClickListener(View1 -> {

                String name = etName.getText().toString().trim();
                String contactNumber = etContact.getText().toString().trim();
                String mail = etMail.getText().toString().trim();

                if (name.isEmpty() || contactNumber.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> newContact = new HashMap<>();
                newContact.put("name", name);
                newContact.put("contactNumber", contactNumber);
                newContact.put("mail", mail);

                arrayList.add(newContact);

                // Notify adapter about the change
                MyAdapter adapter = (MyAdapter) listView.getAdapter();
                adapter.notifyDataSetChanged();

                // Close the dialog
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Contact Added!", Toast.LENGTH_SHORT).show();

            });

        });


    }

    //=======================================End onCreate==================================================

    public void hashMap1() {

        hashMap = new HashMap<>();
        hashMap.put("name", "Shaon");
        hashMap.put("contactNumber", "+8801712345678");
        hashMap.put("mail", "example@gmail.com");

        arrayList.add(hashMap);

    }

    public class MyAdapter extends BaseAdapter {

        LayoutInflater inflater;

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.items, parent, false);

            HashMap<String, String> hashMap = arrayList.get(position);

            LinearLayout linearLayout = view.findViewById(R.id.itemLayout);
            ImageView imageView = view.findViewById(R.id.itemImageView);
            TextView userName = view.findViewById(R.id.itemName);
            TextView userContactNumber = view.findViewById(R.id.itemContactNumber);
            TextView mail = view.findViewById(R.id.itemEmail);

            String name = hashMap.get("name");
            String contactNumber = hashMap.get("contactNumber");
            String mail1 = hashMap.get("mail");

            userName.setText(name);
            userContactNumber.setText(contactNumber);
            mail.setText(mail1);


            userContactNumber.setOnClickListener(View -> {


                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(android.net.Uri.parse("tel:" + contactNumber));
                startActivity(callIntent);

                if (callIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(callIntent);

                } else {
                    Toast.makeText(MainActivity.this, "No application to handle calls", Toast.LENGTH_SHORT).show();
                }


                mail.setOnClickListener(v -> {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(android.net.Uri.parse("mailto:" + mail1));
                    startActivity(emailIntent);

                    if (emailIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(emailIntent);
                    } else {
                        Toast.makeText(MainActivity.this, "No application to handle emails", Toast.LENGTH_SHORT).show();
                    }


                });


                Toast.makeText(MainActivity.this, "Clicked" + position, Toast.LENGTH_SHORT).show();

            });

            return view;
        }
    }


}