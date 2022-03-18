package com.pengffe.pte.modules.question.service;

import com.pengffe.pte.modules.question.bean.*;
import com.pengffe.pte.modules.question.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description
 */
@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private QuestionDao questionDao;

    /**
     * triggered in findAll()
     * check updated and add attributes
     * if surpass 7 days, then change them
     */
    @Override
    public <T extends Question> List<T> checkDuration(List<T> tList) {
        for (T item: tList) {
            /* modify add */
            long insertPeriod = 0;
            if (item.getPostDate() != null && item.isAdd()){
                insertPeriod =  (new Date().getTime() - item.getPostDate().getTime())/(3600000 * 24);
                if (insertPeriod > 7) {
                    questionDao.updateNewById(item.getClass(), item.getQuestionId(), false);
                    item.setAdd(false);
                }
                System.out.println("距初次创建时的时间：" + insertPeriod);
            }
            else {
                System.out.println("Post Date is null");
            }

            /* modify updated */
            long updatePeriod = 0;
            if(item.getModifiedDate() != null && item.isUpdated()){
                updatePeriod =  (new Date().getTime() - item.getModifiedDate().getTime())/(3600000 * 24);
                if (updatePeriod > 7){
                    questionDao.updateUpdatedById(item.getClass(), item.getQuestionId(), false);
                    item.setUpdated(false);
                }
            }
            else {
                System.out.println("Update time not set");
            }
        }
        return tList;
    }

    /**
     *
     * @param questionType "ra" or "ra-1"
     * @return
     */
    @Override
    public Question checkType(String questionType) {
        String type  = questionType;
        if (questionType.contains("-")){
            type = questionType.split("-")[0];
        }

        switch (type) {
            /**
             * speaking
             */
            case ("ra"):
                return new RA();
            case ("rs"):
                return new RS();
            case ("rl"):
                return new RL();
            case ("di"):
                return new DI();
            case ("asq"):
                return new ASQ();

            /**
             * reading
             */
            case ("rmcs"):
                return new RMCS();
            case("rmcm"):
                return new RMCM();
            case("ro"):
                return new RO();
            case("rfib"):
                return new RFIB();
            case("rwfib"):
                return new RWFIB();

            /**
             * listening
             */
            case("fib"):
                return new FIB();
            case("hcs"):
                return new HCS();
            case("hiw"):
                return new HIW();
            case("mcm"):
                return new MCM();
            case("mcs"):
                return new MCS();
            case("smw"):
                return new SMW();
            case("sst"):
                return new SST();
            case("wfd"):
                return new WFD();

            /**
             * writing
             */
            case("swt"):
                return new SWT();
            case("we"):
                return new WE();
        }
        return new Question();
    }
}
