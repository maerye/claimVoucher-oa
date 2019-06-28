package com.tongji.oa.dao;

import com.tongji.oa.entity.DealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRecordDao {
    void insert(DealRecord dealRecord);
    List<DealRecord>  selectByClaimVoucher(Integer cvid);
}
