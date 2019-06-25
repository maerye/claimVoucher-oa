package com.tongji.oa.global;

import java.util.ArrayList;
import java.util.List;

public class Contant {

    //职务
    public static final String POST_STAFF="员工";
    public static final String POST_FM="部门经理";
    public static final String POST_GM="总经理";
    public static final String POST_CASHIER="财务";

    public static List<String> getPost(){
        List<String> posts = new ArrayList<String>();
        posts.add(POST_STAFF);
        posts.add(POST_FM);
        posts.add(POST_GM);
        posts.add(POST_CASHIER);
        return posts;
    }

    //费用类别
    public static List<String> getItems(){
        List<String> posts = new ArrayList<String>();
        posts.add("交通");
        posts.add("住宿");
        posts.add("餐饮");
        posts.add("住宿");
        return posts;
    }

    //报销单状态
    public static final String CLAIMVOUCHER_CREATED="新创建";
    public static final String CLAIMVOUCHER_SUBMIT="已提交";
    public static final String CLAIMVOUCHER_APPROVED="已审核";
    public static final String CLAIMVOUCHER_BACK="已打回";
    public static final String CLAIMVOUCHER_TERMINATED="已终止";
    public static final String CLAIMVOUCHER_RECHECK="待复审";
    public static final String CLAIMVOUCHER_PAID="已打款";

    //审核额度
    public static final Double LIMIT_CHECK = 5000d;

    //处理方式
    public static final String DEAL_CREATE ="创建";
    public static final String DEAL_SUBMIT ="提交";
    public static final String DEAL_UPDATE ="修改";
    public static final String DEAL_BACK ="打回";
    public static final String DEAL_REJECT ="拒绝";
    public static final String DEAL_PASS ="通过";
    public static final String DEAL_PAID ="打款";



}
