package com.pengffe.pte.modules.question.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.practice.bean.FilterStatusEnum;
import com.pengffe.pte.modules.practice.dao.PracticeDao;
import com.pengffe.pte.modules.practice.dao.UserCollectRecordDao;
import com.pengffe.pte.modules.practice.dao.UserPracticeRecordDao;
import com.pengffe.pte.modules.question.bean.*;
import com.pengffe.pte.modules.question.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Value("${question.filePath}")
    private String filePath;

    private QuestionDao questionDao;

    private CheckServiceImpl checkService;

    private PracticeDao practiceDao;

    private UserPracticeRecordDao userPracticeRecordDao;

    private UserCollectRecordDao userCollectRecordDao;

    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Autowired
    public void setCheckService(CheckServiceImpl checkService) {
        this.checkService = checkService;
    }

    @Autowired
    public void setPracticeDao(PracticeDao practiceDao) {
        this.practiceDao = practiceDao;
    }

    @Autowired
    public void setUserPracticeRecordDao(UserPracticeRecordDao userPracticeRecordDao) {
        this.userPracticeRecordDao = userPracticeRecordDao;
    }
    @Autowired
    public void setUserCollectRecordDao(UserCollectRecordDao userCollectRecordDao) {
        this.userCollectRecordDao = userCollectRecordDao;
    }

    /***********
     * Create
     ***********/
    @Override
    public <T extends Question> T save(T t) {
        if (null == t){
            System.out.println("Fail to find local file or request json is null!");
            return null;
        }

        if (null != questionDao.findById(t.getClass(), t.getQuestionId())){
            System.out.println("Instance exits");
            return null;
        }

        else {
            return questionDao.save(t);
        }
    }

    /**
     * 根据postman传来的json对象，生成相应题目的实例，用于存入db
     * @param type
     * @param mnQuestionRequest
     * @return
     */
    @Override
    public Question readRequestJSONToObject(String type, QuestionRequest mnQuestionRequest){

        // unique
        Question mnQuestion = new Question();
        String audioPath;
        String imagePath;
        String question;
        List<String> options;
        List<String> sampleAnswers;
        if (!type.equals(mnQuestionRequest.getQuestionId().split("-")[0])){
            return null;
        }
        switch (type) {
            /**
             * speaking
             */
            case ("ra"):
                mnQuestion = new RA();
                break;
            case ("rs"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new RS(audioPath);
                break;
            case ("rl"):
                audioPath = mnQuestionRequest.getAudioPath();
                imagePath = mnQuestionRequest.getImagePath();
                mnQuestion = new RL(audioPath, imagePath);
                break;
            case ("di"):
                imagePath = mnQuestionRequest.getImagePath();
                sampleAnswers = mnQuestionRequest.getSampleAnswers();
                mnQuestion = new DI(imagePath, sampleAnswers);
                break;
            case ("asq"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new ASQ(audioPath);
                break;

            /**
             * reading
             */
            case ("rmcs"):
                question = mnQuestionRequest.getQuestion();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new RMCS(question, options);
                break;
            case("rmcm"):
                question = mnQuestionRequest.getQuestion();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new RMCM(question, options);
                break;
            case("ro"):
                mnQuestion = new RO();
                break;
            case("rfib"):
                options = mnQuestionRequest.getOptions();
                mnQuestion = new RFIB(options);
                break;
            case("rwfib"):
                mnQuestion = new RWFIB();
                break;

            /**
             * listening
             */
            case("fib"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new FIB(audioPath);
                break;
            case("hcs"):
                audioPath = mnQuestionRequest.getAudioPath();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new HCS(audioPath, options);
                break;
            case("hiw"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new HIW(audioPath);
                break;
            case("wfd"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new WFD(audioPath);
                break;
            case("mcm"):
                question = mnQuestionRequest.getQuestion();
                audioPath = mnQuestionRequest.getAudioPath();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MCM(audioPath, question, options);
                break;
            case("mcs"):
                question = mnQuestionRequest.getQuestion();
                audioPath = mnQuestionRequest.getAudioPath();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MCS(audioPath, question, options);
                break;
            case("smw"):
                audioPath = mnQuestionRequest.getAudioPath();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new SMW(audioPath, options);
                break;
            case("sst"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new SST(audioPath);
                break;

            /**
             * writing
             */
            case("swt"):
                mnQuestion = new SWT();
                break;
            case("we"):
                mnQuestion = new WE();
                break;
        }

        // common
        mnQuestion.setQuestionId(mnQuestionRequest.getQuestionId());
        mnQuestion.setTitle(mnQuestionRequest.getTitle());
        mnQuestion.setContent(mnQuestionRequest.getContent());
        mnQuestion.setLessonPath(mnQuestionRequest.getLessonPath());

        return mnQuestion;
    }

    /***********
     * Read
     ***********/
    @Override
    public <T> T findById(Class<T> tCLass, String questionId){
        return questionDao.findById(tCLass, questionId);
    }

    @Override
    public <T> List<T> findAll(Class<T> tClass) {
        List list = questionDao.findAll(tClass);
        return checkService.checkDuration(list);
    }

    @Override
    public <T> Page<T> findAllByPage(Class<T> tClass, int pageNum, int pageSize, String sortType){
        return questionDao.findAllByPage(tClass, pageNum - 1, pageSize, sortType);
    }

    public <T extends Question> Page<T> findAllByFilter(Class<T> tClass,
                                                        String userId, String type,
                                                        FilterStatusEnum source,
                                                        FilterStatusEnum frequency,
                                                        FilterStatusEnum practiceStatus,
                                                        FilterStatusEnum collectStatus,
                                                        int pageNum, int pageSize, String sortType)
    {
        List<T> mnQuestionList = findAllByStatus(tClass, userId, type, practiceStatus, collectStatus);
        List<T> qList = new ArrayList<>(mnQuestionList);
        int frequencyThreshold = 3;

        if (source.name().equals("MEMORY") && frequency.name().equals("ALL")){
            for (T item: qList
                 ) {
                if (!item.isMemory()){
                    mnQuestionList.remove(item);
                }
            }
        }
        else if (source.name().equals("MEMORY") && frequency.name().equals("HIGH")) {
            for (T item: qList
            ) {
                if (!item.isMemory()){
                    mnQuestionList.remove(item);
                }
                else if (item.getFrequency() < frequencyThreshold){
                    mnQuestionList.remove(item);
                }
            }
        }
        else if (source.name().equals("MEMORY") && frequency.name().equals("LOW")) {
            for (T item: qList
            ) {
                if (!item.isMemory()){
                    mnQuestionList.remove(item);
                }
                else if (item.getFrequency() >= frequencyThreshold){
                    mnQuestionList.remove(item);
                }
            }
        }
        else if(source.name().equals("VERIFIED") && frequency.name().equals("ALL")) {
            for (T item: qList
            ) {
                if (!item.isVerified()){
                    mnQuestionList.remove(item);
                }
            }
        }
        else if(source.name().equals("VERIFIED") && frequency.name().equals("HIGH")) {
            for (T item: qList
            ) {
                if (!item.isVerified()){
                    mnQuestionList.remove(item);
                }
                else if (item.getFrequency() < frequencyThreshold){
                    mnQuestionList.remove(item);
                }
            }
        }
        else if(source.name().equals("VERIFIED") && frequency.name().equals("LOW")) {
            for (T item: qList
            ) {
                if (!item.isVerified()){
                    mnQuestionList.remove(item);
                }
                else if (item.getFrequency() >= frequencyThreshold){
                    mnQuestionList.remove(item);
                }
            }
        }
        else if(source.name().equals("OTHERS") && frequency.name().equals("ALL")) {
            for (T item: qList
            ) {
                if (item.isVerified() || item.isMemory()){
                    mnQuestionList.remove(item);
                }
            }
        }
        else if(source.name().equals("OTHERS") && frequency.name().equals("HIGH")) {
            for (T item: qList
            ) {
                if (item.isVerified() || item.isMemory()){
                    mnQuestionList.remove(item);
                }
                else if (item.getFrequency() < frequencyThreshold){
                    mnQuestionList.remove(item);
                }
            }
        }
        else if(source.name().equals("OTHERS") && frequency.name().equals("LOW")) {
            for (T item: qList
            ) {
                if (item.isVerified() || item.isMemory()){
                    mnQuestionList.remove(item);
                }
                else if (item.getFrequency() >= frequencyThreshold){
                    mnQuestionList.remove(item);
                }
            }
        }

        long count = mnQuestionList.size();
        System.out.println(count);
        if (0 == count){
            return null;
        }
        // sort the search result
        Sort sort;
        if (sortType.equals("ASC")){
            sort = Sort.by(Sort.Direction.ASC, "id");
        }
        else {
            sort = Sort.by(Sort.Direction.DESC, "frequency");
        }
        if (0 == pageSize){
            pageSize = (int)count;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<T> questionPage = new PageImpl(mnQuestionList, pageable, count);
        return questionPage;
    }

    private <T extends Question> List<T> findAllByStatus(Class<T> tClass,
                                                         String userId, String type,
                                                         FilterStatusEnum practiceStatus,
                                                         FilterStatusEnum collectStatus){
        List<T> mnQuestionList = new ArrayList<>();

        // 全部， 全部
        List<String> allRecord = new ArrayList<>();
        List<T> allQuestion = questionDao.findAll(tClass);
        for (Question question: allQuestion
             ) {
            allRecord.add(question.getQuestionId());
        }
        // 练习，全部
        List<String> practiceRecord = userPracticeRecordDao.findAllByTypeForUser(userId, type);
        // 全部，收藏
        List<String> collectRecord = userCollectRecordDao.findAllByTypeForUser(userId, type);

        List<String> resultList = allRecord;
        if (practiceStatus.name().equals("PRACTICED") && collectStatus.name().equals("COLLECTED")){
            // 练习，收藏
            practiceRecord.retainAll(collectRecord);
            resultList = practiceRecord;
        }
        else if (practiceStatus.name().equals("PRACTICED") && collectStatus.name().equals("ALL")) {
            resultList = practiceRecord;
        }
        else if (practiceStatus.name().equals("PRACTICED") && collectStatus.name().equals("UNCOLLECTED")) {
            // 练习，未收藏
            allRecord.removeAll(collectRecord);
            allRecord.retainAll(practiceRecord);
            resultList = allRecord;
        }
        else if(practiceStatus.name().equals("ALL") && collectStatus.name().equals("COLLECTED")) {
            //全部，收藏
            resultList = collectRecord;
        }
        else if(practiceStatus.name().equals("ALL") && collectStatus.name().equals("UNCOLLECTED")) {
            //全部，未收藏
            allRecord.removeAll(collectRecord);
            resultList = allRecord;
        }
        else if(practiceStatus.name().equals("ALL") && collectStatus.name().equals("ALL")) {
            //全部，全部
            return questionDao.findAll(tClass);
        }
        else if(practiceStatus.name().equals("UNPRACTICED") && collectStatus.name().equals("COLLECTED")) {
            // 未练习，收藏
            allRecord.removeAll(practiceRecord);
            allRecord.retainAll(collectRecord);
            resultList = allRecord;
        }
        else if(practiceStatus.name().equals("UNPRACTICED") && collectStatus.name().equals("UNCOLLECTED")) {
            // 未练习，收藏
            allRecord.removeAll(practiceRecord);
            allRecord.removeAll(collectRecord);
            resultList = allRecord;
        }
        else if(practiceStatus.name().equals("UNPRACTICED") && collectStatus.name().equals("ALL")) {
            // 未练习，全部
            allRecord.removeAll(practiceRecord);
            resultList = allRecord;
        }

        for (String questionId: resultList
        ) {
            mnQuestionList.add(questionDao.findById(tClass, questionId));
        }
        return mnQuestionList;
    }

    @Override
    public <T> Long findCount(Class<T> tClass){
        return questionDao.findCount(tClass);
    }

    @Override
    public UpdateResult update(Question mnQuestion){
        return questionDao.update(mnQuestion);
    }

    @Override
    public <T> UpdateResult updateById(Class<T> tClass, String id, QuestionRequest mnQuestionRequest){
        UpdateResult updateResult = null;
        //whether the question content, such as text, audio, image, question, options, answer, is upadated
        boolean isInfoUpdate = false;

        if(null != mnQuestionRequest.getTitle()){
            updateResult = questionDao.updateTitleById(tClass, id, mnQuestionRequest.getTitle());
        }
        if(null != mnQuestionRequest.getContent()){
            updateResult = questionDao.updateContentById(tClass, id, mnQuestionRequest.getContent());
            isInfoUpdate = true;
        }
        if(mnQuestionRequest.isVerified()){
            updateResult = questionDao.updateVerifiedById(tClass, id, true);
        }
        if(mnQuestionRequest.isPredicted()){
            updateResult = questionDao.updatePredictedById(tClass, id, true);
        }
        if(0 != mnQuestionRequest.getFrequency()){
            updateResult = questionDao.updateFrequencyById(tClass, id, mnQuestionRequest.getFrequency());
        }
        if(null != mnQuestionRequest.getLevel()){
            updateResult = questionDao.updateLevelById(tClass, id, mnQuestionRequest.getLevel());
        }
        if(null != mnQuestionRequest.getTestDate()){
            updateResult = questionDao.updateTestDateById(tClass, id, mnQuestionRequest.getTestDate());
        }
        if(null != mnQuestionRequest.getSource()){
            updateResult = questionDao.updateSourceById(tClass, id, mnQuestionRequest.getSource());
        }
        if(null != mnQuestionRequest.getLessonPath()){
            updateResult = questionDao.updateLessonById(tClass, id, mnQuestionRequest.getLessonPath());
        }
        if(null != mnQuestionRequest.getAudioPath()){
            updateResult = questionDao.updateAudioById(tClass, id, mnQuestionRequest.getAudioPath());
            isInfoUpdate = true;
        }
        if(null != mnQuestionRequest.getImagePath()){
            updateResult = questionDao.updateImageById(tClass, id, mnQuestionRequest.getImagePath());
            isInfoUpdate = true;
        }
        if(null != mnQuestionRequest.getOptions()){
            updateResult = questionDao.updateOptionsById(tClass, id, mnQuestionRequest.getOptions());
            isInfoUpdate = true;
        }
        if(null != mnQuestionRequest.getQuestion()){
            updateResult = questionDao.updateQuestionById(tClass, id, mnQuestionRequest.getQuestion());
            isInfoUpdate = true;
        }

        if (isInfoUpdate){
            questionDao.updateModifiedDateById(tClass, id);
        }
        return updateResult;
    }



    /***********
     * Delete
     ***********/
    @Override
    public <T> DeleteResult deleteById(Class<T> tCLass, String id) {
        return questionDao.deleteById(tCLass, id);
    }
}
