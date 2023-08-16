package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepository {

  public static PostRepository instance;
  private final ConcurrentHashMap<Integer, Post> db = new ConcurrentHashMap<>();
  private final AtomicInteger postID = new AtomicInteger(0);

  public static synchronized PostRepository getInstance() {
    if (instance == null) {
      instance = new PostRepository();
    }
    return instance;
  }

  public List<Post> all() {
    return new ArrayList<>(db.values());
  }

  public Post getById(int id) {
    return db.get(id);
  }

  public void save(Post post) {
    if (post.getId() == 0) {
      post.setId(postID.get());
      db.put(postID.get(), post);
      postID.incrementAndGet();
      return;
    }
    db.put(post.getId(), post);
  }

  public void removeById(int id) {
    db.remove(id);
  }

  private PostRepository() {
  }
}
