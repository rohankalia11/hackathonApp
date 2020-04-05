package com.example.newhackathonproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newhackathonproject.Model.NgoRequestDoneModel;
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

public class NgoRequestDoneAdapter extends RecyclerView.Adapter<NgoRequestDoneAdapter.ViewHolder> {

    List<NgoRequestDoneModel> ngoRequestDoneModelList;

    public NgoRequestDoneAdapter(List<NgoRequestDoneModel> ngoRequestDoneModelList) {
        this.ngoRequestDoneModelList = ngoRequestDoneModelList;
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
    public NgoRequestDoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ngo_request_done_item_layout,parent,false);

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
    public void onBindViewHolder(@NonNull NgoRequestDoneAdapter.ViewHolder holder, int position) {

        String address = ngoRequestDoneModelList.get(position).getAddress();
        String number = ngoRequestDoneModelList.get(position).getContactNo();
        String email = ngoRequestDoneModelList.get(position).getUserMail();
        String name = ngoRequestDoneModelList.get(position).getUsername();
        String count = ngoRequestDoneModelList.get(position).getPeopleCount();
        long date = ngoRequestDoneModelList.get(position).getTimestamp();
        String acceptedby = ngoRequestDoneModelList.get(position).getAcceptedBy();

        Date datetime = new Date(date);
        String datepattern = "MMM dd, yyyy EEE h:mm a";
        String datetext = new SimpleDateFormat(datepattern).format(datetime);

        holder.setData(address,number,email,name,count,datetext,acceptedby);

    }

    @Override
    public int getItemCount() {
        return ngoRequestDoneModelList.size();
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
