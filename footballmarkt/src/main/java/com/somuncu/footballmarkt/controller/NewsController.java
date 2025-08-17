package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.request.news.CreateNewsRequest;
import com.somuncu.footballmarkt.request.news.UpdateNewsRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.news.NewsDto;
import com.somuncu.footballmarkt.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<PageResponse<NewsDto>>> getAllNews(@RequestParam(defaultValue = "0") int pagingOffset) {
        PageResponse<NewsDto> pageResponse = this.newsService.getAllNews(pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , pageResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NewsDto>> getNewsById(@PathVariable Long id) {
        NewsDto newsDto = this.newsService.getNewsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , newsDto));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> createNews(@RequestBody CreateNewsRequest createNewsRequest) {
        this.newsService.createNews(createNewsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Successfully created" , null));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> createNews(@RequestBody UpdateNewsRequest updateNewsRequest) {
        this.newsService.updateNews(updateNewsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Successfully updated " , null));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteById(@RequestParam Long id) {
        this.newsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully deleted" , null));
    }

}
