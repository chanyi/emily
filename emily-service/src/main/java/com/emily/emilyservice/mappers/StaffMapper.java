package com.emily.emilyservice.mappers;


import com.emily.emilyservice.model.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface StaffMapper {

    @Select("select * from staff")
    List<Staff> listStaff();

    @Select("select * from staff where userId = #{userId}")
    List<Staff> listStaffById(@Param("userId") String userId);

    List<Staff> listStaffByIdFromXml(@Param("userId") String userId);

    void addUser(Staff user);

    void addUserByMap(Map<String,Object> selectMap);

    List<Staff> getUserLike(String value);

    List<Staff> getUserByLimit(Map<String,Object> map);

    List<Staff> getUserByLimitRowBounds();

    default List<Staff> test(String str){
        System.out.println(str);
        return new ArrayList<>();
    }
}
