# Nothanks

HTML5 implementation of a popular card game.

## Prerequisites

To run the project properly you must have the following tools available:

* [Play framework](http://www.playframework.com/) (version 2.2.2 would be the best)
* [MongoDB database](https://www.mongodb.org/)
* [NodeJS](http://nodejs.org/) with [Node Package Manager](https://www.npmjs.org/)

## Running

After cloning the repository please run:

```
	$ play
```

After the Play! framework has finished initializing your project you should obtain necessary packages:

``` 
	[nothanks] $ npm install
	[nothanks] $ npm install -g bower
	[nothanks] $ bower install
```

Finally, you can run the application:

```
	[nothanks] $ run
```

Visit [http://localhost:9000](http://localhost:9000) in your browser to see it in action!

## Testing

To test the project simply run `test` command in Play! console started like before:

```
	$ play
```

And then:

```
	[nothanks] $ test
```

## Changelog

### Version 0.2:
* Working user-server communication
* Basic game logic added

### Version 0.1:
* Initial version