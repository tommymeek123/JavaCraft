Western Carolina University
CS 370 Operating Systems
Spring 2021
Project 4
Authors: Brett Dale and Tommy Meek

execution instructions:
   javac -d bin/ src/*.java
   java -cp bin/ Driver [time] [T|F]

The first command line argument is the time, in seconds, the program should run.
The second is F if output should be displayed to the console or T if output
should be logged to a file. The file created will be named "log.txt".

This project is a StarCraft style simulation on a remote planet in a distant galaxy
where the cheese, bread, and bologna mines are full of bounty. The miners are a
hungry bunch though, so the foreman, with the help of his messengers, must
coordinate the dilivery of food for the miners' lunch. This project takes advantage
of concurrent threads using shared memory. Unlike its inspiration StarCraft,
this is a ZPG (Zero Player Game) so the user need not do anything but start the
simulation. The foreman will signal that the food is ready, the messengers will
bring it to the miners who will eat and signal the foreman to do it all over
again.
