package blossom.project.towelove.community.service.impl;

import blossom.project.towelove.community.entity.Posts;
import blossom.project.towelove.community.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    @Autowired
    private UserInterestService userInterestService;

    @Autowired
    private PostsService postsService;

    public List<Posts> recommendPosts(Long userId, int limit) {

        return null;
    }

    private double calculatePostScore(Posts post, Map<String, Double> userInterest) {
        return post.getTagList().stream()
                .mapToDouble(tag -> userInterest.getOrDefault(tag, 0.0))
                .sum();
    }
}
