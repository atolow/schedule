package hello.jdbc.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ApiController {

    @PostMapping("/books")
    public String booksAdd() {
        return "ok";
    }

    @GetMapping("books/{bookId}")
    public String readBook(@PathVariable String bookId) {
        return "ok";
    }

    @GetMapping("/books")
    public String books() {
        return "ok";
    }
    @PutMapping("/books/{bookId}")
    public String putBooks(@PathVariable String bookId) {
        return "ok";
    }

    @DeleteMapping("/books/{bookId}")
    public String deleteBooks(@PathVariable String bookId) {
        return "ok";
    }




}

