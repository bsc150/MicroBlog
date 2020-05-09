package com.example.MicroBlog.blog;
import com.example.MicroBlog.exception.BlogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final int TRENDING_LIMIT = 10;
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        blogRepository.findAll().forEach(b->blogs.add(b));
        return blogs;
    }
    public Blog getBlog(int id) throws BlogNotFoundException {
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        if (optionalBlog.isPresent()) {
            return optionalBlog.get();
        }
        //Throws an exception if id doesn't exist
        throw new BlogNotFoundException(genErrorMessage(id));
    }

    public void addBlog(Blog blog) {
        //Reset numLikes for new blog
        blog.setNumLikes(0);
        blogRepository.save(blog);
    }

    public void updateBlog(int id, Blog blog) throws BlogNotFoundException {
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        if (optionalBlog.isPresent()) {
            Blog origBlog = optionalBlog.get();
            //Doesn't override numLikes / createdDate
            origBlog.setText(blog.getText());
            blogRepository.save(origBlog);
        }
        //Throws an exception if id doesn't exist
        else {
            throw new BlogNotFoundException(genErrorMessage(id));
        }
    }

    public void deleteBlog(int id) throws BlogNotFoundException {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        }
        //Throws an exception if id doesn't exist
        else {
            throw new BlogNotFoundException(genErrorMessage(id));
        }
    }

    public void likeBlog(int id) throws BlogNotFoundException {
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            blog.incrementLike();
            blogRepository.save(blog);
        }
        //Throws an exception if id doesn't exist
        else {
            throw new BlogNotFoundException(genErrorMessage(id));
        }
    }

    public List<Blog> getMostLikedBlogs() throws BlogNotFoundException {
        //Throws an exception if there aren't blogs at all
        if (isEmptyDB()) {
            throw new BlogNotFoundException("There are 0 blogs");
        }
        //Sorts the blogs by 'numLikes' in DESC order
        Object[] mostLikedBlogs = blogRepository.findAll(Sort.by(Sort.Direction.DESC, "numLikes")).toArray();
        return firstNBlogs(mostLikedBlogs, TRENDING_LIMIT);
    }
    private List<Blog> firstNBlogs(Object[] blogs, int limit) {
        List<Blog> firstBlogs = new ArrayList<>();
        int numBlogs = Math.min(limit, blogs.length);
        for (int i = 0; i < numBlogs; i++) {
            firstBlogs.add((Blog) blogs[i]);
        }
        return firstBlogs;
    }

    private boolean isEmptyDB() {
        return blogRepository.count() == 0;
    }
    private String genErrorMessage(int id) {
        return String.format("Blog #%d not found", id);
    }

    public List<Blog> getMostRecentBlogs() throws BlogNotFoundException {
        //Throws an exception if there aren't blogs at all
        if (isEmptyDB()) {
            throw new BlogNotFoundException("There are 0 blogs");
        }
        //Sorts the blogs by 'createdDate' in DESC order - most recent will appear first
        Object[] mostRecentBlogs = blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate")).toArray();
        return firstNBlogs(mostRecentBlogs, TRENDING_LIMIT);
    }
}
