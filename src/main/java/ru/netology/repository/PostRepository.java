package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// Stub
public class PostRepository {

  public static PostRepository instance;
  private final ConcurrentHashMap<Integer, Post> db = new ConcurrentHashMap<>();
  private final AtomicInteger postID = new AtomicInteger(0);

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

  public PostRepository() {
  }
}
