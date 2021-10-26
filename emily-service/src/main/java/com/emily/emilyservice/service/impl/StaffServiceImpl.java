package com.emily.emilyservice.service.impl;

import com.emily.emilyservice.mappers.StaffMapper;
import com.emily.emilyservice.model.Staff;
import com.emily.emilyservice.service.StaffService;
import com.emily.emilyservice.utils.es.MapperEsBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffMapper staffMapper;


    @Override
    public List<Staff> getStaffList() {
        return staffMapper.listStaff();
    }

    @Override
    public List<Staff> listStaffById(String userId) {
        return staffMapper.listStaffById(userId);
    }


    @Override
    public List<Staff> listStaffByIdFromXml(String userId){
        return staffMapper.listStaffByIdFromXml(userId);
    }




    public List<Staff> getUserList1(String userId) {
        MapperEsBridge.execute(userId,(str)->staffMapper.test(str));
        return new ArrayList<>();
    }
}
