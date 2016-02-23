package com.example.prabhav.assignment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sneha on 08-05-2015.
 */
public class Address1 implements Parcelable {

    public String add; //lat, longi;
    public double lat;
    public  double longi;

    public Address1(Parcel p) {
        this.add =p.readString();
        //this.lat=p.readString();
        //this.longi=p.readString();
        this.lat = p.readDouble();
        this.longi =p.readDouble();
    }

    public Address1() {

    }

    public void setAdd(String add){
        add = this.add;
    }

    public String getAdd(){
        return add;
    }

    public void  setLat(Double lat){
        lat = this.lat;
    }

    public Double getLat(){
        return  lat;
    }

    public void setLongi(Double longi){
        longi = this.longi;
    }

    public Double getLongi(){
        return  longi;
    }

    /**
     * Parcelable creator. Do not modify this function.
     */
    public static final Parcelable.Creator<Address1> CREATOR = new Parcelable.Creator<Address1>() {
        public Address1 createFromParcel(Parcel p) {
            return new Address1(p);
        }

        public Address1[] newArray(int size) {
            return new Address1[size];
        }
    };

    public Address1(String add, Double lat, Double longi){
        this.add =add;
        this.lat=lat;
        this.longi = longi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.add);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.longi);
    }
}
