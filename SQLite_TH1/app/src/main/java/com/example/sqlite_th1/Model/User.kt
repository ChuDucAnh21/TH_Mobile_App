package com.example.sqlite_th1.Model

class User {

    private var name: String
    private var phone: String

    constructor( name: String, phone: String) {

        this.name = name
        this.phone = phone
    }
    fun getName(): String {
        return name
    }

    fun getPhone(): String {
        return phone
    }
    fun setName(name: String) {
        this.name = name
    }
    fun setPhone(phone: String) {
        this.phone = phone
    }


}