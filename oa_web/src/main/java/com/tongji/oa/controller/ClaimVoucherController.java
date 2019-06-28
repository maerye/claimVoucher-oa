package com.tongji.oa.controller;

import com.tongji.oa.biz.ClaimVoucherBiz;
import com.tongji.oa.dto.ClaimVoucherInfo;
import com.tongji.oa.entity.ClaimVoucher;
import com.tongji.oa.entity.DealRecord;
import com.tongji.oa.entity.Employee;
import com.tongji.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {

    @Autowired
    ClaimVoucherBiz claimVoucherBiz;



    @RequestMapping("/self")
    public String self(HttpSession session,Map<String,Object> map)
    {
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getByCreater(employee.getSn()));
        return "claim_voucher_self";
    }

    @RequestMapping("/deal")
    public String deal(HttpSession session,Map<String,Object> map)
    {
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getByNextDealer(employee.getSn()));
        return "claim_voucher_deal";
    }

    @RequestMapping("/to_add")
    public String toAddCv(Map<String,Object> map){
        map.put("info",new ClaimVoucherInfo());
        map.put("items",Contant.getItems());
        return "claim_voucher_add";
    }

    @RequestMapping("/add")
    public String addCv(HttpSession session,ClaimVoucherInfo info){
        Employee employee = (Employee) session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.add(info.getClaimVoucher(),info.getItems());

        return "redirect:self";
    }

    @RequestMapping("/to_update/{id}")
    public String toUpdate(Map<String,Object> map,@PathVariable("id") String id){

        map.put("items",Contant.getItems());
        ClaimVoucherInfo info = new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherBiz.get(Integer.parseInt(id)));
        info.setItems(claimVoucherBiz.getItems(Integer.parseInt(id)));
        map.put("info",info);
        return "claim_voucher_update";
    }

    @RequestMapping("/update")
    public String update(HttpSession session,ClaimVoucherInfo info){
        Employee employee = (Employee) session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.update(info.getClaimVoucher(),info.getItems());

        return "redirect:deal";
    }
    @RequestMapping("/detail/{id}")
    public String getDetail(@PathVariable("id") String id, Map<String,Object> map){
        Integer intId = Integer.parseInt(id);
        map.put("claimVoucher",claimVoucherBiz.get(intId));
        map.put("items",claimVoucherBiz.getItems(intId));
        map.put("records",claimVoucherBiz.getRecords(intId));

        return "claim_voucher_detail";
    }
    @RequestMapping("/submit/{id}")
    public String submit(@PathVariable("id")String id){
        claimVoucherBiz.submit(Integer.parseInt(id));
        return "redirect:../deal";
    }

    @RequestMapping("/to_check/{id}")
    public String toCheck(Map<String,Object> map,@PathVariable("id") String id){
        Integer intId = Integer.parseInt(id);

        map.put("claimVoucher",claimVoucherBiz.get(intId));
        map.put("items",claimVoucherBiz.getItems(intId));
        map.put("records",claimVoucherBiz.getRecords(intId));
        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(intId);
        map.put("dealRecord",dealRecord);
        return "claim_voucher_check";
    }

    @RequestMapping("/check")
    public String check(HttpSession session,DealRecord dealRecord){
        Employee employee = (Employee) session.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());
        claimVoucherBiz.deal(dealRecord);
        return "redirect:deal";
    }



}
