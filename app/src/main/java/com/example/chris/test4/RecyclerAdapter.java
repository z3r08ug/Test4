package com.example.chris.test4;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chris.test4.model.Item;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Admin on 11/27/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    List<Item> items;
    Context context;
    android.support.v4.app.DialogFragment fragment;
    FragmentTransaction ft;

    public RecyclerAdapter(List<Item> items, FragmentTransaction fragmentTransaction)
    {
        this.items = items;
        ft = fragmentTransaction;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_list_item, null);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position)
    {
        Item item = items.get(position);
        if(item != null)
        {
            holder.tvPicTitle.setText("Title:\n"+item.getTitle());
            holder.tvPicAuthor.setText("Author:\n"+item.getAuthor());
            Glide.with(context).load(item.getMedia().getM()).into(holder.ivPic);
        }
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvPicTitle;
        private final TextView tvPicAuthor;
        private final ImageView ivPic;
        public ViewHolder(final View itemView)
        {
            super(itemView);
            tvPicTitle = itemView.findViewById(R.id.tvPicTitle);
            tvPicAuthor = itemView.findViewById(R.id.tvPicAuthor);
            ivPic = itemView.findViewById(R.id.ivPic);
            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(final View v)
                {
                    final String picture = items.get(getAdapterPosition()).getMedia().getM();
    
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Image Display Options");
                    builder.setItems(R.array.choices, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            switch (which)
                            {
                                case 0:
                                {
                                    Intent intent = new Intent(v.getContext(), FullImageActivity.class);
                                    intent.putExtra("pic", picture);
                                    v.getContext().startActivity(intent);
                                    break;
                                }
                                case 1:
                                {
//                                    fragment = MyDialogFragment.newInstance(1, picture);
//                                    fragment.show(ft, "dialog");
                                    
                                    ImageView imageView = new ImageView(itemView.getContext());
                                    new PicAsync(imageView).execute(picture);
    
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(itemView.getContext())
                                                    .setView(imageView);
                                    builder.create().show();
                                    break;
                                }
                            }
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });
        }
    }
}
