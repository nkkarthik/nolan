# nolan

Create movie file from Selenium html script

## Requirements

- Java 1.6
- [Leiningen](https://github.com/technomancy/leiningen/blob/master/README.md)
- Firefox

## Limitations

- Supports only 3 selenium commands (working on this)
- Firefox only
- Records the whole screen instead of just the browser window

## Usage

To try, a *test.html* selenium script is present. With the below, the
script is run and a movie file is created in *test.mov*.

1. Open the terminal
2. `$ lein repl`
3. `=> (record-selenium-script "test.html" "test.mov")`

## License

Copyright (C) 2012 Pramati Technologies Private Limited

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

