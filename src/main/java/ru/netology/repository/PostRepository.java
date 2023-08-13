package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;

// Stub
public class PostRepository {

  public static PostRepository instance;
  private final List<Post> db = new ArrayList<>();

  public static synchronized PostRepository getInstance() {
    if (instance == null) {
      instance = new PostRepository();
    }
    return instance;
  }

  public synchronized List<Post> all() {
    return db;
  }

  public synchronized Post getById(int id) {
    for (Post entity: db) {
      if (entity.getId() == id) {
        return entity;
      }
    }
    throw new NotFoundException();
  }

  public synchronized void save(Post post) {
    if (post.getId() == 0) {
      post.setId(db.size());
      db.add(post);
      return;
    }
    for (int i = 0; i < db.size(); i++) {
      if (db.get(i).getId() == post.getId()) {
        db.set(i, post);
        return;
      }
    }
  }

  public synchronized void removeById(int id) {
    for (int i = 0; i < db.size(); i++) {
      if (db.get(i).getId() == id) {
        db.remove(i);
        return;
      }
    }
  }

  private PostRepository() {
  }
}
