package com.zyq.reggie.controller;

import com.zyq.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBook {
    @Autowired
    private AddressBookService addressBookService;
//    @GetMapping("/list")
//    public R<Page<AddressBook>> get01(){
//        Page<AddressBook> addressBookPage = new Page<>();
//        addressBookService.page()
//    }
}
