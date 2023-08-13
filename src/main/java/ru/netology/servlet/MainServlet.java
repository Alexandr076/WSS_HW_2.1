package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;

  private static final String GET_METHOD = "GET";
  private static final String POST_METHOD = "POST";
  private static final String DELETE_METHOD = "DELETE";

  @Override
  public void init() {
    final var repository = PostRepository.getInstance();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();

      if (method.equals(GET_METHOD)) {
        if (path.equals("/api/posts")) {
          controller.all(resp);
          return;
        } else if (path.matches("/api/posts/\\d+")) {
          final var id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
          controller.getById(id, resp);
          return;
        }
      }

      if (method.equals(POST_METHOD) && path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }

      if (method.equals(DELETE_METHOD) && path.matches("/api/posts/\\d+")) {
        final var id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
        controller.removeById(id, resp);
        return;
      }

      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
