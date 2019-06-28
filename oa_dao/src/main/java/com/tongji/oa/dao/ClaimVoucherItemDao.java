package com.tongji.oa.dao;

import com.tongji.oa.entity.ClaimVoucher;
import com.tongji.oa.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimVoucherItemDao {

    void insert(ClaimVoucherItem claimVoucherItem);
    void delete(Integer id);
    void update(ClaimVoucherItem claimVoucherItem);
    List<ClaimVoucherItem> getByCVId(Integer cvid);

}
