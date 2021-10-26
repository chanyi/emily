package com.emily.emilyservice;

import com.emily.emilyservice.harry.HarryPotterTest;
import com.emily.emilyservice.mappers.StaffMapper;
import com.emily.emilyservice.model.Staff;
import com.emily.emilyservice.service.StaffService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class EmilyServiceApplicationTests {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private StaffService staffService;

    @Autowired
    private HarryPotterTest harryPotterTest;

    @Test
    void testHarry() {
        System.out.println("test!!!");
        harryPotterTest.deal();
        System.out.println("end!");
    }


    @Test
    void testStaffList() {
        System.out.println("test!!!");
        List<Staff> list = staffMapper.listStaff();
        if(!list.isEmpty()){
            for (Staff staff : list )  {
                System.out.println(staff.toString());
            }
        }
        System.out.println("end!");
    }


    @Test
    void testStaffListById() {
        System.out.println("test!!!");
        List<Staff> list = staffMapper.listStaffById("1");
        if(!list.isEmpty()){
            for (Staff staff : list )  {
                System.out.println(staff.toString());
            }
        }
        System.out.println("end!");
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testStaffFromMybatis() {
        System.out.println("test!!!");
        List<Staff> list = staffMapper.listStaff();
        if(!list.isEmpty()){
            for (Staff staff : list )  {
                System.out.println(staff.toString());
            }
        }
        System.out.println("end!");
    }

    @Test
    void test1() {
        System.out.println("test!!!");
        List<Staff> list = staffService.listStaffById("1");
        System.out.println("end!");
    }

}

