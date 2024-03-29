package Fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koeksworld.homenet.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.MessagesAdapter;
import HomeNETStream.NewMessageFragment;
import Models.HouseAnnouncement;
import Tasks.GetHouseMessagesTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseMessagesFragment extends Fragment implements View.OnClickListener {

    private RecyclerView messagesRecyclerView;
    private GetHouseMessagesTask task;
    private FloatingActionButton refreshButton;
    private FloatingActionButton newPostButton;
    private TextView toolbarTextView;

    public HouseMessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cureentView = inflater.inflate(R.layout.fragment_house_messages, container, false);
        initializeComponents(cureentView);
        getData();
        return cureentView;
    }

    private void initializeComponents(View currentView) {
        messagesRecyclerView = (RecyclerView) currentView.findViewById(R.id.MessagesRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshButton = (FloatingActionButton) currentView.findViewById(R.id.MessagesActionButton);
        newPostButton = (FloatingActionButton) currentView.findViewById(R.id.NewMessageButton);
        toolbarTextView = (TextView) getActivity().findViewById(R.id.HomeManagerActivityToolbarTextView);
        toolbarTextView.setText("Messages");
        newPostButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
    }

    private void getData() {
        task = new GetHouseMessagesTask(getActivity(), messagesRecyclerView);
        task.execute();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.MessagesActionButton:
                task = new GetHouseMessagesTask(getActivity(), messagesRecyclerView);
                task.execute();
                break;
            case R.id.NewMessageButton:
                NewMessageFragment fragment = new NewMessageFragment();
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.HomeNetFeedContentView, fragment, null);
                transaction.addToBackStack(null);
                transaction.commit();

                break;
        }
    }
}
