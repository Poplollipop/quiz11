package com.example.quiz11.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.example.quiz11.constants.QuesType;
import com.example.quiz11.constants.ResMessage;
import com.example.quiz11.dao.QuesDao;
import com.example.quiz11.dao.QuizDao;
import com.example.quiz11.entity.Ques;
import com.example.quiz11.entity.Quiz;
import com.example.quiz11.service.QuizService;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.CreateUpdateReq;

public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuesDao quesDao;

    @Override
    public BasicRes create(CreateUpdateReq req) {
        // 參數檢查
        BasicRes checkResult = checkParams(req, false);
        if (checkResult != null) {
            return checkResult;
        }
        // 因為 quiz pk是流水號，不會重複寫入，不用檢查資料庫是否已經存在相同PK
        // 新增問卷
        // 因為 Quiz 中的 id 是 AI 自動生成的流水號，要讓 quizDao 執行 save 後可以把該 id 的值回傳，
        // 必須要在 Quiz 此 Entity 中將資料型態為 int 的屬性 id
        // 加上 @GeneratedValue(strategy = GenerationType.IDENTITY)
        Quiz quizRes = quizDao.save(new Quiz(req.getName(), req.getDescription(), req.getStartDate(), //
                req.getEndDate(), req.isPublished()));
        // 將 quiz 中的 id 加入到 Ques
        int quizId = quizRes.getId();
        for (Ques item : req.getQuesList()) {
            item.setQuesId(quizId);
        }
        // 新增問題
        quesDao.saveAll(req.getQuesList());
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    private BasicRes checkParams(CreateUpdateReq req, boolean isUpdate) {
        // 如果是更新，檢查 ID 必須大於 0
        if (isUpdate && req.getId() <= 0) {
            return new BasicRes(ResMessage.QUIZ_PARAM_ERROR.getCode(), ResMessage.QUIZ_PARAM_ERROR.getMessage());
        }

        // 如果是新增，檢查 ID 必須等於 0
        if (!isUpdate && req.getId() != 0) {
            return new BasicRes(ResMessage.QUIZ_PARAM_ERROR.getCode(), ResMessage.QUIZ_PARAM_ERROR.getMessage());
        }
        if (!StringUtils.hasText(req.getName()) || !StringUtils.hasText(req.getDescription())) {
            return new BasicRes(ResMessage.QUIZ_PARAM_ERROR.getCode(), ResMessage.QUIZ_PARAM_ERROR.getMessage());
        }
        // 檢查開始時間不能比結束時間晚
        if (req.getStartDate() == null || req.getEndDate() == null || req.getStartDate().isAfter(req.getEndDate())) {
            return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
        }
        // 檢查開始時間不能比今天早(開始時間是今天)
        if (req.getStartDate().isBefore(LocalDate.now())) {
            return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
        }
        // 檢查 Ques
        for (Ques item : req.getQuesList()) {
            if (item.getQuesId() <= 0 || !StringUtils.hasText(item.getQuesName()) || //
                    !StringUtils.hasText(item.getType())) {
                return new BasicRes(ResMessage.QUES_PARAM_ERROR.getCode(), ResMessage.QUES_PARAM_ERROR.getMessage());
            }
            // 檢查題目類型： 單選(single)、多選(multi)、文字(text)
            if (QuesType.checkType(item.getType())) {
                return new BasicRes(ResMessage.QUES_TYPE_ERROR.getCode(), ResMessage.QUES_TYPE_ERROR.getMessage());
            }
            // 檢查非文字類型，選項沒有值
            if (!item.getType().equalsIgnoreCase(QuesType.TEXT.toString()) && !StringUtils.hasText(item.getType())) {
                return new BasicRes(ResMessage.QUES_TYPE_ERROR.getCode(), ResMessage.QUES_TYPE_ERROR.getMessage());
            }
        }

        return null;
    }

    // 編輯問卷
    @Override
    public BasicRes update(CreateUpdateReq req) {
        BasicRes checkResult = checkParams(req, true);
        if (checkResult != null) {
            return checkResult;
        }

        Quiz quizExist = quizDao.getById(req.getId());
        if (quizExist == null) {
            return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
        }
        // 更新問卷訊息
        quizExist.setName(req.getName());
        quizExist.setDescription(req.getDescription());
        quizExist.setStartDate(req.getStartDate());
        quizExist.setEndDate(req.getEndDate());
        quizExist.setPublished(req.isPublished());
        quizDao.save(quizExist);
        // 更新問題
        for (Ques item : req.getQuesList()) {
            if (item.getQuesId() > 0) {
                // 更新現有問題
                Ques existQues = quesDao.getById(item.getQuesId());
                if (existQues != null) {
                    existQues.setQuesName(item.getQuesName());
                    existQues.setType(item.getType());
                    existQues.setOptions(item.getOptions());
                    quesDao.save(existQues);
                } else {
                    return new BasicRes(ResMessage.QUES_NOT_FOUND.getCode(), ResMessage.QUES_NOT_FOUND.getMessage());
                }
            } else {
                // 新增新問題
                item.setQuizId(req.getId());
                quesDao.save(item);
            }
        }
    
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }
}
