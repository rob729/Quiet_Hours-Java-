package com.example.robin.quiethours;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.abdularis.civ.AvatarImageView;

import java.util.ArrayList;

import androidx.work.WorkManager;

public class Profile_Adapter  extends RecyclerView.Adapter<Profile_Adapter.ViewHolder>  {

    private Context ctx;
    private ArrayList<Profile> profileArrayList;

    public Profile_Adapter(Context ctx, ArrayList<Profile> profileArrayList) {
        this.ctx = ctx;
        this.profileArrayList = profileArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View inflatedView = li.inflate(R.layout.item_row,parent,false);
        ViewHolder vh= new ViewHolder(inflatedView);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile p = profileArrayList.get(position);
        holder.name.setText(p.getName());
        holder.a.setText(p.getName().charAt(0) + "");
    }

    @Override
    public int getItemCount() {
        return profileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        AvatarImageView a;
        public ViewHolder(View inflatedView) {
            super(inflatedView);
            name = inflatedView.findViewById(R.id.ProfileName);
            a = inflatedView.findViewById(R.id.TxtImg);

            inflatedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int j = getAdapterPosition();
                    Profile profile = profileArrayList.get(j);
                    Intent i = new Intent(ctx,Detail.class);
                    i.putExtra("profile",profile);
                    ctx.startActivity(i);
                }
            });

            inflatedView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int j = getAdapterPosition();
                    Profile profile = profileArrayList.get(j);
                    ProfileApplication.getDb().getProfileDao().deleteProfile(profile);
                    profileArrayList.remove(j);
                    notifyDataSetChanged();
                    return true;
                }
            });
        }
    }
}
