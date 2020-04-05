package com.example.newhackathonproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newhackathonproject.Model.ActiveRequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ActiveRequestAdapter extends RecyclerView.Adapter<ActiveRequestAdapter.ViewHolder> {



    List<ActiveRequestModel> activeRequestModelList;


    public ActiveRequestAdapter(List<ActiveRequestModel> activeRequestModelList) {
        this.activeRequestModelList = activeRequestModelList;
    }


    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2,databaseReference4;
    FirebaseUser firebaseUser;
    String mail,name;

    @NonNull
    @Override
    public ActiveRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ngo_active_request_item_layout,parent,false);

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
    public void onBindViewHolder(@NonNull ActiveRequestAdapter.ViewHolder holder, int position) {

        String address = activeRequestModelList.get(position).getAddress();
        String number = activeRequestModelList.get(position).getContactNo();
        String email = activeRequestModelList.get(position).getUserMail();
        String name = activeRequestModelList.get(position).getUsername();
        String count = activeRequestModelList.get(position).getPeopleCount();
        long date = activeRequestModelList.get(position).getTimestamp();
        String acceptedby = activeRequestModelList.get(position).getAcceptedBy();

        Date datetime = new Date(date);
        String datepattern = "MMM dd, yyyy EEE h:mm a";
        String datetext = new SimpleDateFormat(datepattern).format(datetime);

        holder.setData(address,number,email,name,count,datetext,acceptedby);

    }

    @Override
    public int getItemCount() {
        return activeRequestModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Address,ContactNo,UserMail,username,PeopleCount,timestamp,acceptedBy;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            Address =itemView.findViewById(R.id.textAddress);
            ContactNo =itemView.findViewById(R.id.textContactno);
            UserMail =itemView.findViewById(R.id.textEmail);
            username =itemView.findViewById(R.id.textName);
            PeopleCount =itemView.findViewById(R.id.textPeopleCount);
            timestamp =itemView.findViewById(R.id.textDate);
            acceptedBy=itemView.findViewById(R.id.accepted_By);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


               /*     databaseReference.addValueEventListener(new ValueEventListener() {
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

                                           // RequestAdapter requestAdapter = new RequestAdapter(activeRequestModelList);
                                            //requestAdapter.notifyDataSetChanged();

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
                    }); */

               databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                       if (dataSnapshot.exists()) {


                           for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                               databaseReference4= FirebaseDatabase.getInstance().getReference("RequestCompleted");


                               String address =   snapshot.child("Address").getValue().toString();
                               String cno=      snapshot.child("ContactNo").getValue().toString();

                               mail =  snapshot.child("UserMail").getValue().toString();
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


                               databaseReference4.child(snapshot.getKey()).setValue(hashMap);

                               databaseReference4.child(snapshot.getKey()).child("timestamp").setValue(time);




                               databaseReference.child(snapshot.getKey()).removeValue();
                               Toast.makeText(itemView.getContext(), "Delivered", Toast.LENGTH_SHORT).show();
                           }

                       }



                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });



                }
            });
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


