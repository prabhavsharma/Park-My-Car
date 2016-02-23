package com.example.prabhav.assignment;

/**
 * Created by Prabhav on 15-04-2015.
 */


        import android.net.Uri;

/**
 * Created by Johnny Manson on 23.07.13.
 */
public class Contact {

    private String _name, _phone, _email, _address;
    private Uri _imageURI;
    private int _id;

    public Contact (int id, String name, String phone, String email, String address, Uri imageURI) {
        _id = id;
        _name = name;
        _phone = phone;
        _email = email;
        _address = address;
        _imageURI = imageURI;
    }

    public Contact() {

    }

    public int getId() { return _id; }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void set_imageURI(Uri _imageURI) {
        this._imageURI = _imageURI;
    }

    public String getName() {
        return _name;
    }

    public String getPhone() {
        return _phone;
    }

    public String getEmail() {
        return _email;
    }

    public String getAddress() {
        return _address;
    }

    public Uri getImageURI() { return _imageURI; }
}


