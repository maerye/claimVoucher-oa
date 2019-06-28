package com.tongji.oa.dao;

import com.tongji.oa.entity.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClaimVoucherDao {

    ClaimVoucher getById(Integer id);
    void insert(ClaimVoucher claimVoucher);
    void delete(Integer id);
    void update(ClaimVoucher claimVoucher);

    List<ClaimVoucher> getByCreateSn(String createSn);
    List<ClaimVoucher> getByDealerSn(String nextDealSn);

}
