package com.example.alfasdk.Util;

public class FileMetaData {
    public String displayName;
    public long size;
    public String mimeType;
    public String path;

    @Override
    public String toString()
    {
        return "name : " + displayName + " ; size : " + size + " ; path : " + path + " ; mime : " + mimeType;
    }
}

