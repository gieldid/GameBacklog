package giel.hva.nl.gamebacklog;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.JsonReader;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import giel.hva.nl.gamebacklog.Helpers.GameAdapter;
import giel.hva.nl.gamebacklog.Helpers.JSONHelper;
import giel.hva.nl.gamebacklog.Models.GameObject;

public class MainActivity extends AppCompatActivity {
    private GameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addGameButton =  findViewById(R.id.addGameButton);
        addGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        try {
            adapter = new GameAdapter(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallBack  =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = (viewHolder.getAdapterPosition());
                        JSONHelper jsonHelper = new JSONHelper(MainActivity.this);

                        try {
                            List<GameObject> gameObjects = jsonHelper.ReadJson();
                            gameObjects.remove(position);
                            jsonHelper.WriteToJson(gameObjects);
                            adapter.updateGameList();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            adapter.updateGameList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
