package com.example.enigma;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enigma.database.AppDatabase;
import com.example.enigma.database.Contact;
import com.example.enigma.database.ContactDao;
import com.example.enigma.databinding.FragmentContactsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ContactsFragment extends Fragment {

    private FragmentContactsBinding binding;
    private AppDatabase databaseInstance;

    private ContactAdapter contactAdapter;

    public ContactsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseInstance = AppDatabase.getInstance(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);

        getContactsFromDatabase();

        return binding.getRoot();
    }

    private void populateRecyclerView(List<ContactItem> items)
    {
        if(contactAdapter == null)
        {
            contactAdapter = new ContactAdapter(requireContext(), items);
        }

        binding.contactsRecyclerView.setHasFixedSize(true);
        binding.contactsRecyclerView.setAdapter(contactAdapter);
    }

    private void getContactsFromDatabase()
    {
        List<ContactItem> contactsList = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper());

        Executors.newSingleThreadExecutor().execute(() -> {
            ContactDao contactDao = databaseInstance.contactDao();
            final List<Contact> contacts = contactDao.getAll();

            for(Contact contact : contacts)
            {
                contactsList.add(new ContactItem(contact.getAddress(), contact.getNickName()));
            }

            handler.post(() -> {
                populateRecyclerView(contactsList);
            });
        });
    }
}