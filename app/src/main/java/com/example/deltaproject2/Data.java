package com.example.deltaproject2;

import android.media.Image;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private String tip;
    public String getTip() {
        return tip;
    }
    private String type;
    ArrayList<String> types;

    public String getType() {
        return type;
    }

    public ArrayList<String> getTypes() {
        return types;
    }
    ArrayList<Characters> characters;
    ArrayList<Scores> scores;
    String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Scores> getScores() {
        return scores;
    }

        public ArrayList<Characters> getcharacters() {
        return characters;
    }

    class Characters{
        @SerializedName("name")
        String name;
        @SerializedName("description")
        String description;
        @SerializedName("type")
        String type;
        @SerializedName("imageUrl")
        String imageUrl;
        public String getName() {
            return name;
        }
        public String getDesc() {
            return description;
        }
        public String getType(){
            return type;
        }
        public String getimageUrl() {
            return imageUrl;
        }
    }
    class Scores{
        @SerializedName("name")
        String name;
        @SerializedName("score")
        int score;


        public String getname2() {
            return name;
        }

        public void setname(String name) {
            this.name = name;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }

}
