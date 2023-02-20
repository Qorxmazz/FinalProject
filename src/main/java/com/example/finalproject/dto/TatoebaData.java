package com.example.finalproject.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TatoebaData {
        public int id;
        public String text;
        public String lang;
        public int correctness;
        public Object script;
        public String license;
        public ArrayList<ArrayList<Translations>> translations;
        public ArrayList<Object> transcriptions;
        public ArrayList<Object> audios;
        public Object user;
        public String highlightedText;
        public Object expandLabel;
        public String lang_name;
        public String dir;
        public String lang_tag;
        public Object is_favorite;
        public boolean is_owned_by_current_user;
        public Object permissions;
        public int max_visible_translations;
        public Object current_user_review;
    }



