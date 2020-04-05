package com.example.newhackathonproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newhackathonproject.Model.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    public RequestAdapter(List<RequestModel> requestModelList) {
        this.requestModelList = requestModelList;
    }

    List<RequestModel> requestModelList;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_list_item_layout,parent,false);

        firebaseAuth= FirebaseAuth.getInstance();

        firebaseUser= firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Requests");

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String address = requestModelList.get(position).getAddress();
        String number = requestModelList.get(position).getContactNo();
        String email = requestModelList.get(position).getUserMail();
        String name = requestModelList.get(position).getUsername();
        String count = requestModelList.get(position).getPeopleCount();
        long date = requestModelList.get(position).getTimestamp();

        Date datetime = new Date(date);
        String datepattern = "MMM dd, yyyy EEE h:mm a";
        String datetext = new SimpleDateFormat(datepattern).format(datetime);

        holder.setData(address,number,email,name,count,datetext);



    }

    @Override
    public int getItemCount() {
        return requestModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address,ContactNo,UserMail,username,PeopleCount,timestamp;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            Address =itemView.findViewById(R.id.textAddress);
            ContactNo =itemView.findViewById(R.id.textContactno);
            UserMail =itemView.findViewById(R.id.textEmail);
            username =itemView.findViewById(R.id.textName);
            PeopleCount =itemView.findViewById(R.id.textPeopleCount);
            timestamp =itemView.findViewById(R.id.textDate);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            try {


                                if (dataSnapshot.exists()) {

                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                                mail =  snapshot.child("UserMail").getValue().toString();

                                        if (UserMail.getText().toString().equals(mail))
                                        {
                                            //Toast.makeText(itemView.getContext(), "item clicked "+mail+" "+UserMail.getText().toString(), Toast.LENGTH_SHORT).show();

                                            databaseReference1= FirebaseDatabase.getInstance().getReference("ActiveRequests");


                                            String address =   snapshot.child("Address").getValue().toString();
                                            String cno=      snapshot.child("ContactNo").getValue().toString();

                                            String username =     snapshot.child("username").getValue().toString();
                                            String count =   snapshot.child("PeopleCount").getValue().toString();
                                            long time =   (long) snapshot.child("timestamp").getValue();


                                            HashMap<String, String> hashMap = new HashMap<>();

                                            hashMap.put("PeopleCount", count);
                                            hashMap.put("ContactNo", cno);
                                            hashMap.put("Address", address);
                                            hashMap.put("UserMail", mail);
                                            hashMap.put("username", username);
                                            hashMap.put("AcceptedBy",name);
                                            //  hashMap.put("timestamp", ServerValue.TIMESTAMP.toString());


                                            databaseReference1.child(snapshot.getKey()).setValue(hashMap);

                                            databaseReference1.child(snapshot.getKey()).child("timestamp").setValue(time);

                                            Toast.makeText(itemView.getContext(), "request confirmed", Toast.LENGTH_SHORT).show();

                                           databaseReference.child(snapshot.getKey()).removeValue();

                                           RequestAdapter requestAdapter = new RequestAdapter(requestModelList);
                                           requestAdapter.notifyDataSetChanged();

                                          // ShowListActivity showListActivity=new ShowListActivity();
                                           //showListActivity.finish();
                                          // itemView.getContext().startActivity(new Intent(itemView.getContext(),NgoMainActivity.class));


                                        }



                                    }



                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(itemView.getContext(), e.getMessage()+"Added "+mail+" "+UserMail.getText().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });





                }
            });


        }


        public void setData(String add,String number ,String email,String name,String count,String date )
        {


            Address.setText(add);
            ContactNo.setText(number);
            UserMail.setText(email);
            username.setText(name);
            PeopleCount.setText(count);
            timestamp.setText(date);
        }
    }
}
