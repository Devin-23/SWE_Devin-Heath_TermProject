package com.example.swe_termproj_v9f;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private ContactsAdapter adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.contactRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ContactsAdapter(contactList, this);
        recyclerView.setAdapter(adapter);

        Button addContactButton = findViewById(R.id.addContactButton);
        addContactButton.setOnClickListener(v -> showAddContactDialog());
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Contact");

            //creating the layout that will accept the users inputs
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameInput = new EditText(this);
        nameInput.setHint("Name");
        layout.addView(nameInput);

        final EditText phoneInput = new EditText(this);
        phoneInput.setHint("Phone #");
        phoneInput.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
        layout.addView(phoneInput);

        final EditText emailInput = new EditText(this);
        emailInput.setHint("Email");
        emailInput.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(emailInput);

            //it took an embarrassing amount of time to figure out why it wouldn't let you type when the popup came up...
            //...forgot to add the "setInputType" :^/

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String email = emailInput.getText().toString();

            if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                Contact contact = new Contact(name, phone, email);
                adapter.addContact(contact);
            } else {
                    //give a alert message that there is an error if anything is left empty...
                    //because if anything is left empty the app just decides to give up...
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("All fields must be filled out!")
                        .setPositiveButton("OK", null)
                        .create();
                alertDialog.show();
            }
        });

            //cancel button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
