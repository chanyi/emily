package com.emily.emilyweb.controller;

import com.emily.emilyservice.model.Staff;
import com.emily.emilyservice.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @ResponseBody
    @RequestMapping("getList")
    public List<Staff> getStaffList(){
        return staffService.getStaffList();
    }

    @ResponseBody
    @RequestMapping("getListFromXml")
    public List<Staff> getListFromXml(){
        return staffService.listStaffByIdFromXml("1");
    }
}
