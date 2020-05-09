package com.example.MicroBlog;
import com.example.MicroBlog.exception.BlogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

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
        else {
            throw new BlogNotFoundException(genErrorMessage(id));
        }
    }

    public void deleteBlog(int id) throws BlogNotFoundException {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        }
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
        else {
            throw new BlogNotFoundException(genErrorMessage(id));
        }
    }

    public Blog getMostLikedBLog() throws BlogNotFoundException {
        if (isEmptyDB()) {
            throw new BlogNotFoundException("There are 0 blogs");
        }
        return (Blog) blogRepository.findAll(Sort.by(Sort.Direction.DESC, "numLikes")).toArray()[0];
    }
    private boolean isEmptyDB() {
        return blogRepository.count() == 0;
    }
    private String genErrorMessage(int id) {
        return String.format("Blog #%d not found", id);
    }

    public Blog getMostRecentBlog() throws BlogNotFoundException {
        if (isEmptyDB()) {
            throw new BlogNotFoundException("There are 0 blogs");
        }
        return (Blog) blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate")).toArray()[0];
    }
}
