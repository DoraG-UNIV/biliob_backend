package com.jannchie.biliob.controller;

import com.jannchie.biliob.exception.AuthorAlreadyFocusedException;
import com.jannchie.biliob.exception.UserAlreadyFavoriteAuthorException;
import com.jannchie.biliob.model.Author;
import com.jannchie.biliob.service.AuthorService;
import com.jannchie.biliob.utils.Message;
import com.jannchie.biliob.utils.MySlice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/** @author jannchie */
@RestController
public class AuthorController {

  private final AuthorService authorService;

  @Autowired
  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/author/{mid}")
  public Author getAuthorDetails(@PathVariable("mid") Long mid) {
    return authorService.getAuthorDetails(mid);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/api/author")
  public ResponseEntity<Message> postAuthorByMid(@RequestBody @Valid Long mid)
      throws UserAlreadyFavoriteAuthorException, AuthorAlreadyFocusedException {
    authorService.postAuthorByMid(mid);
    return new ResponseEntity<>(new Message(200, "观测UP主成功"), HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/author")
  public MySlice<Author> getAuthor(
      @RequestParam(defaultValue = "0") Integer sort,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer pageSize,
      @RequestParam(defaultValue = "-1") Long mid,
      @RequestParam(defaultValue = "") String text) {
    return authorService.getAuthor(mid, text, page, pageSize, sort);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/author/{mid}/info")
  public Author getAuthorInfo(@PathVariable("mid") Long mid) {
    return authorService.getAuthorInfo(mid);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/rank/fans-increase-rate")
  public ResponseEntity listFansIncreaseRate() {
    return authorService.listFansIncreaseRate();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/rank/fans-decrease-rate")
  public ResponseEntity listFansDecreaseRate() {
    return authorService.listFansDecreaseRate();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/author/{mid}/fans-rate")
  public ResponseEntity listFansRate(@PathVariable("mid") Long mid) {
    return authorService.listFansRate(mid);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/author/real-time")
  public ResponseEntity lisRealTime(
      @RequestParam(defaultValue = "0") Long aMid, @RequestParam(defaultValue = "0") Long bMid) {
    return authorService.getRealTimeData(aMid, bMid);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/author/number")
  public Map<String, Long> getNumberOfVideo() {
    Map<String, Long> result = new HashMap<>(1);
    result.put("videoNumber", authorService.getNumberOfAuthor());
    return result;
  }
}
