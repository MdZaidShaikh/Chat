package com.MohammadZaidShaikh.chat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MohammadZaidShaikh.chat.R;
import com.bumptech.glide.Glide;
import com.MohammadZaidShaikh.chat.Model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_RECEIVER = 0;
    public static final int MSG_TYPE_SENDER = 1;

    private Context context;
    private List<Chat> chats;
    private String imageURL;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chats, String imageURL) {
        this.context = context;
        this.chats = chats;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_SENDER){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_message, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_message, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chat chat = chats.get(position);

        holder.message.setText(chat.getMessage());

        if (imageURL.equals("default")){
            holder.profileImage.setImageResource(R.drawable.ic_profile);
        }
        else {
            Glide.with(context).load(imageURL).into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView message;
        public ImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (chats.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_SENDER;
        }
        else {
            return MSG_TYPE_RECEIVER;
        }
    }
}
