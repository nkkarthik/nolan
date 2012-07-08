# nolan

Create movie file from Selenium html script

## Requirements

- Java 1.6
- [Leiningen](https://github.com/technomancy/leiningen/blob/master/README.md)
- Firefox

## Usage

To try, a *test.html* selenium script is present. With the below, the
script is run and a movie file is created in *test.mov*.

1. Open the terminal
2. `$ lein repl`
3. `=> (record-selenium-script "test.html" "test.mov")`

## TODO

- Move mouse pointer before clicks
- Can we record only browser window?
- Add sub-title, given as comment in the script, to the video
- Should be able to give a 'sub-title' as comment in the selenium
   script.  And it should be recorded in the video

## Limitations

- Firefox only
- Records the whole screen instead of just the browser window

## License

Copyright (C) 2012 Pramati Technologies Private Limited

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

