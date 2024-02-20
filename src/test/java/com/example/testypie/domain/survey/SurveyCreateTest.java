package com.example.testypie.domain.survey;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.testypie.domain.survey.entity.Option;
import com.example.testypie.domain.survey.entity.Question;
import com.example.testypie.domain.survey.entity.QuestionType;
import com.example.testypie.domain.survey.entity.Survey;
import com.example.testypie.domain.survey.repository.SurveyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SurveyCreateTest {

    @Autowired private SurveyRepository surveyRepository;

    public void createSurveyTest() {

        Survey survey = Survey.builder().title("새 설문조사").build();

        Question question1 =
                Question.builder()
                        .text("첫 번째 질문")
                        .questionType(QuestionType.SHORT_ANSWER)
                        .survey(survey)
                        .build();

        Question question2 =
                Question.builder()
                        .text("두 번째 질문")
                        .questionType(QuestionType.MULTI_CHOICE)
                        .survey(survey)
                        .build();

        Option option1 = Option.builder().text("옵션 1").question(question2).build();

        Option option2 = Option.builder().text("옵션 2").question(question2).build();

        question2.getOptionList().addAll(List.of(option1, option2));

        survey.getQuestionList().addAll(List.of(question1, question2));

        Survey savedSurvey = surveyRepository.save(survey);

        assertNotNull(savedSurvey.getId());
    }
}
