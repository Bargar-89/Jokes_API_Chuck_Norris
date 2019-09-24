package com.example.jokesapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class JokesRVAdapter extends RecyclerView.Adapter<JokesRVAdapter.OneJokeHolder> {
    private final ArrayList<OneJoke> jokesList;
    private final int CORRECT_NUMBER = 1;

    public JokesRVAdapter(ArrayList<OneJoke> jokesList) {
        this.jokesList = jokesList;
    }

    @NonNull
    @Override
    public OneJokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_joke_layout, parent, false);
        return new OneJokeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OneJokeHolder holder, int position) {
        OneJoke oneJoke = jokesList.get(position);
        oneJoke.setNumberOfJoke(String.valueOf(position+CORRECT_NUMBER));
        holder.bind(oneJoke);
    }

    @Override
    public int getItemCount() {
        return jokesList.size();
    }

    public void upDate(ArrayList<OneJoke> arrayList){
        jokesList.clear();
        jokesList.addAll(arrayList);
        notifyDataSetChanged();
    }

    class OneJokeHolder extends RecyclerView.ViewHolder {
        private TextView numberOfJoke;
        private TextView textOfJoke;

        public OneJokeHolder(@NonNull View itemView) {
            super(itemView);
            numberOfJoke = itemView.findViewById(R.id.number_of_joke);
            textOfJoke = itemView.findViewById(R.id.text_of_joke);
        }

        public void bind(OneJoke oneJoke) {
            textOfJoke.setText(oneJoke.getTextOfJoke());
            numberOfJoke.setText(oneJoke.getNumberOfJoke());
        }
    }
}
