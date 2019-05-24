package com.yumodev.arch.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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
