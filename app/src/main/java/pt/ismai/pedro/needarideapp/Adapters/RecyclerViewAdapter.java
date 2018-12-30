package pt.ismai.pedro.needarideapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import pt.ismai.pedro.needarideapp.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    // Definir as Variáveis do conteúdo
    private ArrayList<String> images;
    private Context context;

    // Criar o construtor


    public RecyclerViewAdapter(Context context, ArrayList<String> images) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Inflate the view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_folding,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        RelativeLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {

           super(itemView);

            //parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
