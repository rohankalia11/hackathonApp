package com.example.newhackathonproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newhackathonproject.Model.UserRequestDoneModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserRequestDoneAdapter extends RecyclerView.Adapter<UserRequestDoneAdapter.ViewHolder> {

    List<UserRequestDoneModel> userRequestDoneModelList;

    public UserRequestDoneAdapter(List<UserRequestDoneModel> userRequestDoneModelList) {
        this.userRequestDoneModelList = userRequestDoneModelList;
    }


    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    FirebaseUser firebaseUser;
    String mail,name;

    @NonNull
    @Override
    public UserRequestDoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_request_done_item_layout,parent,false);

        firebaseAuth= FirebaseAuth.getInstance();

        firebaseUser= firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("ActiveRequests");

        databaseReference2= FirebaseDatabase.getInstance().getReference("Accounts");

        databaseReference2.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("Username").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRequestDoneAdapter.ViewHolder holder, int position) {

        String address = userRequestDoneModelList.get(position).getAddress();
        String number = userRequestDoneModelList.get(position).getContactNo();
        String email = userRequestDoneModelList.get(position).getUserMail();
        String name = userRequestDoneModelList.get(position).getUsername();
        String count = userRequestDoneModelList.get(position).getPeopleCount();
        long date = userRequestDoneModelList.get(position).getTimestamp();
        String acceptedby = userRequestDoneModelList.get(position).getAcceptedBy();

        Date datetime = new Date(date);
        String datepattern = "MMM dd, yyyy EEE h:mm a";
        String datetext = new SimpleDateFormat(datepattern).format(datetime);

        holder.setData(address,number,email,name,count,datetext,acceptedby);

    }

    @Override
    public int getItemCount() {
        return userRequestDoneModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Address,ContactNo,UserMail,username,PeopleCount,timestamp,acceptedBy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address =itemView.findViewById(R.id.textAddress);
            ContactNo =itemView.findViewById(R.id.textContactno);
            UserMail =itemView.findViewById(R.id.textEmail);
            username =itemView.findViewById(R.id.textName);
            PeopleCount =itemView.findViewById(R.id.textPeopleCount);
            timestamp =itemView.findViewById(R.id.textDate);
            acceptedBy=itemView.findViewById(R.id.accepted_By);


        }

        public void setData(String add,String number ,String email,String name,String count,String date,String acceptedby )
        {


            Address.setText(add);
            ContactNo.setText(number);
            UserMail.setText(email);
            username.setText(name);
            PeopleCount.setText(count);
            timestamp.setText(date);
            acceptedBy.setText(acceptedby);
        }
    }
}
