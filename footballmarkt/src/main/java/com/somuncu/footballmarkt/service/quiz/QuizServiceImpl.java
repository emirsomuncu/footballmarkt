package com.somuncu.footballmarkt.service.quiz;

import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NotAbleToDoThisOperationException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.quiz.NoQuizFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserNotFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.QuizRepository;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.Question;
import com.somuncu.footballmarkt.entities.Quiz;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.quiz.CreateQuizRequest;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.response.dtos.quiz.QuizDto;
import com.somuncu.footballmarkt.response.dtos.quiz.SolveQuizRequest;
import com.somuncu.footballmarkt.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionService questionService;
    private final ModelMapperService modelMapperService;

    @Override
    public PageResponse<QuizDto> getAllQuizzes(int pagingOffset) {

        int pageSize = 2 ;
        int pageNo = pagingOffset/pageSize;

        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<Quiz> quizPage  = this.quizRepository.findAll(pageable);

        List<Quiz> quizList = quizPage.getContent();
        List<QuizDto> quizDtoList = quizList.stream().map(quiz -> this.modelMapperService.forResponse().map(quiz , QuizDto.class)).collect(Collectors.toList());

        PageResponse<QuizDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(quizDtoList);
        pageResponse.setPageNo(quizPage.getNumber());
        pageResponse.setPageSize(quizPage.getSize());
        pageResponse.setTotalPages(quizPage.getTotalPages());
        pageResponse.setTotalElements(quizPage.getTotalElements());
        pageResponse.setLast(quizPage.isLast());

        return pageResponse;
    }

    @Override
    @Cacheable(value = "getQuizById" , key = "#id")
    public QuizDto getQuizById(Long id) {

        Quiz quiz = this.quizRepository.findById(id).orElseThrow(()-> new NoQuizFoundException("No quiz found"));
        return this.modelMapperService.forResponse().map(quiz , QuizDto.class);
    }

    @Cacheable(value = "getQuizzesByUserName" , key = "#userName")
    @Override
    public List<QuizDto> getQuizzesByUserName(String userName , UserDetails userDetails) {

        String email = userDetails.getUsername();
        User user = this.userRepository.findUserByEmail(email).orElseThrow(()-> new UserNotFoundException("You should login to see your quizzes"));
        if(!user.getName().equals(userName)) {
            throw new NotAbleToDoThisOperationException("You can only see your quizzes");
        }

        List<Quiz> quizByUserName = this.quizRepository.findQuizByUserName(userName);
        if(quizByUserName.isEmpty()) {
            throw new NoQuizFoundException("No quiz found for this username");
        }

        return quizByUserName.stream().map(quiz -> this.modelMapperService.forResponse().map(quiz , QuizDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "getQuizzesByUserName" , key = " #result.userName")
    @CachePut(value="getQuizById" , key = "#result.id")
    public QuizDto createQuiz(CreateQuizRequest createQuizRequest , UserDetails userDetails) {

        Quiz quiz = new Quiz();
        quiz.setName(createQuizRequest.getName());
        quiz.setNumOfQuestions(createQuizRequest.getNumOfQuestions());
        quiz.setSolved(false);
        quiz.setNumOfCorrectAnswers(0);
        quiz.setNumOfCorrectAnswers(0);
        quiz.setScore(0.0);

        String mail = userDetails.getUsername();
        User user = this.userRepository.findUserByEmail(mail).orElseThrow(()-> new UserNotFoundException("You must login to create quiz"));
        quiz.setUser(user);

        List<Question> selectedQuestions = new ArrayList<>();

        int categoryCount = createQuizRequest.getQuestionCategories().size();
        if (categoryCount == 0) {
            throw new IllegalArgumentException("You should choose at least one category");
        }

        if(categoryCount >= createQuizRequest.getNumOfQuestions()) {
            throw new IllegalArgumentException("Number of questions should be higher than number of categories");
        }

        int questionsPerCategory = createQuizRequest.getNumOfQuestions() / categoryCount;
        int remaining = createQuizRequest.getNumOfQuestions() % categoryCount;

        Random random = new Random();

        for (int i = 0; i < categoryCount; i++) {
            String category = createQuizRequest.getQuestionCategories().get(i);
            int count = questionsPerCategory + (i < remaining ? 1 : 0);

            List<Question> allQuestionsByCategory = questionService.getAllQuestions().stream()
                    .filter(q -> q.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());

            if (allQuestionsByCategory.size() < count) {
                throw new IllegalArgumentException("No enough question in category : " + category);
            }
            Collections.shuffle(allQuestionsByCategory, random);
            List<Question> selectedFromCategory = allQuestionsByCategory.subList(0, count);
            selectedQuestions.addAll(selectedFromCategory);
        }
        quiz.setQuestionList(selectedQuestions);
        Quiz quiz1 = quizRepository.save(quiz);
        return this.modelMapperService.forResponse().map(quiz1 , QuizDto.class);
    }

    @Override
    @CachePut(value="getQuizById" , key = "#result.id")
    @CacheEvict(value= "getQuizzesByUserName" , key="#result.userName")
    public QuizDto solveQuiz(Long quizId, SolveQuizRequest solveQuizRequest , UserDetails userDetails) {

        String mail = userDetails.getUsername();
        User user = this.userRepository.findUserByEmail(mail).orElseThrow(() -> new UserNotFoundException("You must login to solve quiz"));

        Quiz quiz1= this.quizRepository.findById(quizId).orElseThrow(()-> new NoQuizFoundException("No quiz found to solve"));
        if(!quiz1.getUser().equals(user)) {
            throw new NotAbleToDoThisOperationException("You can only solve your quizzes");
        }

        List<Quiz> quizList = user.getQuizList();
        Quiz quizToSolve = quizList.stream().filter(quiz -> quiz.getId().equals(quizId)).findFirst().orElseThrow(() -> new NoQuizFoundException("No quiz found for user : " + user.getName()));
        List<Question> questionList = quizToSolve.getQuestionList();

        int correct = 0;
        int wrong = 0;

        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            String userAnswer = solveQuizRequest.getAnswers().get(i);

            if (question.getCorrectOption().equalsIgnoreCase(userAnswer)) {
                correct++;
            } else {
                wrong++;
            }
        }

        quizToSolve.setSolved(true);
        quizToSolve.setNumOfCorrectAnswers(correct);
        quizToSolve.setNumOfWrongAnswers(wrong);


        double quizScore = ((double) correct / quizToSolve.getNumOfQuestions()) * 100;
        quizToSolve.setScore(quizScore);
        user.updateQuizScoreAverage();

        userRepository.save(user);

        QuizDto quizDto = this.modelMapperService.forResponse().map(quizToSolve , QuizDto.class);
        return quizDto;
    }

    @Override
    public String getQuizOwnerByQuizId(Long quizId) {

        Quiz quiz = this.quizRepository.findById(quizId).orElseThrow(()-> new NoQuizFoundException("No quiz found to get owner of the quiz"));
        return quiz.getUser().getName();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "getQuizById" , key = "#id") ,
            @CacheEvict(value = "getQuizzesByUserName" , key ="#root.target.getQuizOwnerByQuizId(#id)" , beforeInvocation = true)
    })
    public void deleteQuiz(Long id , UserDetails userDetails ) {

        Quiz quiz = this.quizRepository.findById(id).orElseThrow(()-> new NoQuizFoundException("No quiz found to delete"));
        User user = quiz.getUser();
        User currentUser = this.userRepository.findUserByEmail(userDetails.getUsername()).get();
        if (!user.equals(currentUser)) {
            throw new NotAbleToDoThisOperationException("You can only delete your quizzes");
        }

        user.getQuizList().remove(quiz);
        user.updateQuizScoreAverage();
        userRepository.save(user);

    }
}