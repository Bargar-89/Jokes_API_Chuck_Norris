package com.example.jokesapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class FirstPageFragment extends Fragment{
    private EditText editTextInputCounts;
    private Button buttonReload;
    private RecyclerView recyclerView;
    private ArrayList<OneJoke> jokesList;
    private JokesRVAdapter jokesRVAdapter;
    private BDHandler bdHandler;
    private ConnectHandler connectHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.first_fragment_layout,container,false);
        bdHandler = new BDHandler(getContext());
        connectHandler = new ConnectHandler();
        buttonReload = rootView.findViewById(R.id.firs_fragment__button_reload);
        editTextInputCounts = rootView.findViewById(R.id.firs_fragment__input_counts);
        recyclerView = rootView.findViewById(R.id.first_fragment__recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        jokesList = bdHandler.getAllJokes();
        jokesRVAdapter = new JokesRVAdapter(jokesList);
        recyclerView.setAdapter(jokesRVAdapter);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextInputCounts.getText().toString().length()<=0) {
                    Toast.makeText(getContext(), getActivity().getResources().getString(R.string.error_counts), Toast.LENGTH_SHORT).show();
                }else {
                    bdHandler.deleteAll();
                    jokesList.clear();
                    connectHandler.setCounts(Integer.valueOf(editTextInputCounts.getText().toString()));
                    final Thread getJokesThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jokesList = connectHandler.sendGet();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
//start connection and get jokes
                    if(connectHandler.hasConnection(getActivity())){
                        getJokesThread.start();
                        try {
                            getJokesThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getJokesThread.interrupt();
//update UI and BD
                        if (jokesList.size() > 0) {
                            bdHandler.addJokes(jokesList);
                            jokesRVAdapter.upDate(jokesList);
                        } else {
                            Toast.makeText(getContext(),getActivity().getResources().getString(R.string.error_) , Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getContext(), getActivity().getResources().getString(R.string.Error_connect), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}