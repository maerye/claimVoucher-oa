package com.tongji.oa.biz.impl;

import com.tongji.oa.biz.ClaimVoucherBiz;
import com.tongji.oa.dao.ClaimVoucherDao;
import com.tongji.oa.dao.ClaimVoucherItemDao;
import com.tongji.oa.dao.DealRecordDao;
import com.tongji.oa.dao.EmployeeDao;
import com.tongji.oa.entity.ClaimVoucher;
import com.tongji.oa.entity.ClaimVoucherItem;
import com.tongji.oa.entity.DealRecord;
import com.tongji.oa.entity.Employee;
import com.tongji.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



@Service
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {

    @Autowired
    private ClaimVoucherDao claimVoucherDao;

    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;

    @Autowired
    private DealRecordDao dealRecordDao;

    @Autowired
    private EmployeeDao employeeDao;

    public void add(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucherDao.insert(claimVoucher);
        for (ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealTime(new Date());
        dealRecord.setDealWay(Contant.DEAL_CREATE);
        dealRecord.setDealSn(claimVoucher.getCreateSn());
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_CREATED);
        dealRecord.setClaimVoucherId(claimVoucher.getId());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);

    }
    public ClaimVoucher get(Integer id) {
        return claimVoucherDao.getById(id);
    }

    public void remove(Integer id) {
        claimVoucherDao.delete(id);
    }

    public void edit(ClaimVoucher claimVoucher) {
        claimVoucherDao.update(claimVoucher);
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealTime(new Date());
        dealRecord.setDealWay(Contant.DEAL_UPDATE);
        dealRecord.setDealSn(claimVoucher.getCreateSn());
        dealRecord.setDealResult("已经修改");
        dealRecord.setClaimVoucherId(claimVoucher.getId());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);
    }

    public List<ClaimVoucher> getByCreater(String createSn) {
        return claimVoucherDao.getByCreateSn(createSn);
    }

    public List<ClaimVoucher> getByNextDealer(String nextDealerSn) {
        return claimVoucherDao.getByDealerSn(nextDealerSn);
    }

    public List<ClaimVoucherItem> getItems(Integer cvid) {
        return claimVoucherItemDao.getByCVId(cvid);
    }

    public List<DealRecord> getRecords(Integer cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {

        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucherDao.update(claimVoucher);

        List<ClaimVoucherItem> olds = claimVoucherItemDao.getByCVId(claimVoucher.getId());

        //旧条目删去
        for (ClaimVoucherItem old:olds){
            boolean has = false;
           for (ClaimVoucherItem item:items){
               if(old.getId()==item.getId()){
                   has=true;
                   break;
               }
           }
           if(!has){
               claimVoucherItemDao.delete(old.getId());
           }
        }
        for (ClaimVoucherItem item:items){
            Integer id = item.getId();
            if(id!=null && id>0){           //该条目原来存在
                claimVoucherItemDao.update(item);
            }else {                         //该条目不存在
                claimVoucherItemDao.insert(item);
            }

        }
    }

    public void submit(Integer id) {
        ClaimVoucher claimVoucher = claimVoucherDao.getById(id);
        Employee employee = employeeDao.findBySn(claimVoucher.getCreateSn());
        claimVoucher.setNextDealSn(employeeDao.getByDepartmentAndPos(employee.getDepartmentSn(),Contant.POST_FM).get(0).getSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        claimVoucherDao.update(claimVoucher);

        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(id);
        dealRecord.setComment("无");
        dealRecord.setDealResult(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setDealTime(new Date());
        dealRecord.setDealWay(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecordDao.insert(dealRecord);
    }

    public void deal(DealRecord dealRecord) {
       ClaimVoucher claimVoucher = claimVoucherDao.getById(dealRecord.getClaimVoucherId());
       Employee dealer = employeeDao.findBySn(dealRecord.getDealSn());

       dealRecord.setDealTime(new Date());
       String dealWay = dealRecord.getDealWay();

       if(dealWay.equals(Contant.DEAL_PASS)){
           if(claimVoucher.getTotalAmount()<=Contant.LIMIT_CHECK || dealer.getPost().equals(Contant.POST_GM)){ //不复审
              claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
              claimVoucher.setNextDealSn(employeeDao.getByDepartmentAndPos(null,Contant.POST_CASHIER).get(0).getSn());

              dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
           }else {
               claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
               claimVoucher.setNextDealSn(employeeDao.getByDepartmentAndPos(null,Contant.POST_GM).get(0).getSn());

               dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
           }
       }else if (dealWay.equals(Contant.DEAL_BACK)){
           claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
           claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

           dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);

       }else if (dealWay.equals(Contant.DEAL_REJECT)){
           claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
           claimVoucher.setNextDealSn(null);

           dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);

       }else if (dealWay.equals(Contant.DEAL_PAID)){
           claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
           claimVoucher.setNextDealSn(null);

           dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
       }

       claimVoucherDao.update(claimVoucher);
       dealRecordDao.insert(dealRecord);
    }
}
