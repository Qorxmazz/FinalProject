package com.example.finalproject.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Translations {

        public int id;
        public String text;
        public String lang;
        public int correctness;
        public Object script;
        public ArrayList<Object> transcriptions;
        public ArrayList<Object> audios;
        public boolean isDirect;
        public String lang_name;
        public String dir;
        public String lang_tag;
    }

