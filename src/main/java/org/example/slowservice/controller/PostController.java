package org.example.slowservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.slowservice.dto.PostDto;
import org.example.slowservice.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        log.info("GET /posts - Request received");

        int delaySeconds = ThreadLocalRandom.current().nextInt(15, 61);
        log.info("GET /posts - Simulating slow service with {}s delay", delaySeconds);

        try {
            Thread.sleep(delaySeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("GET /posts - Request interrupted", e);
        }

        List<PostDto> posts = postService.getAllPosts();
        log.info("GET /posts - Returning {} posts after {}s delay", posts.size(), delaySeconds);
        return ResponseEntity.ok(posts);
    }
}