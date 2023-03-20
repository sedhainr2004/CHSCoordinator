package com.example.chs_coordinator2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.List;


public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder>
{
    private LayoutInflater inflater;
    private List<Sport> sports;
    private Context ctx;

    public SportsAdapter (Context ctx, List<Sport> sports)
    {
        this.ctx = ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.sports = sports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sports_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //binding the data
        holder.title.setText(sports.get(position).getTitle());
        holder.email.setText(sports.get(position).getEmail());
        holder.coach.setText("Coach: " + sports.get(position).getCoach());
        Picasso.get().load(sports.get(position).getImageURL()).into(holder.coverImage);
        holder.twitterImage.setVisibility(View.INVISIBLE);
        holder.maxPrepsImage.setVisibility(View.INVISIBLE);
        if(sports.get(position).getTwitterURL().contains("https"))
        {
            holder.twitterImage.setVisibility(View.VISIBLE);
            holder.twitterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(sports.get(holder.getAdapterPosition()).getTwitterURL()));
                    ctx.startActivity(i);
                }
            });
        }



        if(sports.get(position).getMaxPrepsURL().contains("https"))
        {
            holder.maxPrepsImage.setVisibility(View.VISIBLE);
            holder.maxPrepsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(sports.get(holder.getAdapterPosition()).getMaxPrepsURL()));
                    ctx.startActivity(i);
                }
            });

        }

        //when using onclick methods using .getAdapterPosition is the best option
        //because the position of the data changes


    }



    @Override
    public int getItemCount() {
        return sports.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,coach,email;
        ImageView coverImage, twitterImage, maxPrepsImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txtSportsTitle);
            coach = itemView.findViewById(R.id.txtCoach);
            email = itemView.findViewById(R.id.txtEmail2);
            coverImage = itemView.findViewById(R.id.imgViewSportsLogo);
            twitterImage = itemView.findViewById(R.id.imgViewTwitterBtn);
            maxPrepsImage = itemView.findViewById(R.id.imgViewMaxPrepsBtn);
        }


    }
}
