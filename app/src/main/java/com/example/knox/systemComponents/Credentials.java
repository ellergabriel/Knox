package com.example.knox.systemComponents;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;


//(tableName = "credentials")
@Entity(tableName = "credentials")
public class Credentials {

    @ColumnInfo(name = "username")
    public String uName;

    @ColumnInfo(name = "password")
    public String passwd;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "URL")
    public String url;


    public static List<Credentials> ITEMS = new ArrayList<Credentials>();

    public Credentials() { }

    /****
     * Pre-Condition: the password MUST be AES encrypted before the object is created.
     * @param name - username for credential pair
     * @param pass - ENCRYPTED password for credential pair
     * @param URL - URL for webpage associated with credentials
     */
    public Credentials(String name, String pass, String URL) {
        uName = name;
        passwd = pass;
        url = URL;
    }

    public String getUName() {return this.uName;}
    public String getPasswd(){return this.passwd;}
    public String getUrl(){return this.url;}

    public void setuName(String name){this.uName = name;}
    public void setPasswd(String pass){this.passwd = pass;}
    public void setUrl(String URL){this.url = URL;}
//
    @Override
    public String toString() {
        return "Credentials{" +
                "uName='" + uName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public boolean equals(Credentials c){
        return (c.getUrl().equals(this.url)
                && c.getPasswd().equals(this.passwd)
                && c.getUName().equals(this.uName));
    }
}
