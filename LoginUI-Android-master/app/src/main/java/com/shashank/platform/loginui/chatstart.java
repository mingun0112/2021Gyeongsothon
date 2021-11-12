package com.shashank.platform.loginui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;
public class chatstart extends AppCompatActivity {


    private ListView chat_list;
    private Button btn_create;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference reference = databaseReference.getRoot();
    private String name;
    private ArrayList<String> arr_roomList = new ArrayList<>();
    private String str_name;
    private String str_room;

    Map<String,Object> map = new HashMap<String, Object>();
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("chat list");
        setContentView(R.layout.activity_chatstart);

        Intent intent = getIntent();
        str_name = intent.getStringExtra("name");

        chat_list = (ListView) findViewById(R.id.chat_list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr_roomList);
        chat_list.setAdapter(arrayAdapter);
        btn_create = (Button) findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et_inD = new EditText(chatstart.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(chatstart.this);
                builder.setTitle("목격자 입력");
                builder.setView(et_inD);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        str_room = et_inD.getText().toString();
                        map.put(str_room,"");
                        reference.updateChildren(map);
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()){
                    set.add(((DataSnapshot) i.next()).getKey());
                }
                arr_roomList.clear();
                arr_roomList.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),MainActivity_chat.class);
                intent.putExtra("room_name", ((TextView) view).getText().toString());
                intent.putExtra("user_name",str_name);
                startActivity(intent);
            }
        });

    }

}