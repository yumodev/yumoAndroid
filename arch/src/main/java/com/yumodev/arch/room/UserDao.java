package com.yumodev.arch.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by yumodev on 17/12/6.
 */

@Dao
public interface UserDao {

    @Query("select * from User")
    List<User> getAll();

    @Insert
    void insertAll(User... userDemos);

    @Delete
    void delete(User userDemo);
}
