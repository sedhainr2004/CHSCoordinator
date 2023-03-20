package com.example.chs_coordinator2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ECsAdapter extends RecyclerView.Adapter<ECsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Extracurricular> ecs;
    private Context ctx;

    public ECsAdapter(Context ctx, List<Extracurricular> ecs)
    {
        this.ctx = ctx;
        this.ecs = ecs;
        this.inflater = LayoutInflater.from(ctx);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ecs_layout,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //binding the data
        holder.title.setText(ecs.get(position).getTitle());
        holder.sponsor.setText(ecs.get(position).getSponsor());
        holder.email.setText(ecs.get(position).getEmail());
        if(ecs.get(position).getTitle().equals("Chess Club"))
            holder.coverImage.setImageResource(R.drawable.chesslogo2);

        else
        {
            Picasso.get().load(ecs.get(position).getImageURL()).into(holder.coverImage);
        }

        holder.twitterImage.setVisibility(View.INVISIBLE);
        holder.igImage.setVisibility(View.INVISIBLE);

        if(!ecs.get(position).getTwitterURL().equals("n/a"))
        {
            holder.twitterImage.setVisibility(View.VISIBLE);
            holder.twitterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(ecs.get(holder.getAdapterPosition()).getTwitterURL())); //have to use .getAdapterPosition because position is static
                    ctx.startActivity(i); //have to use context because class has no activity attached to it
                }
            });
        }

        if(!ecs.get(position).getIgURl().equals("n/a"))
        {
            holder.igImage.setVisibility(View.VISIBLE);
            holder.igImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(ecs.get(holder.getAdapterPosition()).getIgURl()));
                    ctx.startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return ecs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, sponsor, email;
        ImageView coverImage, twitterImage, igImage;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.txtECsTitle);
            sponsor = itemView.findViewById(R.id.txtSponsor);
            email = itemView.findViewById(R.id.txtSponsorEmail);
            coverImage = itemView.findViewById(R.id.imgViewECslogo);
            twitterImage = itemView.findViewById(R.id.imgViewTwitterECBtn);
            igImage = itemView.findViewById(R.id.imgViewIGBtn);
        }
    }






}
