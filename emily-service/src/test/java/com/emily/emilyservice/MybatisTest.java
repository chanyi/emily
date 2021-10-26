package com.emily.emilyservice;

import com.emily.emilyservice.mappers.StaffMapper;
import com.emily.emilyservice.model.Staff;
import com.emily.emilyservice.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MybatisTest {

    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();

        //方式1
        StaffMapper userDao = sqlSession.getMapper(StaffMapper.class);
        List<Staff> userList =userDao.listStaff();

        //方式2
//        List<User> userList = sqlSession.selectList("com.example.lisa.dao.UserDao.getUserList");
        userList.stream().forEach((staff)->{
            System.out.println(staff.toString());
        });
        sqlSession.close();
    }
}
