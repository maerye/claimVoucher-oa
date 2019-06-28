package com.tongji.oa.biz;

import com.tongji.oa.entity.ClaimVoucher;
import com.tongji.oa.entity.ClaimVoucherItem;
import com.tongji.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {
    ClaimVoucher get(Integer id);
    void add(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);
    void remove(Integer id);
    void edit(ClaimVoucher claimVoucher);

    List<ClaimVoucher> getByCreater(String createSn);
    List<ClaimVoucher> getByNextDealer(String nextDealerSn);

    List<ClaimVoucherItem> getItems(Integer cvid);
    List<DealRecord> getRecords(Integer cvid);

    void update(ClaimVoucher claimVoucher,List<ClaimVoucherItem> items);
    void submit(Integer id);
    void deal(DealRecord dealRecord);

}
