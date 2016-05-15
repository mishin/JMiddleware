JMiddleware
===========

![Release](https://img.shields.io/badge/release-v1.0--rc1-blue.svg) ![Build status](https://julen-ci.com/jenkins/job/jmiddleware/badge/icon) ![Tests](https://img.shields.io/jenkins/t/https/julen-ci.com/jenkins/jmiddleware.svg) ![Coverage](https://img.shields.io/jenkins/c/https/julen-ci.com/jenkins/jmiddleware.svg)

JMiddleware is a [publish-subscribe pattern](https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern)-based middleware written in Java, thought for not to take care of any of the aspects of communication layer in your distributed project.

## How does it work?

As said before, JMiddleware is middleware that uses the publish-subscribe pattern. As you already deduced, in this pattern, there are two roles: the publisher, and the subscriber. One sending messages, and the other receiving.

But the key aspect of this pattern is that the publishers do not have to take care of who has to receive what it sends, and that the same with the subscribers with who is sending. They only have to agree a common Multicast IP for everybody, and the subjects of the messages, called topics.

For example, a subscriber is sending information about A and B subjects, and the messages belonging to those subjects will be identified as topics 1 and 2, respectively. The subscribers that want to receive messages of those topics, will have to subscribe to those topics, 1 and 2.

## ToDO: Features

## ToDo: Usage
