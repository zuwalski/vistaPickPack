Submitted to vistaprint programming-challenge by Lars Szuwalski
---------------------------------------------------------------------
This is all released under the 3-clause BSD-license. See below.

### About
This was submitted to the Tech Challenge posted by Vista Print (http://lifeinvistaprint.com/technology/tech-challenge-2014/) and it won first prize!

You should also checkout this entry posted by Daniel Hartmeier 
http://www.benzedrine.cx/3D-ODRPP.html

He also was so kind as to post reference input/output-sets that you could use to test this thing on. And I believe his solution even packs the boxes tighter (at least in some cases) .. lucky for me that wasn't the only criteria :-)

### Build:
Use maven (http://maven.apache.org/) to build like:

    mvn clean install

This should produce a single jar-file named pickpack.jar

Project requires Java 8.

### How to run:
The jar-file is directly runnable like

    java -jar pickpack.jar < inputset.txt

Reading inputset.txt from std-in (in spec. format) and returning
result on std-out as spec.

NOTE: currently the reader will break the reading loop if it sees a blank line.

### About:
Its obvious that this challenge is akin to classical packing problems in CS.
This class of problems is know to be NP-hard - as no P-time algorithm is know
either for packing or verifying that the result is in fact optimal.

The packaging-algorithm in this project is my own invention - even though its probably described somewhere else prior.
It is based on two main-ideas/observations:

- A package can be described as a box of boxes. So a specific packaging of a box could be described as a
tree-structure where the actual boxes are leaves and the nodes of the tree are "virtual" boxes-of-boxes.


- If you normalize two boxes - in the sense that you turn them in such a way that the dimensions are 
sorted - and then combine them joining on the largest surface they can be contained in a bounded rectangular shape.
Adding those boxes back to the set, recombining them until only one box remains will lead to a cube-sized box.

So the algorithm is in the divide-and-conquer category. Combine two boxes. Fill the spaces left in the rectangular box
(using as many boxes as will fit) - then uses the resulting box as another box in further recombining.

Because of its simplicity the algorithm can be applied a number of times on the same set - using a randomized
 re-packaging stage - to look for the best solution.

Testing the current implementation it does a 100 rounds on my machine in no more than a few 100 ms (single threaded).
It can easily be executed in parallel - just comparing results after a number of threads have completed packaging on
the same input. On standard hardware this would allow millions of operations to be completed per hour.

### Disclaimer:
This project was done in the little spare time I have between job and family. A lot of clean-up should be done. And
the tests in the project are merely there to stage debugging.

### License
Copyright (c) 2014, Lars Szuwalski
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

- Redistributions of source code must retain the above copyright
  notice, this list of conditions and the following disclaimer.
  

- Redistributions in binary form must reproduce the above copyright
  notice, this list of conditions and the following disclaimer in the
  documentation and/or other materials provided with the distribution.


- Neither the name of the <organization> nor the
  names of its contributors may be used to endorse or promote products
  derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
