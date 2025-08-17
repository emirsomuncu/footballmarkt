package com.somuncu.footballmarkt.service.news;

import com.somuncu.footballmarkt.entities.News;
import com.somuncu.footballmarkt.request.news.CreateNewsRequest;
import com.somuncu.footballmarkt.request.news.UpdateNewsRequest;
import com.somuncu.footballmarkt.response.DetermineNumbersForPagingResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.news.NewsDto;

import java.util.List;

public interface NewsService {

    public PageResponse<NewsDto> getAllNews(int pagingOffset);
    public NewsDto getNewsById(Long id);
    public void createNews(CreateNewsRequest createNewsRequest);
    public void updateNews(UpdateNewsRequest updateNewsRequest);
    public void deleteById(Long id);
    public NewsDto convertNewsToNewsDto(News news);
    public List<NewsDto> convertNewsListToNewsDtoList(List<News> allNews);
    public DetermineNumbersForPagingResponse determineNumbersForPaging(int pagingOffset);
}
