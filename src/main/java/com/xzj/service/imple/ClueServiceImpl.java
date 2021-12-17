package com.xzj.service.imple;

import com.github.pagehelper.PageHelper;
import com.xzj.dao.*;
import com.xzj.domain.*;
import com.xzj.service.ClueService;
import com.xzj.util.DateTimeUtil;
import com.xzj.util.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ClueServiceImpl implements ClueService {

    //线索
     @Resource
      private ClueDao clueDao;
      @Resource
      private ClueActivityRelationDao clueActivityRelationDao;
       @Resource
       private ClueRemarkDao clueRemarkDao;

        //客户
       @Resource
      private CustomerDao customerDao;
       @Resource
      private CustomerRemarkDao customerRemarkDao;

       //联系人
       @Resource
       private ContactsDao contactsDao;
       @Resource
       private ContactsRemarkDao contactsRemarkDao;
        @Resource
        private ContactsActivityRelationDao contactsActivityRelationDao;

        //交易
        @Resource
        private TranDao tranDao;

       @Resource
        private TranHistoryDao tranHistoryDao;






    @Override
    public boolean save(Clue clue) {

         int num=clueDao.save(clue);
         boolean flag=false;
          if (num>=1){
              flag=true;
          }

        return flag;
    }

    //分页查询
    @Override
    public Map<String, Object> getAllClue(Integer pageNo, Integer pageSize) {

          int num=clueDao.getCount();

        PageHelper.startPage(pageNo,pageSize);

               List<Clue> clueList=clueDao.getAllClue();

        Map<String, Object> map=new HashMap<>();

        map.put("clue",clueList);
        map.put("total",num);

        return map;
    }

    @Override
    public Clue getIdClue(String id) {

        Clue clue=clueDao.getIdClue(id);

        return clue;
}

    @Override
        public List<Activity> clueIDGetActivity(String clueId) {

            System.out.println("市场活动关联的service执行");
            List<Activity> list=clueDao.getActivityList(clueId);


            return list;
    }

    @Override
    public Map<String, Object> deleteCar(String id) {

         int num=clueDao.deleteCar(id);
         boolean flag=true;

         if (num<1){
              flag=false;
         }

        Map<String, Object> map=new HashMap<>();
         map.put("success",flag);
        return map;
    }

    //模态窗口数据
    @Override
    public List<Activity> modelActivity(String clueId) {


        List<Activity> list=clueDao. modelActivityClueId(clueId);

        return list;

    }

    @Override
    public List<Activity> textActivity(String textActivity,String clueId) {

        List<Activity> listText=null;

        if (textActivity!=null) {
            listText = clueDao.getActivityListText(textActivity,clueId);

         }
        return listText;
    }

    @Override
    public Map<String, Object> addActivityAndClue(String[] ids, String clueId) {
          boolean flag=true;

        for (String activityid:
          ids
        ) {
            ClueActivityRelation car=new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(clueId);
            car.setActivityId(activityid);


                   int num =clueDao.addActivityAndClueRemark(car);

                   if (num<=0){
                       flag=false;
                   }

        }



        Map<String, Object> map=new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @Override
    public List<Activity> getActivityConventModel(String name) {
            List<Activity> list=clueDao.getActivityConventModel(name);
             return list;
    }

       //线索转换
    @Override
    public boolean conversionActivity(String clueId, String flag, Tran tran, String createBy) {
         boolean flag1=true;
         String createTime= DateTimeUtil.getSysTime();

               //(1)通过线索id获取线索对象
               Clue clue=clueDao.getById(clueId);

               //(2)通过线索对象寻找客户信息
               String company=clue.getCompany();

               Customer customer=customerDao.getCustmoerName(company);

               if (customer==null){

                   customer=new Customer();

                   customer.setId(UUIDUtil.getUUID());
                   customer.setAddress(clue.getAddress());
                   customer.setWebsite(clue.getWebsite());
                   customer.setPhone(clue.getPhone());
                   customer.setOwner(clue.getOwner());
                   customer.setNextContactTime(clue.getNextContactTime());
                   customer.setName(company);
                   customer.setDescription(clue.getDescription());
                   customer.setCreateTime(createTime);
                   customer.setCreateBy(createBy);
                   customer.setContactSummary(clue.getContactSummary());

                   int customerNum=customerDao.save(customer);

                   if (customerNum!=1){
                       flag1=false;
                   }

               }

                //通过线索对象提取联系人信息保存联系人
                   Contacts contacts=new Contacts();
                   contacts.setId(UUIDUtil.getUUID());
                   contacts.setSource(clue.getSource());
                   contacts.setOwner(clue.getOwner());
                   contacts.setNextContactTime(clue.getNextContactTime());
                   contacts.setMphone(clue.getMphone());
                   contacts.setJob(clue.getJob());
                   contacts.setFullname(clue.getFullname());
                   contacts.setEmail(clue.getEmail());
                   contacts.setDescription(clue.getDescription());
                   contacts.setCustomerId(customer.getId());
                  contacts.setCreateTime(createTime);
                  contacts.setCreateBy(createBy);
                  contacts.setContactSummary(clue.getContactSummary());
                  contacts.setAppellation(clue.getAppellation());
                  contacts.setAddress(clue.getAddress());

                  int contactsNum=contactsDao.save(contacts);

                  if (contactsNum!=1){
                     flag1=false;
                  }

                  //线索备注转换到客户备注和联系人备注
                 List<ClueRemark> clueRemarkslist=clueRemarkDao.getListClud(clueId);

        for (ClueRemark clueRemark : clueRemarkslist) {

                 String noteContent=clueRemark.getNoteContent();

                  CustomerRemark customerRemark=new CustomerRemark();

                  customerRemark.setId(UUIDUtil.getUUID());
                  customerRemark.setCreateBy(createBy);
                  customerRemark.setCreateTime(createTime);
                  customerRemark.setCustomerId(customer.getId());
                  customerRemark.setEditFlag("0");
                  customerRemark.setNoteContent(noteContent);
                     int customerNum=customerRemarkDao.save(customerRemark);

                     if (customerNum!=1){
                         flag1=false;
                     }

           ContactsRemark contactsRemark=new ContactsRemark();

            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int contactsRemarkNum=contactsRemarkDao.save(contactsRemark);

            if (contactsRemarkNum!=1){
                flag1=false;
            }

        }

        //线索和市场活动转到联系人和市场活动
           List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationDao.getListClueId(clueId);

        for (ClueActivityRelation clueActivityRelation :clueActivityRelationList) {
             String activityId=clueActivityRelation.getId();

             ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
             contactsActivityRelation.setId(UUIDUtil.getUUID());
              contactsActivityRelation.setActivityId(activityId);
              contactsActivityRelation.setContactsId(contacts.getId());

                int contactsActivityRelationNum =contactsActivityRelationDao.save(contactsActivityRelation);
                   if (contactsActivityRelationNum!=1){
                       flag1=false;
                   }
            }

             //判断有没有创建交易
            if ("true".equals(flag)){

                tran.setSource(clue.getSource());
                tran.setOwner(clue.getOwner());
                tran.setNextContactTime(clue.getNextContactTime());
                tran.setDescription(clue.getDescription());
                tran.setCustomerId(customer.getId());
                tran.setContactSummary(clue.getContactSummary());
                tran.setContactsId(contacts.getId());

                   int tranNum=tranDao.save(tran);

                   if (tranNum!=1){
                       flag1=false;
                   }

                   TranHistory tranHistory=new TranHistory();
                   tranHistory.setId(UUIDUtil.getUUID());
                   tranHistory.setCreateBy(createBy);
                   tranHistory.setCreateTime(createTime);
                   tranHistory.setExpectedDate(tran.getExpectedDate());
                   tranHistory.setMoney(tran.getMoney());
                   tranHistory.setStage(tran.getStage());
                   tranHistory.setTranId(tran.getId());

                   int tranHistoryNum=tranHistoryDao.save(tranHistory);

                   if (tranHistoryNum!=1){
                       flag1=false;
                   }


            }

             //删除

        for (ClueRemark clueRemark :
                clueRemarkslist) {
               int clueRemarkNum=clueRemarkDao.delete(clueRemark);
                  if (clueRemarkNum!=1){
                      flag1=false;
                  }

        }

        for (ClueActivityRelation clueActivityRelation:
              clueActivityRelationList
         ) {
            int  clueActivityRelationNum=clueActivityRelationDao.delete(clueActivityRelation);
            if (clueActivityRelationNum!=1){
                flag1=false;
            }
        }

            int clueNum=clueDao.delete(clueId);

           if (clueNum!=1){
               flag1=false;
           }

                  return flag1;
    }
}
