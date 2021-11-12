package com.shashank.platform.loginui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

public class MainActivity_chat extends AppCompatActivity {
    private RecyclerView recyclerView; // lv_chating
    private RecyclerView.Adapter adapter;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arr_room = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private List<Chat> chatList;

    private DatabaseReference myRef;
    private EditText chatText;
    private Button sendButton;

    private String str_room_name;
    private String str_user_name;
    private String key;
    private String chat_user;
    private String chat_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        chatText = (EditText) findViewById(R.id.chatText);
        sendButton = (Button) findViewById(R.id.sendButton);
        str_room_name = getIntent().getExtras().get("room_name").toString();
        str_user_name = getIntent().getExtras().get("user_name").toString();
        myRef = FirebaseDatabase.getInstance().getReference().child(str_room_name);
        setTitle(str_room_name);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력창에 메시지를 입력 후 버튼클릭했을 때
                String msg = chatText.getText().toString();
                Date today = new Date();
                if (msg != null) {
                    Chat chat = new Chat();
                    chat.setName(str_user_name);
                    chat.setMsg(msg);

                    //메시지를 파이어베이스에 보냄.
                    myRef.push().setValue(chat);

                    chatText.setText("");
                }

            }
        });

        /*
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<String, Object>();
                key = myRef.push().getKey();
                myRef.updateChildren(map);

                DatabaseReference root = myRef.child(key);
                Map<String, Object> objectMap = new HashMap<String, Object>();
                objectMap.put("name", str_user_name);
                objectMap.put("message", sendButton.getText().toString());
                root.updateChildren(objectMap);

            }
        });
        */

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chatList = new ArrayList<>();
        adapter = new ChatAdapter(chatList, str_user_name);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference("message");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Chat chat = snapshot.getValue(Chat.class);
                ((ChatAdapter) adapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}


        /*

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chatConversation(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chatConversation(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void chatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user = (String) ((DataSnapshot) i.next()).getValue();
            arrayAdapter.add(chat_user + " :" + chat_msg);
        }
        arrayAdapter.notifyDataSetChanged();
    }
        */

