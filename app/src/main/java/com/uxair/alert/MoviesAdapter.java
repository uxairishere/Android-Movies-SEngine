package com.uxair.alert;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movies_Data> arrayList;
    public MoviesAdapter(Context context, ArrayList<Movies_Data> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.movies_list, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {
        Movies_Data movies_data = arrayList.get(position);
        holder.title.setText(movies_data.getTitle());
        holder.type.setText(movies_data.getType());
        holder.year.setText(movies_data.getYear());
        holder.desc.setText(movies_data.getDesc());
        holder.rating.setText(movies_data.getRating());
        Glide.with(context).load(movies_data.getPoster()).into(holder.poster);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(view.getContext(), Movie_Detail.class);
                in.putExtra("TITLE", movies_data.getTitle());
                in.putExtra("TYPE", movies_data.getType());
                in.putExtra("YEAR", movies_data.getYear());
                in.putExtra("DESC", movies_data.getDesc());
                in.putExtra("RATING", movies_data.getRating());
                in.putExtra("POSTER", movies_data.getPoster());
                in.putExtra("BACKDROP", movies_data.getBackDrop());
                in.putExtra("RATING", movies_data.getRating());
                view.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView poster;
        TextView type;
        TextView year;
        TextView desc;
        TextView rating;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            poster = itemView.findViewById(R.id.movieImageView);
            year = itemView.findViewById(R.id.textViewYear);
            type = itemView.findViewById(R.id.textViewType);
            desc = itemView.findViewById(R.id.textViewDesc);
            rating = itemView.findViewById(R.id.textViewRating);
            constraintLayout = itemView.findViewById(R.id.contraintLayOut);
        }
    }
}
