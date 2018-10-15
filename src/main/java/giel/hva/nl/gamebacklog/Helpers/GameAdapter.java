package giel.hva.nl.gamebacklog.Helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import giel.hva.nl.gamebacklog.AddGameActivity;
import giel.hva.nl.gamebacklog.EditGameActivity;
import giel.hva.nl.gamebacklog.MainActivity;
import giel.hva.nl.gamebacklog.Models.GameObject;
import giel.hva.nl.gamebacklog.R;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private Context mContext;
    private List<GameObject> gameList;
    private JSONHelper jsonHelper;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, platform, status;
        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.gameTitleText);
            date =  view.findViewById(R.id.dateText);
            status =  view.findViewById(R.id.statusText);
            platform = view.findViewById(R.id.platformText);
        }
    }


    public GameAdapter(Context mContext) throws IOException {
        this.mContext = mContext;
        jsonHelper = new JSONHelper(mContext);
        gameList = jsonHelper.ReadJson();
    }

    public void updateGameList() throws IOException {
        gameList.clear();
        gameList.addAll(jsonHelper.ReadJson());
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        final GameObject game = gameList.get(position);
        holder.title.setText(game.getTitle());
        holder.date.setText(df.format(game.getDate()));
        holder.platform.setText(game.getPlatform());
        holder.status.setText(game.getGameStatus().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditGameActivity.class);
                Gson gson = new Gson();
                intent.putExtra("game", gson.toJson(game));
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}