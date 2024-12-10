package com.example.swe_termproj_v9f;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contact> contacts;
    private Context context;

    public ContactsAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.itemView.setOnClickListener(v -> showContactOptions(contact));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        sortContacts();
        notifyDataSetChanged();
    }

    private void makePhoneCall(Contact contact) {
            //had to make it request permission after realizing that's why it kept breaking
        if (context.checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getPhoneNumber()));
            context.startActivity(callIntent);
        } else {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                activity.requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    private void sendEmail(Contact contact) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
            //this is the standard "setType" according to Google
        emailIntent.setType("message/rfc822");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getEmail()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello - " + contact.getName());
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Sent from 'Institute of the South Pacific' App!");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Please Install an email client.", Toast.LENGTH_SHORT).show();
        }
    }

        //show the options to call or email
    private void showContactOptions(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Contact: " +contact.getName());
        builder.setItems(new String[]{"Call", "Email"}, (dialog, which) -> {
           switch (which) {
               case 0:
                   makePhoneCall(contact);
                   break;
               case 1:
                   sendEmail(contact);
                   break;
           }
        });
        builder.show();
    }

    private void sortContacts() {
        contacts.sort(Comparator.comparing(Contact::getName));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView avatarImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textView1);
            avatarImageView = itemView.findViewById(R.id.imageView1);
        }
    }
}
