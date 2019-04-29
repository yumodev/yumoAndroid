package com.yumodev.arch.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;

/**
 * Created by yumodev on 17/12/6.
 *
 * 默认使用类名做表名，可以通过@Entity(tableName="表名")指定表名
 * 默认使用变量名为字段名，可以通过 @ColumnInfo(name = "create_time")指定字段名
 *
 * 使用@PrimaryKey指定主键，如果autoGenerate属性设置为true
 * 如果指定为联合主键
 * @Ignore 忽略一个字段
 */
@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    @ColumnInfo(name="name")
    public String username;
    public String password;
    public String phone;
    public String email;
    @ColumnInfo(name = "create_time")
    public long createAt;
    @ColumnInfo(name = "update_time")
    public long updateAt;
    public int age;

    @Override
    public String toString() {
        return (new Gson()).toJson(this);
    }
}
