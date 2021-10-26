package com.emily.emilyservice.service;

import com.emily.emilyservice.model.Staff;

import java.util.List;

public interface StaffService {

    List<Staff> getStaffList();

    List<Staff> listStaffById(String userId);

    List<Staff> listStaffByIdFromXml(String userId);
}
