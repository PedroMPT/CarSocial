package pt.ismai.pedro.needarideapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import pt.ismai.pedro.needarideapp.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    // Definir as Variáveis do conteúdo
    private ArrayList<Bitmap> avatar;
    private ArrayList<String> avatarName;
    private ArrayList<String> price;
    private ArrayList<String> time;
    private ArrayList<String> rideDate;
    private ArrayList<String> rideFrom;
    private ArrayList<String> rideTo;
    private LayoutInflater inflater;

    // Criar o construtor


    public RecyclerViewAdapter(Context context, ArrayList<Bitmap> avatar, ArrayList<String> avatarName, ArrayList<String> price, ArrayList<String> time, ArrayList<String> rideDate, ArrayList<String> rideFrom, ArrayList<String> rideTo) {
        inflater = LayoutInflater.from(context);
        this.avatar = avatar;
        this.avatarName = avatarName;
        this.price = price;
        this.time = time;
        this.rideDate = rideDate;
        this.rideFrom = rideFrom;
        this.rideTo = rideTo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Inflate the view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_folding,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.title_price.setText(price.get(i));
        viewHolder.title_date.setText(rideDate.get(i));
        viewHolder.title_time.setText(time.get(i));
        viewHolder.title_from_address.setText(rideFrom.get(i));
        viewHolder.title_to_address.setText(rideTo.get(i));
        viewHolder.avatar_name.setText(avatarName.get(i));
        viewHolder.avatar.setImageBitmap(avatar.get(i));
    }

    @Override
    public int getItemCount() {
        return price.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearTitleLayout;
        RelativeLayout leftSideRelativeLayout,rightTitleRelativeLayout;
        TextView title_price,title_time,title_date,avatar_name,title_from_address,title_to_address;
        CircleImageView avatar;


        public ViewHolder(@NonNull View itemView) {

           super(itemView);

           linearTitleLayout = itemView.findViewById(R.id.linearTitleLayout);
           leftSideRelativeLayout = itemView.findViewById(R.id.leftSideRelativeLayout);
           rightTitleRelativeLayout = itemView.findViewById(R.id.rightTitleRelativeLayout);
           title_price = itemView.findViewById(R.id.title_price);
           title_time = itemView.findViewById(R.id.title_time_label);
           title_date = itemView.findViewById(R.id.title_date_label);
           avatar_name = itemView.findViewById(R.id.avatar_name);
           title_from_address = itemView.findViewById(R.id.title_from_address);
           title_to_address = itemView.findViewById(R.id.title_to_address);
           avatar = itemView.findViewById(R.id.avatar);


        }
    }

}
