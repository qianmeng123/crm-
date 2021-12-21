package com.xzj.service.imple;

import com.xzj.dao.ClueDao;
import com.xzj.dao.CustomerDao;
import com.xzj.dao.TranDao;
import com.xzj.dao.TranHistoryDao;
import com.xzj.domain.Customer;
import com.xzj.domain.Tran;
import com.xzj.domain.TranHistory;
import com.xzj.service.TranSerice;
import com.xzj.util.DateTimeUtil;
import com.xzj.util.UUIDUtil;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranSericeImpl implements TranSerice {
     @Resource
     private CustomerDao customerDao;

     @Resource
     private TranDao tranDao;

     @Resource
     private ClueDao clueDao;

     @Resource
     private TranHistoryDao tranHistoryDao;


    @Override
    public boolean save(Tran tran) {

        boolean flag=true;

            String customerName=tran.getCustomerId();

            //查询有没有这个客户
                  Customer customer=customerDao.getNameId(customerName);

                  //没有这个用户
                  if (customer==null){
                      customer=new Customer();
                      customer.setId(UUIDUtil.getUUID());
                      customer.setCreateTime(DateTimeUtil.getSysTime());
                      customer.setCreateBy(tran.getCreateBy());
                      customer.setNextContactTime(tran.getNextContactTime());
                      customer.setName(customerName);
                      customer.setContactSummary(tran.getContactSummary());
                      customer.setOwner(tran.getOwner());
                         int num=customerDao.save(customer);
                     if (num!=1){
                         flag=false;
                     }
                  }
                  //获取到id
                  tran.setCustomerId(customer.getId());

                   int num=tranDao.save(tran);

                   if (num>1){
                       flag=false;
                   }

                  //添加交易历史
               TranHistory tranHistory=new TranHistory();
               tranHistory.setId(UUIDUtil.getUUID());
               tranHistory.setStage(tran.getStage());
               tranHistory.setCreateBy(tran.getCreateBy());
               tranHistory.setCreateTime(DateTimeUtil.getSysTime());
               tranHistory.setMoney(tran.getMoney());
               tranHistory.setTranId(tran.getId());
               tranHistory.setExpectedDate(tran.getExpectedDate());

                  int num2=tranHistoryDao.save(tranHistory);
                        if (num2!=1){
                            flag=false;
                        }



        return flag;

    }

    @Override
      public Tran detail(String id) {
                   Tran tran=tranDao.getAllId(id);

         return tran;
    }

    @Override
    public List<TranHistory> getTranHistory(String tranId) {

        List<TranHistory> tranHistoryList=tranHistoryDao.getTranHistoryList(tranId);

        return tranHistoryList;
    }

    @Override
    public Map<String, Object> changeStage(Tran tran) {

        Map<String,Object> map=new HashMap<>();
              boolean flag=true;

               int num=tranDao.updateChangeStage(tran);
                  if (num!=1){
                      flag=false;
                  }

                  //添加历史记录
        TranHistory tranHistory=new TranHistory();
                  tranHistory.setId(UUIDUtil.getUUID());
                  tranHistory.setExpectedDate(tran.getExpectedDate());
                  tranHistory.setTranId(tran.getId());
                  tranHistory.setMoney(tran.getMoney());
                  tranHistory.setCreateTime(DateTimeUtil.getSysTime());
                  tranHistory.setStage(tran.getStage());
                  tranHistory.setCreateBy(tran.getEditBy());

                     int num2=tranHistoryDao.save(tranHistory);
                        if (num2!=1){
                            flag=false;
                        }


                        map.put("success",flag);


        return map;
    }

    @Override
    public Map<String, Object> getCountAndMap() {

                        int count=tranDao.getCount();

                         List<Map<String,String>> list=tranDao.getMap();
               Map<String,Object> newMap=new HashMap<>();

                newMap.put("total",count);
                newMap.put("dataList",list);
        return newMap;
    }
}
