package com.example.quiz11.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
import com.example.quiz11.vo.DeleteReq;
import com.example.quiz11.vo.FillinReq;
import com.example.quiz11.vo.SearchReq;
import com.example.quiz11.vo.SearchRes;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuesDao quesDao;

    @Transactional
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
            item.setQuizId(quizId);
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
            if (!QuesType.checkType(item.getType())) {
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
    @Transactional
    @Override
    public BasicRes update(CreateUpdateReq req) {
        BasicRes checkResult = checkParams(req, true);
        if (checkResult != null) {
            return checkResult;
        }
        // 檢查 Ques 的 quiz_id 是否與 Quiz 的 id相符
        int quizId = req.getId();
        for (Ques item : req.getQuesList()) {
            if (item.getQuizId() != quizId) {
                return new BasicRes(ResMessage.QUIZID_MISMATCH.getCode(), ResMessage.QUIZID_MISMATCH.getMessage());
            }
        }
        // 問卷可以更新的狀態: 1. 未發布 2. 已發佈但尚未開始
        Optional<Quiz> op = quizDao.findById(quizId);

        // 確認問卷是否存在
        // if (op.isEmpty()) {
        // return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(),
        // ResMessage.QUIZ_NOT_FOUND.getMessage());
        // }
        // // 取得問卷(資料庫中的資料)
        Quiz quiz = op.get();
        // // 確認問卷是否可以進行更新
        // // 尚未發布: quiz.isPublished()
        // // 已發佈但尚未開始: quiz.isPublished() &&
        // req.getStartDate().isAfter(LocalDate.now())
        // // 小括號分組用
        // // 排除法: 所以整個邏輯前面加入 ! 表示反相
        if (!(!quiz.isPublished() || (quiz.isPublished() && req.getStartDate().isAfter(LocalDate.now())))) {
            return new BasicRes(ResMessage.QUIZ_UPDATE_FAILED.getCode(), ResMessage.QUIZ_UPDATE_FAILED.getMessage());
        }

        // 從資料庫中透過ID取得 Quiz 物件
        Quiz quizExist = quizDao.getById(req.getId());

        // 如果未找到對應的 Quiz
        if (quizExist == null) {
            // 回傳包含錯誤代碼和訊息的回應，表示未找到 Quiz
            return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
        }

        // // 將 req 中的值 set 回從資料庫取出的 quiz 中
        // quiz.setName(req.getName());
        // quiz.setDescription(req.getDescription());
        // quiz.setStartDate(req.getStartDate());
        // quiz.setEndDate(req.getEndDate());
        // quiz.setPublished(req.isPublished());
        // // 更新問卷
        // quizDao.save(quiz);
        // // 先刪除相同 quiz_id 的問卷所有問題，再新增
        // quesDao.deleteByQuizId(quizId);
        // quesDao.saveAll(req.getQuesList());
        // return new BasicRes(ResMessage.SUCCESS.getCode(),
        // ResMessage.SUCCESS.getMessage());
        // 更新問卷訊息
        quiz.setName(req.getName());
        quiz.setDescription(req.getDescription());
        quiz.setStartDate(req.getStartDate());
        quiz.setEndDate(req.getEndDate());
        quiz.setPublished(req.isPublished());
        quizDao.save(quiz);

        // 刪除現有問題
        quesDao.deleteByQuizId(quizId);
        // 更新問題
        for (Ques item : req.getQuesList()) {
            item.setQuizId(quizId);
            quesDao.save(item);
        }

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Override
    public BasicRes delete(DeleteReq req) {
        // 刪問卷
        quizDao.deleteByIdIn(req.getQuizIdList());
        // 刪相同 quiz_id問卷的所有問題
        quesDao.deleteByQuizIdIn(req.getQuizIdList());
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Override
    public SearchRes search(SearchReq req) {
        // 檢視條件
        String name = req.getName();
        LocalDate starDate = req.getStartDate();
        LocalDate endDate = req.getEndDate();
        // 若 name = null 或空(白)字串，一律轉成空字串
        if (!StringUtils.hasText(name)) {
            name = "";
        }
        // 若沒有開始日期條件，將日期預設為1970年1月1日
        if (starDate == null) {
            starDate = LocalDate.of(1970, 1, 1);
        }
        // 若沒有結束日期條件，將日期預設為9999年12月31日
        if (endDate == null) {
            endDate = LocalDate.of(9999, 12, 31);
        }
        List<Quiz> quizList = quizDao.getByConditions(name, starDate, endDate);
        return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), quizList);
    }

    @Override
    public List<Quiz> findAllSurveys() {
        return quizDao.findAll(); // 從資料庫取得所有問卷
    }

    @Override
    public BasicRes fillin(FillinReq req) {
        // 參數檢查
        if (req.getQuizId() <= 0) {
            return new BasicRes(ResMessage.QUIZ_ID_ERROR.getCode(), ResMessage.QUIZ_ID_ERROR.getMessage());
        }
        if (!StringUtils.hasText(req.getUserName()) || !StringUtils.hasText(req.getEmail())) {
            return new BasicRes(ResMessage.USERNAME_AND_EMAIL_REQUIRED.getCode(),
                    ResMessage.USERNAME_AND_EMAIL_REQUIRED.getMessage());
        }
        if (req.getAge() < 12) {
            return new BasicRes(ResMessage.AGE_ABOVE_12.getCode(), ResMessage.AGE_ABOVE_12.getMessage());
        }
        if (CollectionUtils.isEmpty(req.getAnswer())) {
            return new BasicRes(ResMessage.ANSWER_REQUIRED.getCode(), ResMessage.ANSWER_REQUIRED.getMessage());
        }

        // 需要檢查填寫日期是否是問間可填寫的時間範圍內，要比對存在資料庫中的資料
        // 比對資料庫的問卷和問題
        // 可以填寫問卷必須是已發布的
        Quiz quiz = quizDao.getByIdAndPublishedTrue(req.getQuizId());
        if (quiz == null) {
            return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
        }
        if (req.getFillinDate() == null || req.getFillinDate().isBefore(quiz.getStartDate())
                || req.getFillinDate().isAfter(quiz.getEndDate())) {
            return new BasicRes(ResMessage.DATE_RANGE_ERROR.getCode(), ResMessage.DATE_RANGE_ERROR.getMessage());
        }
        // 比對問題
        List<Ques> quesList = quesDao.getByQuizId(req.getQuizId());
        if(CollectionUtils.isEmpty(quesList)){
            return new BasicRes(ResMessage.QUES_NOT_FOUND.getCode(), ResMessage.QUES_NOT_FOUND.getMessage());
        }
        return null;
    }

}
