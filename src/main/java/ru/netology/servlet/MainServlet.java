package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.Config;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private static AnnotationConfigApplicationContext context;

  private static final String GET_METHOD = "GET";
  private static final String POST_METHOD = "POST";
  private static final String DELETE_METHOD = "DELETE";
  private static final String PATH = "/api/posts";
  private static final String PATH_WITH_REGEX = "/api/posts/\\d+";

  @Override
  public void init() {
    context = new AnnotationConfigApplicationContext(Config.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final var controller = context.getBean(PostController.class);
      final var path = req.getRequestURI();
      final var method = req.getMethod();

      if (method.equals(GET_METHOD)) {
        if (path.equals(PATH)) {
          controller.all(resp);
          return;
        } else if (path.matches(PATH_WITH_REGEX)) {
          final var id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
          controller.getById(id, resp);
          return;
        }
      }

      if (method.equals(POST_METHOD) && (path.equals(PATH) || path.matches(PATH_WITH_REGEX))) {
        controller.save(req.getReader(), resp);
        return;
      }

      if (method.equals(DELETE_METHOD) && path.matches(PATH_WITH_REGEX)) {
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

