package com.example.app_ga_pfe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;


public class SearchAdapter extends FirebaseRecyclerAdapter<UserModel, SearchAdapter.UserModalViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context context ;
    public SearchAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options, Context context) {
        super(options);
        this.context = context;
    }



    @Override
    protected void onBindViewHolder(@NonNull UserModalViewHolder holder, int position, @NonNull UserModel model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getGmail());
        Glide.with(holder.profilImg.getContext()).load(model.getImag()).placeholder(R.drawable.person_icon)
                        .circleCrop()
                                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                                        .into(holder.profilImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nameS", model.getName());
                editor.apply();
                Intent intent = new Intent(context,Profil_Student.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }


    @NonNull
    @Override
    public UserModalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_view , parent , false );
        return  new UserModalViewHolder(view );
    }

    class UserModalViewHolder extends RecyclerView.ViewHolder{
        TextView name ;
        TextView email ;
        CircleImageView profilImg ;
        public UserModalViewHolder(@NonNull View itemView) {
            super(itemView);
            name = ( TextView ) itemView.findViewById(R.id.userNametxt);
            email = (TextView)itemView.findViewById(R.id.emailTxt);
            profilImg =(CircleImageView)itemView.findViewById(R.id.profile_pic_image_view);

        }
    }
}
