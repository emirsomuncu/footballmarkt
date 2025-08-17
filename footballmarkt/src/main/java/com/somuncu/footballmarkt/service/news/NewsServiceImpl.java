package com.somuncu.footballmarkt.service.news;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.images.NoImageFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.news.NoNewsFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.ClubRepository;
import com.somuncu.footballmarkt.dao.ImageRepository;
import com.somuncu.footballmarkt.dao.NewsRepository;
import com.somuncu.footballmarkt.dao.PlayerRepository;
import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.entities.Image;
import com.somuncu.footballmarkt.entities.News;
import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.request.news.CreateNewsRequest;
import com.somuncu.footballmarkt.request.news.UpdateNewsRequest;
import com.somuncu.footballmarkt.response.DetermineNumbersForPagingResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.news.NewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final ImageRepository imageRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public PageResponse<NewsDto> getAllNews(int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = this.determineNumbersForPaging(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<News> newsPage = this.newsRepository.findAll(pageable);

        List<News> newsList = newsPage.getContent();
        List<NewsDto> newsDtoList = this.convertNewsListToNewsDtoList(newsList);

        PageResponse<NewsDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(newsDtoList);
        pageResponse.setPageNo(newsPage.getNumber());
        pageResponse.setPageSize(newsPage.getSize());
        pageResponse.setTotalPages(newsPage.getTotalPages());
        pageResponse.setTotalElements(newsPage.getTotalElements());
        pageResponse.setLast(newsPage.isLast());

        return pageResponse;
    }

    @Override
    public NewsDto getNewsById(Long id) {

        News news = this.newsRepository.findById(id).orElseThrow(()-> new NoNewsFoundException("No news found"));
        return this.convertNewsToNewsDto(news);

    }

    @Override
    @Transactional
    public void createNews(CreateNewsRequest createNewsRequest) {

        News news = new News();
        news.setText(createNewsRequest.getText());

        if(createNewsRequest.getPlayerId() == null) {
            news.setPlayer(null);
        }
        else {
            Player player = this.playerRepository.findById(createNewsRequest.getPlayerId()).orElseThrow(()-> new NoPlayerFoundException("No player found to assign to news"));
            news.setPlayer(player);
        }

        Club club = this.clubRepository.findById(createNewsRequest.getClubId()).orElseThrow(()-> new NoClubFoundException("No club found to assign to news"));
        news.setClub(club);

        for(Long imageId : createNewsRequest.getImagesIds()) {
            Image image = this.imageRepository.findById(imageId).orElseThrow(()-> new NoImageFoundException("No image found"));
            image.setNews(news);
            news.getImages().add(image);
        }

        this.newsRepository.save(news);
    }

    @Override
    public void updateNews(UpdateNewsRequest updateNewsRequest) {

        News newsToUpdate = this.newsRepository.findById(updateNewsRequest.getId()).orElseThrow(()-> new NoNewsFoundException("No news found to update"));
        newsToUpdate.setText(updateNewsRequest.getText());
        newsToUpdate.setPlayer(this.playerRepository.findById(updateNewsRequest.getPlayerId()).orElseThrow(()-> new NoPlayerFoundException("No player found to assign to news")));
        newsToUpdate.setClub(this.clubRepository.findById(updateNewsRequest.getClubId()).orElseThrow(()-> new NoClubFoundException("No club found for assigning to news")));

    }

    @Override
    public void deleteById(Long id) {

        News news = this.newsRepository.findById(id).orElseThrow(()-> new NoNewsFoundException("No news found to delete"));
        this.newsRepository.delete(news);

    }

    @Override
    public NewsDto convertNewsToNewsDto(News news) {

        NewsDto newsDto = this.modelMapperService.forResponse().map(news , NewsDto.class);
        return newsDto;
    }

    @Override
    public List<NewsDto> convertNewsListToNewsDtoList(List<News> allNews) {

        List<NewsDto> allNewsDtos = allNews.stream().map(this::convertNewsToNewsDto).collect(Collectors.toList());
        return allNewsDtos;
    }

    @Override
    public DetermineNumbersForPagingResponse determineNumbersForPaging(int pagingOffset) {

        int pageSize = 2 ;
        int pageNo = pagingOffset/pageSize;

        return new DetermineNumbersForPagingResponse(pageNo,pageSize);
    }
}
