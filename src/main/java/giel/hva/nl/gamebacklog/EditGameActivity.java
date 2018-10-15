package giel.hva.nl.gamebacklog;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import giel.hva.nl.gamebacklog.Helpers.JSONHelper;
import giel.hva.nl.gamebacklog.Models.GameObject;

public class EditGameActivity extends AppCompatActivity {
    private TextInputEditText titleInput, platformInput, notesInput;
    private Spinner statusInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);


        titleInput = findViewById(R.id.titleInputEditText);
        platformInput = findViewById(R.id.platformInputEditText);
        notesInput = findViewById(R.id.notesInputEditText);
        statusInput = findViewById(R.id.statusSpinner);
        statusInput.setAdapter(new ArrayAdapter<GameObject.GameStatus>(this, android.R.layout.simple_spinner_item, GameObject.GameStatus.values()));

        Gson gson = new Gson();
        String json = getIntent().getStringExtra("game");
        final int gamePosition = getIntent().getIntExtra("position",-1);
        final GameObject game = gson.fromJson(json,GameObject.class);

        titleInput.setText(game.getTitle());
        platformInput.setText(game.getPlatform());
        notesInput.setText(game.getNotes());
        int enumPosition = game.getGameStatus().ordinal();
        statusInput.setSelection(enumPosition);

        FloatingActionButton saveButton = findViewById(R.id.saveGameButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONHelper jsonHelper = new JSONHelper(EditGameActivity.this);
                try {
                    List<GameObject> gameObjects = jsonHelper.ReadJson();
                    GameObject gameFromList = gameObjects.get(gamePosition);
                    gameFromList.setTitle(titleInput.getText().toString());
                    gameFromList.setPlatform(platformInput.getText().toString());
                    gameFromList.setNotes(notesInput.getText().toString());
                    gameFromList.setGameStatus((GameObject.GameStatus) statusInput.getSelectedItem());
                    jsonHelper.WriteToJson(gameObjects);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
